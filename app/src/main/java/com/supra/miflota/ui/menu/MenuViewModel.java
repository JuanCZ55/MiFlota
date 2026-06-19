package com.supra.miflota.ui.menu;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supra.miflota.data.models.ChecklistDiario;
import com.supra.miflota.data.models.Usuario;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.UsuarioApiService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> usuarioMutable;
    private MutableLiveData<String> errorMessageMutable;
    private UsuarioApiService service;

    public MenuViewModel(@NonNull Application application) {
        super(application);
        usuarioMutable = new MutableLiveData<>();
        service = ApiClient.getClient(application.getApplicationContext()).create(UsuarioApiService.class);
    }

    public LiveData<Usuario> getUsuario() {
        return usuarioMutable;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessageMutable;
    }


    public void cargarUsuario(){
        Call<Usuario> call = service.getUsuario();
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(!response.isSuccessful()){
                    errorMessageMutable.setValue("Ocurrio un error obtener el usuario");
                    return;
                }

                Usuario usuario = response.body();
                if(usuario == null){
                    errorMessageMutable.setValue("No se encontraron usuario");
                    return;
                }

                usuarioMutable.postValue(usuario);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                errorMessageMutable.postValue("Error al usuario");
            }
        });
    }

}