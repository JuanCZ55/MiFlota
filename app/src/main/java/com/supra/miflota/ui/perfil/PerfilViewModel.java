package com.supra.miflota.ui.perfil;

import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.supra.miflota.data.models.Usuario;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.UsuarioApiService;

import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private UsuarioApiService usuarioApiService;
    private MutableLiveData<Usuario> usuario;
    private MutableLiveData<String> msj;
    private MutableLiveData<Boolean> operationSuccess = new MutableLiveData<>();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        usuarioApiService = ApiClient.getClient(application.getApplicationContext()).create(UsuarioApiService.class);
        usuario = new MutableLiveData<>();
        msj = new MutableLiveData<>();
        operationSuccess = new MutableLiveData<>();

    }

    public LiveData<String> getMsj() {
        return msj;
    }

    public LiveData<Usuario> getUsuario() {
        return usuario;
    }

    public LiveData<Boolean> getOperationSuccess() {
        return operationSuccess;
    }

    public void cargarUsuario() {
        usuarioApiService.getUsuario().enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (!response.isSuccessful()) {
                    msj.setValue("No se pudo cargar el usuario");
                    return;
                }
                usuario.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                msj.setValue("Error de conexion");
            }
        });
    }

    public void actualizarUsuario(String email) {
        if (email.isEmpty()) {
            msj.setValue("Ingresa un email");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            msj.setValue("Email Incorrecto");
            return;
        }
        usuarioApiService.updateEmail(email).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    msj.setValue("No se pudo actualizar el email");
                    return;
                }
                msj.setValue("Email actualizado");
                operationSuccess.setValue(true);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                msj.setValue("Error de conexion");
            }
        });
    }

    public void limpiarMsj() {
        msj.setValue(null);
    }

    public void limpiarBoolean() {
        operationSuccess.setValue(false);
    }
}