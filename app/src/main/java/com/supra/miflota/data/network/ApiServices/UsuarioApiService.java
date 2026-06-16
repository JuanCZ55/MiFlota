package com.supra.miflota.data.network;

import com.supra.miflota.data.models.Usuario;
import com.supra.miflota.data.network.ApiResponsesHelpers.MessageResponse;
import com.supra.miflota.data.network.ApiResponsesHelpers.PagedResponse;

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

    // GET TODOS LOS USUARIOS
    @GET("api/usuarios")
    Call<PagedResponse<Usuario>> obtenerTodosLosUsuarios(
            @Query("numeroPagina") int numeroPagina,
            @Query("tamanoPagina") int tamanoPagina
    );

    @GET("api/usuarios/{id}")
    Call<Usuario> obtenerUsuarioPorId(
            @Path("id") int id
    );


    // PUT ACTUALIZAR USUARIO
    @PUT("api/usuarios/{id}")
    Call<Void> actualizarUsuario(
            @Path("id") int id,
            @Body Usuario usuario
    );

    // PUT RESET PASSWORD
    @PUT("api/usuarios/reset/{id}")
    Call<MessageResponse> resetearPassword(
            @Path("id") int id
    );

    // PUT CAMBIAR CONTRASEÑA

    /**
     *
     * @param id
     * @param passwordDto
     * Se debe crear un mapa y agregar los campos de contraseña
     * Map<String, String> body = new HashMap<>();
     * body.put("NewPassword", "nuevaContraseña");
     * body.put("currentPassword", "contraseñaAntigua");
     * @return
     */
    @PUT("api/usuarios/cambiarContrasena/{id}")
    Call<MessageResponse> cambiarContrasena(
            @Path("id") int id,
            @Body Map<String,String> passwordDto
    );

    /**
     *
     * @param id
     * @param emailDto
     * Se debe crear un hashmap y agregar el campo "Gmail"
     * Map<String, String> body = new HashMap<>();
     * body.put("gmail", "nuevo_correo@gmail.com");
     * @return
     */
    @PUT("api/usuarios/email/{id}")
    Call<ResponseBody> actualizarEmail(
            @Path("id") int id,
            @Body Map<String,String> emailDto
    );

    // POST ACTUALIZAR AVATAR (Subida de archivo)
    @Multipart
    @POST("api/usuarios/avatar/{id}")
    Call<Void> actualizarAvatar(
            @Path("id") int id,
            @Part MultipartBody.Part avatar
    );

    // GET OBTENER AVATAR (Descarga de archivo/imagen)
    @GET("api/usuarios/avatar/{id}")
    Call<ResponseBody> obtenerAvatar(
            @Path("id") int id
    );
}