package com.supra.miflota.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiResponsesHelpers.ApiErrorResponse;
import com.supra.miflota.data.network.ApiResponsesHelpers.TokenResponse;
import com.supra.miflota.data.network.ApiServices.AuthApiService;
import com.supra.miflota.ui.menu.MenuActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private Context context;
    private AuthApiService authApiService;
    public LoginViewModel(@NonNull Application application)
    {
        super(application);
        context = application.getApplicationContext();
        authApiService = ApiClient.getClient(context).create(AuthApiService.class);
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(MutableLiveData<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
    public void login(String gmail, String password){
        if(gmail.isEmpty() || password.isEmpty()){
            errorMessage.setValue("Por favor ingrese su correo y contraseña");
        }
        Map<String,String> credenciales = new HashMap<>();
        credenciales.put("gmail",gmail);
        credenciales.put("contrasena",password);
        authApiService.iniciarSesion(credenciales).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (!response.isSuccessful() ) {

                    errorMessage.setValue("Gmail o Contraseña incorrectos");
                    return;
                } else {
                    TokenResponse tokenResponse = response.body();
                    if (tokenResponse.getToken() == null || tokenResponse.getToken().isEmpty()) {
                        errorMessage.setValue("Error de el servidor");
                        return;
                    }
                    ApiClient.saveToken(context, tokenResponse.getToken());
                    //Hacemos el intent y mandamos a la proxima vista
                    Intent intent = new Intent(getApplication(), MenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                errorMessage.setValue("Error de conexión con el Servidor " );
            }
        });
    }
}