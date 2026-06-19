package com.supra.miflota.ui.servicioLista;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supra.miflota.data.models.Service;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.ServiceApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioListaViewModel extends AndroidViewModel {
    private MutableLiveData<List<Service>> serviceList;
    private MutableLiveData<String> errorMessage;
    private ServiceApiService serviceApiService;


    public ServicioListaViewModel(@NonNull Application application) {
        super(application);
        serviceApiService = ApiClient.getClient(application.getApplicationContext()).create(ServiceApiService.class);
    }
    public LiveData<String> getErrorMessage() {
        if (errorMessage == null) {
            errorMessage = new MutableLiveData<>();
        }
        return errorMessage;
    }
    public LiveData<List<Service>> getServiceList() {
        if (serviceList == null) {
            serviceList = new MutableLiveData<>();
        }
        return serviceList;
    }

    public void listServicios(boolean misServicios, boolean estado) {
        if (errorMessage == null) {
            errorMessage = new MutableLiveData<>();
        }
        errorMessage.setValue(null);
        SharedPreferences pref = getApplication().getSharedPreferences("DataVehiculo", Context.MODE_PRIVATE);
        int idVehiculo = pref.getInt("id_vehiculo", -1);
        if (idVehiculo == -1) {
            errorMessage.setValue("No se selecciono ningun vehiculo");
            return;
        }
        serviceApiService.listadoServicios(idVehiculo, misServicios, estado).enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (!response.isSuccessful()) {
                    errorMessage.setValue("Error al cargar los servicios");
                    serviceList.postValue(new java.util.ArrayList<>());
                    return;
                }
                List<Service> lista = response.body();
                if (lista == null || lista.isEmpty()) {
                    errorMessage.setValue("No se encontraron servicios");
                } else {
                    errorMessage.setValue(null);
                }
                serviceList.postValue(new ArrayList<>(lista));
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable t) {
                errorMessage.setValue("Error al cargar los servicios");
            }
        });

    }

    public void clearErrorMessage() {
        if (errorMessage != null) {
            errorMessage.setValue(null);
        }
    }

}