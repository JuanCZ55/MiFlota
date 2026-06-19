package com.supra.miflota.ui.kilometraje;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.MutableBoolean;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.supra.miflota.data.models.RegistroKilometraje;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiResponsesHelpers.ApiErrorResponse;
import com.supra.miflota.data.network.ApiServices.RegistrosKmApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KilometrajeViewModel extends AndroidViewModel {

    private MutableLiveData<RegistroKilometraje> registroKilometrajeMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCrear = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isEdicion = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isOnlyView = new MutableLiveData<>(false);
    private RegistrosKmApiService apiService;

    public KilometrajeViewModel(@NonNull Application application) {
        super(application);
        apiService = ApiClient.getClient(application.getApplicationContext()).create(RegistrosKmApiService.class);
    }
    public void setRegistroKilometrajeMutableLiveData(RegistroKilometraje registroKilometraje) {
        this.registroKilometrajeMutableLiveData.setValue(registroKilometraje);
    }
    public LiveData<RegistroKilometraje> getRegistroKilometrajeMutableLiveData() {
        return registroKilometrajeMutableLiveData;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setValue(errorMessage);
    }
    public void setIsCrear(boolean isCrear) {
        this.isCrear.setValue(isCrear);
    }
    public LiveData<Boolean> getIsCrear() {
        return isCrear;
    }
    public void setIsEdicion(boolean isEdicion) {
        this.isEdicion.setValue(isEdicion);
    }
    public LiveData<Boolean> getIsEdicion() {
        return isEdicion;
    }
    public void setIsOnlyView(boolean isOnlyView) {
        this.isOnlyView.setValue(isOnlyView);
    }
    public LiveData<Boolean> getIsOnlyView() {
        return isOnlyView;
    }
    public void descomprimirBundle(Bundle bundle){
        if(bundle != null){
            RegistroKilometraje registroKilometraje = (RegistroKilometraje) bundle.getSerializable("registroKilometraje");
            setearConfiguracion(registroKilometraje);
            setRegistroKilometrajeMutableLiveData(registroKilometraje);
        }else {
            isCrear.setValue(true);
        }
    }
    public void setearConfiguracion(RegistroKilometraje registroKilometraje){
        if(registroKilometraje.isCurrentUser())
            isEdicion.setValue(true);
        else
            isOnlyView.setValue(true);
    }
    public void editarKilometraje(String kilometrajeEnviado, Boolean estadoEnviado){
        setErrorMessage("Registro: " + kilometrajeEnviado + "Estado: " + estadoEnviado);
        if(kilometrajeEnviado.isEmpty()){
            setErrorMessage("El kilometraje no puede estar vacio");
            return;
        }
        int kilometrajeParsed = Integer.parseInt(kilometrajeEnviado);
        RegistroKilometraje registroKilometrajeNew = registroKilometrajeMutableLiveData.getValue();
        registroKilometrajeNew.setKilometraje(kilometrajeParsed);
        registroKilometrajeNew.setEstado(estadoEnviado);
        apiService.actualizarRegistro(registroKilometrajeNew.getIdRegistroKilometraje(),
                registroKilometrajeNew).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    setRegistroKilometrajeMutableLiveData(registroKilometrajeNew);
                    setIsEdicion(true);
                    setIsCrear(false);
                    errorMessage.setValue("Registro actualizado exitosamente");
                }else{
                    try{
                        String errorJson = response.errorBody().string();
                        Gson gson = new Gson();
                        ApiErrorResponse apiErrorResponse = gson.fromJson(errorJson, ApiErrorResponse.class);
                        setErrorMessage(apiErrorResponse.getMessage());
                    }catch (Exception e){
                        setErrorMessage("Ocurrio un error desconocido. Codigo: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errorMessage.setValue("Error en el servidor");
            }
        });

    }
    public void crearKilometraje(String kilometraje){
        if(kilometraje.isEmpty()){
            setErrorMessage("El kilometraje no puede estar vacio");
            return;
        }
        int kilometrajeParsed = Integer.parseInt(kilometraje);
        RegistroKilometraje registroKilometrajeNew = new RegistroKilometraje();

        registroKilometrajeNew.setKilometraje(kilometrajeParsed);

        //obtener el id del vehiculo desde las shared preferences
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DataVehiculo", Application.MODE_PRIVATE);
        int idVehiculo = sharedPreferences.getInt("id_vehiculo", 0);
        registroKilometrajeNew.setIdVehiculo(idVehiculo);
        registroKilometrajeNew.setEstado(true);

        apiService.crearRegistro(registroKilometrajeNew).enqueue(new Callback<RegistroKilometraje>() {
            @Override
            public void onResponse(Call<RegistroKilometraje> call, Response<RegistroKilometraje> response) {
                if(response.isSuccessful()){
                    RegistroKilometraje registroKilometraje = response.body();
                    if(registroKilometraje != null){
                        setRegistroKilometrajeMutableLiveData(registroKilometraje);
                        setIsEdicion(true);
                        setIsCrear(false);
                    }
                    errorMessage.setValue("Registro creado exitosamente");
                    return;
                }
                try{
                    String errorJson = response.errorBody().string();
                    Gson gson = new Gson();
                    ApiErrorResponse apiErrorResponse = gson.fromJson(errorJson, ApiErrorResponse.class);
                    setErrorMessage(apiErrorResponse.getMessage());
                }catch (Exception e){
                    setErrorMessage("Ocurrio un error desconocido. Codigo: " + response.code());
                }

            }

            @Override
            public void onFailure(Call<RegistroKilometraje> call, Throwable t) {
                errorMessage.setValue("Error al crear el registro");
            }
        });

    }
}