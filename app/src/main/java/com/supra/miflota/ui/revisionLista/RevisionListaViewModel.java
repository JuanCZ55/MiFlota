package com.supra.miflota.ui.revisionLista;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.supra.miflota.data.models.ChecklistDiario;
import com.supra.miflota.data.models.Status;
import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.RevisionApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevisionListaViewModel extends AndroidViewModel {
    private MutableLiveData<Vehiculo> vehiculoMutable;
    private MutableLiveData<List<ChecklistDiario>> revisionListMutable;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<Status> statusMutable;

    private RevisionApiService revisionApiService;
    public RevisionListaViewModel(@NonNull Application application) {
        super(application);
        revisionListMutable = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        vehiculoMutable = new MutableLiveData<>();
        statusMutable = new MutableLiveData<>();
        revisionApiService = ApiClient.getClient(application.getApplicationContext()).create(RevisionApiService.class);
    }

    public LiveData<List<ChecklistDiario>> getListaRevisiones() {
        return revisionListMutable;
    }

    public LiveData<Status> getStatusMutable(){
        return statusMutable;
    }

  public LiveData<Vehiculo> getVehiculoMutable(){
        return vehiculoMutable;
  }


    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Recupera la información del vehículo desde SharedPreferences e inicializa
     * el estado de los filtros para disparar la carga inicial de revisiones.
     */
    public void cargarVehiculo(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DataVehiculo", Context.MODE_PRIVATE);

        if (!sharedPreferences.contains("id_vehiculo")) {
            errorMessage.setValue("No se pudo cargar la información del vehiculo.");
            return;
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(sharedPreferences.getInt("id_vehiculo",-1));
        vehiculo.setPatente(sharedPreferences.getString("patente",null));
        vehiculo.setMarca(sharedPreferences.getString("marca",null));
        vehiculo.setModelo(sharedPreferences.getString("modelo",null));

        if (vehiculo.getIdVehiculo() == -1 || vehiculo.getIdVehiculo()==0 ) {
            errorMessage.setValue("No se pudo cargar la información del vehiculo.");
            return;
        }

        vehiculoMutable.postValue(vehiculo);

        setFiltros(null, null);
    }


    /**
     * Coordina la obtencion de los filtros actuales y el vehiculo seleccionado
     * para disparar la consulta de revisiones
     */
    public void dispararBusquedaRevisiones(){
        Status status = statusMutable.getValue();
        if (status == null) {
            status = new Status(false, true);
        }

        cargarRevisiones(vehiculoMutable.getValue().getIdVehiculo(),status);
    }

    /**
     * Realiza la petición asíncrona al servidor para obtener el historial de revisiones.
     *
     * @param idVehiculo Identificador único del vehículo a consultar.
     * @param status Objeto que contiene los estados de los filtros:
     *               isAll: false para ver todas las revisiones, true para solo las propias.
     *               isActive: true para revisiones activas, false para inactivas .
     */
    public void cargarRevisiones (int idVehiculo, Status status){
        String token = ApiClient.leerToken(getApplication());
        if (token == null) {
            return;
        }

        Call<List<ChecklistDiario>> call = revisionApiService.listaRevisiones(idVehiculo, status.isAll(), status.isActive(), token);

        call.enqueue(new Callback<List<ChecklistDiario>>() {
            @Override
            public void onResponse(Call<List<ChecklistDiario>> call, Response<List<ChecklistDiario>> response) {
                if(!response.isSuccessful()){
                    errorMessage.setValue("Ocurrio un error al cargar las revisiones");
                    return;
                }
                List<ChecklistDiario> revisionList = response.body();

                if(revisionList == null){
                    errorMessage.setValue("No se encontraron revisiones");
                    return;
                }

                revisionListMutable.postValue(revisionList);
            }

            @Override
            public void onFailure(Call<List<ChecklistDiario>> call, Throwable t) {
                errorMessage.postValue("Error al carlar las revisiones");
            }
        });
    }

    /**
     * Actualiza el estado de los filtros de busqueda de forma selectiva.
     *
     * @param isAll    Define el alcance: false para "Todos", true para "Propio".
     *                 Si es null, mantiene el valor actual.
     * @param isActive Define el estado: true para "Activos", false para "Inactivos".
     *                 Si es null, mantiene el valor actual.
     */
    public void setFiltros(Boolean isAll, Boolean isActive){
        Status statusUpdate = statusMutable.getValue();
        if (statusUpdate == null) {
            statusUpdate = new Status(false, true);
        }
        if(isAll != null){
            statusUpdate.setAll(isAll);
        }
        if(isActive != null){
            statusUpdate.setActive(isActive);
        }
        statusMutable.postValue(statusUpdate);
    }
}