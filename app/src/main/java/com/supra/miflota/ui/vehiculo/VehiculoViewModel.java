package com.supra.miflota.ui.vehiculo;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.supra.miflota.data.models.Vehiculo;

public class VehiculoViewModel extends AndroidViewModel {
    private MutableLiveData<Vehiculo> vehiculoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private Context context;

    public VehiculoViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public MutableLiveData<Vehiculo> getVehiculoMutableLiveData() {
        return vehiculoMutableLiveData;
    }

    public void setVehiculoMutableLiveData(MutableLiveData<Vehiculo> vehiculoMutableLiveData) {
        this.vehiculoMutableLiveData = vehiculoMutableLiveData;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void getVehiculo(Bundle bundle){
        if(bundle == null){
            errorMessage.setValue("No se envio el vehiculo");
            return;
        }
        Vehiculo vehiculo = (Vehiculo) bundle.getSerializable("vehiculo");
        if(vehiculo == null){
            errorMessage.setValue("No se envio el vehiculo");
            return;
        }
        vehiculoMutableLiveData.setValue(vehiculo);
    }

}