package com.supra.miflota.ui.servicio;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supra.miflota.data.models.Service;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.ServiceApiService;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioViewModel extends AndroidViewModel {
    private ServiceApiService servicioApiService;
    private MutableLiveData<Service> service;
    private MutableLiveData<String> msj;
    private MutableLiveData<Integer> modo;

    /// 0 Crear - 1 Editar - 2 Ver

    private MutableLiveData<Boolean> operationSuccess = new MutableLiveData<>();


    public ServicioViewModel(@NonNull Application application) {
        super(application);
        servicioApiService = ApiClient.getClient(application.getApplicationContext()).create(ServiceApiService.class);
        service = new MutableLiveData<>();
        msj = new MutableLiveData<>();
        modo = new MutableLiveData<>();
    }

    public LiveData<Service> getService() {
        return service;
    }

    public LiveData<String> getMsj() {
        return msj;
    }


    public LiveData<Integer> getModo() {
        return modo;
    }

    public LiveData<Boolean> getOperationSuccess() {
        return operationSuccess;
    }
//cosas del bundle
    public void initialize(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("servicio")) {
            modo.setValue(0);//modo crear
            service.setValue(new Service());
            return;
        }
        Service servicio = (Service) bundle.getSerializable("servicio");
        if (servicio == null) {
            msj.setValue("No se pudo cargar el servicio.");
            return;
        }
        if (servicio.isCurrentUser()) {
            modo.setValue(1);//modo editar
        } else {
            modo.setValue(2);//modo ver
        }
        service.setValue(servicio);
    }
//crear servicio
    public void crearServicio(Service s) {
        SharedPreferences pref = getApplication().getSharedPreferences("DataVehiculo", Context.MODE_PRIVATE);
        int idVehiculo = pref.getInt("id_vehiculo", -1);
        if (idVehiculo == -1) {
            msj.setValue("No se selecciono ningun vehiculo");
            return;
        }
        s.setIdVehiculo(idVehiculo);

        servicioApiService.crearServicio(s).enqueue(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                if (response.isSuccessful()) {
                    operationSuccess.setValue(true);
                } else {
                    msj.setValue(parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                msj.setValue("Error de conexion");
            }
        });
    }
//actualizar servicio
    public void actualizarServicio(Service s) {
        servicioApiService.actualizarServicio(s.getIdService(), s).enqueue(new Callback<Service>() {
            @Override
            public void onResponse(Call<Service> call, Response<Service> response) {
                if (response.isSuccessful()) {
                    operationSuccess.setValue(true);
                } else {
                    msj.setValue(parseError(response));
                }
            }

            @Override
            public void onFailure(Call<Service> call, Throwable t) {
                msj.setValue("Error de conexion");
            }
        });
    }
//funcion auxiliar para parsear el error
    private String parseError(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                String errorString = response.errorBody().string();
                JSONObject jsonObject = new JSONObject(errorString);
                if (jsonObject.has("message")) {
                    return jsonObject.getString("message");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ocurrió un error inesperado.";
    }
}