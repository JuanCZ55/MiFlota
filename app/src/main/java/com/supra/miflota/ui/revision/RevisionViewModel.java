package com.supra.miflota.ui.revision;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supra.miflota.data.models.ChecklistDiario;
import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.RevisionApiService;

import java.time.Instant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevisionViewModel extends AndroidViewModel {
    private MutableLiveData<Vehiculo> vehiculoMutable;
    private MutableLiveData<ChecklistDiario> revisionMutable;
    private MutableLiveData<String> errorMessage;
    private MutableLiveData<String> successMessage;

    private RevisionApiService revisionApiService;

    public RevisionViewModel(@NonNull Application application) {
        super(application);
        revisionMutable = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();
        vehiculoMutable = new MutableLiveData<>();
        successMessage = new MutableLiveData<>();
        revisionApiService = ApiClient.getClient(application.getApplicationContext()).create(RevisionApiService.class);
    }

    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    public LiveData<Vehiculo> getVehiculoMutable(){
        return vehiculoMutable;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<ChecklistDiario> getRevisionMutable(){
        return revisionMutable;
    }

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

        if (vehiculo.getIdVehiculo() == -1 || vehiculo.getIdVehiculo() == 0 ) {
            errorMessage.setValue("No se pudo cargar la información del vehiculo.");
            return;
        }
        vehiculoMutable.postValue(vehiculo);
    }

    public void cargarRevision(Bundle bundle){
        ChecklistDiario revision = (ChecklistDiario) bundle.getSerializable("Revision");
        if (revision == null) {
            errorMessage.setValue("No se pudo cargar la información de la revisión.");
            return;
        }

        revisionMutable.setValue(revision);
    }


    public void crearRevision(boolean faroDelanteroIzquierdo, boolean faroDelanteroDerecho, boolean faroTraseroIzquierdo, boolean faroTraseroDerecho,
                                    boolean liquidoFrenos, boolean nivelAceite, boolean presionNeumaticos, boolean nivelFrenos, boolean nivelRefrigerante,
                                    boolean nivelAguaParabrisas, boolean matafuegoVigente, String observaciones, boolean estado )
    {
        ChecklistDiario newRevision = new ChecklistDiario();
        newRevision.setFaroDelanteroIzquierdo(faroDelanteroIzquierdo);
        newRevision.setFaroDelanteroDerecho(faroDelanteroDerecho);
        newRevision.setFaroTraseroIzquierdo(faroTraseroIzquierdo);
        newRevision.setFaroTraseroDerecho(faroTraseroDerecho);
        newRevision.setLiquidoFrenos(liquidoFrenos);
        newRevision.setNivelAceite(nivelAceite);
        newRevision.setPresionNeumaticos(presionNeumaticos);
        newRevision.setNivelFrenos(nivelFrenos);
        newRevision.setNivelRefrigerante(nivelRefrigerante);
        newRevision.setNivelAguaParabrisas(nivelAguaParabrisas);
        newRevision.setMatafuegoVigente(matafuegoVigente);

        newRevision.setObservaciones(observaciones);
        newRevision.setFecha(Instant.now().toString());

        Vehiculo v = vehiculoMutable.getValue();
        assert v != null;
        newRevision.setIdVehiculo(v.getIdVehiculo());
        newRevision.setEstado(estado);

        consultaCrearRevision(newRevision);
    }


    private void consultaCrearRevision(ChecklistDiario revision){
        String token = ApiClient.leerToken(getApplication());
        if (token == null) {
            return;
        }

        Call<ChecklistDiario> call = revisionApiService.crearRevision(token, revision);
        call.enqueue(new Callback<ChecklistDiario>() {
            @Override
            public void onResponse(Call<ChecklistDiario> call, Response<ChecklistDiario> response) {
                if(!response.isSuccessful()){
                    errorMessage.setValue("Ocurrio un error al cargar la revision");
                    return;
                }
                ChecklistDiario revisionCreada = response.body();
                if(revisionCreada == null){
                    errorMessage.setValue("No se pudo crear la revision");
                    return;
                }

                revisionMutable.postValue(revisionCreada);
                successMessage.postValue("Revision creada con exito");

            }

            @Override
            public void onFailure(Call<ChecklistDiario> call, Throwable t) {
                errorMessage.postValue("Error inesperado al crear la revision");
            }
        });

    }

    public void editarRevision(
            boolean faroDelanteroIzquierdo, boolean faroDelanteroDerecho, boolean faroTraseroIzquierdo, boolean faroTraseroDerecho,
            boolean liquidoFrenos, boolean nivelAceite, boolean presionNeumaticos, boolean nivelFrenos, boolean nivelRefrigerante,
            boolean nivelAguaParabrisas, boolean matafuegoVigente, String observaciones, boolean estado){
        ChecklistDiario revision = revisionMutable.getValue();
        if (revision == null) {
            errorMessage.setValue("No se pudo cargar la información de la revisión.");
            return;
        }
        revision.setFaroDelanteroIzquierdo(faroDelanteroIzquierdo);
        revision.setFaroDelanteroDerecho(faroDelanteroDerecho);
        revision.setFaroTraseroIzquierdo(faroTraseroIzquierdo);
        revision.setFaroTraseroDerecho(faroTraseroDerecho);
        revision.setLiquidoFrenos(liquidoFrenos);
        revision.setNivelAceite(nivelAceite);
        revision.setPresionNeumaticos(presionNeumaticos);
        revision.setNivelFrenos(nivelFrenos);
        revision.setNivelRefrigerante(nivelRefrigerante);
        revision.setNivelAguaParabrisas(nivelAguaParabrisas);
        revision.setMatafuegoVigente(matafuegoVigente);
        revision.setObservaciones(observaciones);
        revision.setEstado(estado);

        consultarActualizarRevision(revision);
    }

    private void consultarActualizarRevision(ChecklistDiario revision){
        String token = ApiClient.leerToken(getApplication());
        if (token == null) {
            return;
        }
        Call<ChecklistDiario> call = revisionApiService.editarRevision(token, revision.getIdChecklistDiario(), revision);
        call.enqueue(new Callback<ChecklistDiario>() {
            @Override
            public void onResponse(Call<ChecklistDiario> call, Response<ChecklistDiario> response) {
                if(!response.isSuccessful()){
                    errorMessage.setValue("Ocurrio un error al editar la revision");
                    return;
                }

                successMessage.postValue("Revision editada con exito");
            }

            @Override
            public void onFailure(Call<ChecklistDiario> call, Throwable t) {
                errorMessage.setValue("Error inesperado al editar la revision");
            }
        });
    }





}