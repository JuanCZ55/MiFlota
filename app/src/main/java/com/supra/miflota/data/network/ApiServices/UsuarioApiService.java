package com.supra.miflota.data.network.ApiServices;

import com.supra.miflota.data.models.Usuario;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface UsuarioApiService {
    // PUT CAMBIAR CONTRASEÑA

    /**
     *
     * @param bodyDatos HashMap<String, String> bodyDatos = new HashMap<>();
     *                  bodyDatos.put("NewPassword", newPass);
     *                  bodyDatos.put("currentPassword", currentPass);
     * @return
     */
    @PUT("usuarios/password")
    Call<Void> updatePassword(@Body HashMap<String, String> bodyDatos);

    @PUT("usuarios/email")
    Call<ResponseBody> updateEmail(
            @Body String emailDto
    );
    @GET("usuarios/getMe")
    Call<Usuario> getUsuario();
}