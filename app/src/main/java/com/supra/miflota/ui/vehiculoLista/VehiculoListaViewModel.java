package com.supra.miflota.ui.vehiculoLista;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.VehiculoApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculoListaViewModel extends AndroidViewModel {
    private MutableLiveData<List<Vehiculo>> mutableLiveDataListaVehiculos = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private VehiculoApiService vehiculoApiService;
    public VehiculoListaViewModel(@NonNull Application application) {
        super(application);
        vehiculoApiService = ApiClient.getClient(application.getApplicationContext()).create(VehiculoApiService.class);
    }

    public MutableLiveData<List<Vehiculo>> getMutableLiveDataListaVehiculos() {
        return mutableLiveDataListaVehiculos;
    }

    public void setMutableLiveDataListaVehiculos(MutableLiveData<List<Vehiculo>> mutableLiveDataListaVehiculos) {
        this.mutableLiveDataListaVehiculos = mutableLiveDataListaVehiculos;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void cargarListadoDeVehiculos(){
        vehiculoApiService.obtenerVehiculos().enqueue(new Callback<List<Vehiculo>>() {
            @Override
            public void onResponse(Call<List<Vehiculo>> call, Response<List<Vehiculo>> response) {
                if(!response.isSuccessful()){
                    errorMessage.setValue("Ocurrio un error al cargar los vehiculos");
                    return;
                }
                List<Vehiculo> vehiculoList = response.body();
                if(vehiculoList == null){
                    errorMessage.setValue("No se encontraron vehiculos");
                    return;
                }
                mutableLiveDataListaVehiculos.postValue(vehiculoList);
                return;
            }

            @Override
            public void onFailure(Call<List<Vehiculo>> call, Throwable t) {
                errorMessage.setValue("Error de conexión: " + t.getMessage());
            }
        });
    }
}