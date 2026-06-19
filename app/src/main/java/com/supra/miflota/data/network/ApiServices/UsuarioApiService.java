package com.supra.miflota.data.network.ApiServices;

import com.supra.miflota.data.models.Usuario;
import com.supra.miflota.data.network.ApiResponsesHelpers.MessageResponse;
import com.supra.miflota.data.network.ApiResponsesHelpers.PagedResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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