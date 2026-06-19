package com.supra.miflota.ui.perfil;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.UsuarioApiService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordViewModel extends AndroidViewModel {
    private MutableLiveData<String> msj;
    private UsuarioApiService usuarioApiService;
    private MutableLiveData<Boolean> operacionExitosa;

    public PasswordViewModel(@NonNull Application application) {
        super(application);
        msj = new MutableLiveData<>();
        usuarioApiService = ApiClient.getClient(application.getApplicationContext()).create(UsuarioApiService.class);

        operacionExitosa = new MutableLiveData<>();
    }

    public LiveData<String> getMsj() {
        return msj;
    }

    public LiveData<Boolean> getOperacionExitosa() {
        return operacionExitosa;
    }

    public void cambiarPassword(String currentPass, String newPass) {
        if (currentPass.isEmpty() || newPass.isEmpty()) {
            msj.setValue("Ingresa una contraseña");
            return;
        }
        if (currentPass.length() < 8) {
            msj.setValue("La contraseña debe tener al menos 8 caracteres");
            return;
        }
        if (newPass.length() < 8) {
            msj.setValue("Debe tener al menos 8 caracteres");
            return;
        }
        if (currentPass.equals(newPass)) {
            msj.setValue("La nueva contraseña no puede ser igual a la actual");
            return;
        }
        if (!newPass.matches(".*[a-z].*")) {
            msj.setValue("Debe incluir al menos una minúscula");
            return;
        }
        if (!newPass.matches(".*[A-Z].*")) {
            msj.setValue("Debe incluir al menos una mayúscula");
            return;
        }
        if (!newPass.matches(".*\\d.*")) {
            msj.setValue("Debe incluir al menos un número");
            return;
        }
        if (!newPass.matches(".*[\\W_].*")) {
            msj.setValue("Debe incluir un carácter especial (@, #, $, _)");
            return;
        }
        HashMap<String, String> bodyDatos = new HashMap<>();
        bodyDatos.put("NewPassword", newPass);
        bodyDatos.put("currentPassword", currentPass);
        usuarioApiService.updatePassword(bodyDatos).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    msj.setValue(parseError(response));
                    return;
                }
                msj.setValue("Contraseña actualizada");
                operacionExitosa.setValue(true);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                msj.setValue("Error de conexion");
            }
        });
    }

    public void limpiarOperacionExitosa() {
        operacionExitosa.setValue(false);
    }

    public void limpiarMsj() {
        msj.setValue(null);
    }

    private String parseError(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                String errorString = response.errorBody().string();
                com.supra.miflota.data.network.ApiResponsesHelpers.MessageResponse errorObj = new com.google.gson.Gson().fromJson(errorString, com.supra.miflota.data.network.ApiResponsesHelpers.MessageResponse.class);
                if (errorObj != null && errorObj.getMessage() != null) {
                    return errorObj.getMessage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Ocurrió un error inesperado.";
    }

}