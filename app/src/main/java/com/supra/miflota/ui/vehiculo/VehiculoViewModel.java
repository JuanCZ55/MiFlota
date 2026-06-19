package com.supra.miflota.ui.vehiculo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supra.miflota.data.models.Matafuego;
import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.data.network.ApiClient;
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


    /**
     * Carga los datos del vehiculo en el LiveData vehiculoMutable.
     * Si ocurre un error, se actualiza el LiveData mensajeError.
     * @param bundle Datos del vehiculo.
    * */
    public void cargarVehiculo(Bundle bundle){
        if (bundle == null || !bundle.containsKey("id_vehiculo")) {
            mensajeError.postValue("No se encontraron datos del vehiculo.");
            return;
        }
        int idVehiculo = bundle.getInt("id_vehiculo");
        consultarVehiculo(idVehiculo);
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

    public void actualizarVehiculo(){
        SharedPreferences pref = getApplication().getSharedPreferences("DataVehiculo",
                Context.MODE_PRIVATE);
        int idVehiculo = pref.getInt("id_vehiculo", 0);
        if (idVehiculo == 0) {
            mensajeError.postValue("No se encontraron datos del vehiculo.");
            return;
        }
        consultarVehiculo(idVehiculo);
    }
}