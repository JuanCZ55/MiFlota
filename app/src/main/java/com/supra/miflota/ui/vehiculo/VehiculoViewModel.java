package com.supra.miflota.ui.vehiculo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.supra.miflota.data.models.Matafuego;
import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiResponsesHelpers.ApiErrorResponse;
import com.supra.miflota.data.network.ApiServices.VehiculoApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculoViewModel extends AndroidViewModel {
    private MutableLiveData<Vehiculo> vehiculoMutable;
    private MutableLiveData<String> mensajeError;
    private VehiculoApiService service;

    public VehiculoViewModel(@NonNull Application application) {
        super(application);
        mensajeError = new MutableLiveData<>();
        vehiculoMutable = new MutableLiveData<>();
        service = ApiClient.getClient(application.getApplicationContext()).create(VehiculoApiService.class);
    }
    public LiveData<Vehiculo> getVehiculoMutable(){
        return vehiculoMutable;
    }

    public LiveData<String> getMensajeError(){
        return mensajeError;
    }





    private void consultarVehiculo(int idVehiculo){

        Call<Vehiculo> call = service.obtenerVehiculo(idVehiculo);
        call.enqueue(new Callback<Vehiculo>() {
            @Override
            public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {
                if (!response.isSuccessful()){
                    mensajeError.postValue("Ocurrio un error al cargar los datos del vehiculo.");
                    return;
                }
                Vehiculo vehiculo = response.body();
                if(vehiculo == null){
                    mensajeError.postValue("No se encontraron datos del vehiculo.");
                    return;
                }
                SharedPreferences pref = getApplication().getSharedPreferences("DataVehiculo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("id_vehiculo", idVehiculo);
                editor.putString("patente", vehiculo.getPatente());
                editor.putString("marca", vehiculo.getMarca());
                editor.putString("modelo", vehiculo.getModelo());
                editor.apply();
                vehiculoMutable.postValue(vehiculo);
            }

            @Override
            public void onFailure(Call<Vehiculo> call, Throwable t) {
                mensajeError.postValue("Error de inesperado al obtener el vehiculo");
            }
        });
    }



    /**
     * Carga los datos del vehiculo en el LiveData vehiculoMutable.
     * Si ocurre un error, se actualiza el LiveData mensajeError.
     * @param bundle Datos del vehiculo.
     * */
    public void cargarVehiculo(Bundle bundle) {
        SharedPreferences preferences = getApplication().getSharedPreferences("DataVehiculo", Context.MODE_PRIVATE);
        if(bundle == null && !preferences.contains("id_vehiculo") ){
            mensajeError.setValue("No se encontraron datos del vehiculo.");
            return;
        }
        if(bundle != null && bundle.containsKey("vehiculo")){
            Vehiculo vehiculo = (Vehiculo) bundle.getSerializable("vehiculo");
            preferences.edit()
                    .putInt("id_vehiculo", vehiculo.getIdVehiculo())
                    .putString("patente", vehiculo.getPatente())
                    .putString("marca", vehiculo.getMarca())
                    .putString("modelo", vehiculo.getModelo())
                    .apply();
            vehiculoMutable.postValue(vehiculo);
            return;
        }
        int idVehiculo = 0;
        try{
            idVehiculo = preferences.getInt("id_vehiculo", 0);
            if(idVehiculo == 0){
                mensajeError.setValue("Identificador de Vehiculo Invalido");
                return;
            }

        }catch (Exception e){
            mensajeError.setValue("No se pudo cargar el vehiculo por su identificador.");
            return;
        }
        consultarVehiculo(idVehiculo);
    }

}