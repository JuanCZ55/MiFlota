package com.supra.miflota.ui.vehiculo;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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

    public LiveData<Vehiculo> getVehiculoMutableLiveData() {
        return vehiculoMutableLiveData;
    }

    public void setVehiculoMutableLiveData(Vehiculo vehiculoMutableLiveDataParam) {
        vehiculoMutableLiveData.setValue(vehiculoMutableLiveDataParam);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setValue(errorMessage);
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