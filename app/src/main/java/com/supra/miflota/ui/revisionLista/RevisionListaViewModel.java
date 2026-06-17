package com.supra.miflota.ui.revisionLista;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.supra.miflota.data.models.ChecklistDiario;
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
    private MutableLiveData<String> errorMessage ;
    private RevisionApiService revisionApiService;
    public RevisionListaViewModel(@NonNull Application application) {
        super(application);
        revisionListMutable = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        vehiculoMutable = new MutableLiveData<>();
        revisionApiService = ApiClient.getClient(application.getApplicationContext()).create(RevisionApiService.class);
    }

    public LiveData<List<ChecklistDiario>> getListaRevisiones() {
        return revisionListMutable;
    }

  public LiveData<Vehiculo> getVehiculoMutable(){
        return vehiculoMutable;
  }


    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void cargarVehiculoYRevisiones(){

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

        cargarRevisiones(vehiculo.getIdVehiculo(),false,true);
    }

    public void cargarRevisiones (int idVehiculo,boolean misRegistros, boolean estado){
        String token = ApiClient.leerToken(getApplication());
        if (token == null) {
            return;
        }

        Call<List<ChecklistDiario>> call = revisionApiService.listaRevisiones(idVehiculo,misRegistros, estado, token);

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
}