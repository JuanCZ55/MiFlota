package com.supra.miflota.ui.servicioLista;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supra.miflota.data.models.Service;
import com.supra.miflota.data.models.Status;
import com.supra.miflota.data.models.Vehiculo;
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
    private MutableLiveData<Status> statusMutable;
    private MutableLiveData<Vehiculo> vehiculoMutable;

    public ServicioListaViewModel(@NonNull Application application) {
        super(application);
        serviceApiService = ApiClient.getClient(application.getApplicationContext()).create(ServiceApiService.class);
        statusMutable = new MutableLiveData<>();
        vehiculoMutable = new MutableLiveData<>();
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

    public LiveData<Vehiculo> getVehiculoMutable() {
        return vehiculoMutable;
    }

    public LiveData<Status> getStatusMutable() {
        return statusMutable;
    }

    public void listServicios(boolean misServicios, boolean estado) {
        if (errorMessage == null) {
            errorMessage = new MutableLiveData<>();
        }
        errorMessage.setValue(null);

        int idVehiculo = vehiculoMutable.getValue().getIdVehiculo();
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

    public void cargarVehiculo() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DataVehiculo", Context.MODE_PRIVATE);

        if (!sharedPreferences.contains("id_vehiculo")) {
            errorMessage.setValue("No se pudo cargar la información del vehiculo.");
            return;
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(sharedPreferences.getInt("id_vehiculo", -1));
        vehiculo.setPatente(sharedPreferences.getString("patente", null));
        vehiculo.setMarca(sharedPreferences.getString("marca", null));
        vehiculo.setModelo(sharedPreferences.getString("modelo", null));

        if (vehiculo.getIdVehiculo() == -1 || vehiculo.getIdVehiculo() == 0) {
            errorMessage.setValue("No se pudo cargar la información del vehiculo.");
            return;
        }

        vehiculoMutable.postValue(vehiculo);

        setFiltros(null, null);
    }

    public void dispararBusquedaRevisiones() {
        Status status = statusMutable.getValue();
        if (status == null) {
            status = new Status(false, true);
        }

        listServicios(status.isAll(), status.isActive());
    }

    public void setFiltros(Boolean isAll, Boolean isActive) {
        Status statusUpdate = statusMutable.getValue();
        if (statusUpdate == null) {
            statusUpdate = new Status(false, true);
        }
        if (isAll != null) {
            statusUpdate.setAll(isAll);
        }
        if (isActive != null) {
            statusUpdate.setActive(isActive);
        }
        statusMutable.postValue(statusUpdate);
    }

    public void clearErrorMessage() {
        if (errorMessage != null) {
            errorMessage.setValue(null);
        }
    }

}