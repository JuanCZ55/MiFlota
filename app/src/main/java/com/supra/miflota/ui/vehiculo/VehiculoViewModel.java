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

public class VehiculoViewModel extends AndroidViewModel {
    private MutableLiveData<Vehiculo> vehiculoMutable;
    private MutableLiveData<String> mensajeError;

    public VehiculoViewModel(@NonNull Application application) {
        super(application);
        mensajeError = new MutableLiveData<>();
        vehiculoMutable = new MutableLiveData<>();
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
        Vehiculo vehiculo;
        if (bundle == null || !bundle.containsKey("vehiculo")) {
            mensajeError.postValue("No se encontraron datos del vehiculo.");
            return;
        }
        vehiculo = (Vehiculo) bundle.getSerializable("vehiculo");

        SharedPreferences pref = getApplication().getSharedPreferences("DataVehiculo", Context.MODE_PRIVATE);
        pref.edit()
                .putInt("id_vehiculo", vehiculo.getIdVehiculo())
                .putString("patente", vehiculo.getPatente())
                .putString("marca", vehiculo.getMarca())
                .putString("modelo", vehiculo.getModelo())
                .apply();
        vehiculoMutable.postValue(vehiculo);
    }


}