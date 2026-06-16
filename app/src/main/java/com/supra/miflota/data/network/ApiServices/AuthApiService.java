package com.supra.miflota.data.network.ApiServices;

// Asegúrate de importar tus DTOs y clases de respuesta
// import com.supra.miflota.data.models.LoginDto;
// import com.supra.miflota.data.models.RegisterDto;
// import com.supra.miflota.data.models.TokenResponse;
// import com.supra.miflota.data.models.MessageResponse;

import com.supra.miflota.data.network.ApiResponsesHelpers.TokenResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    /**
     *
     * @param credenciales
     * Se debe generar un mapa y agregar los campos de credenciales
     * Ejemplo:
     * Map<String, String> credenciales = new HashMap<>();
     * credenciales.put("gmail", "correo@gmail.com");
     * credenciales.put("contrasena", "123456");
     * @return
     */
    @POST("ControllerAuth/login")
    Call<TokenResponse> iniciarSesion(
            @Body Map<String,String> credenciales);
}