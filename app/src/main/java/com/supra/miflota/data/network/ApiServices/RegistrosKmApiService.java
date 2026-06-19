package com.supra.miflota.data.network.ApiServices;

import com.supra.miflota.data.models.RegistroKilometraje;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RegistrosKmApiService {
    @GET("registro-kilometraje")
    Call<List<RegistroKilometraje>> obtenerListadoDeRegistros(
            @Query("misRegistros") boolean misRegistros,
            @Query("estado") boolean estado,
            @Query("idVehiculo") int idVehiculo
    );

    @POST("registro-kilometraje")
    Call<RegistroKilometraje> crearRegistro(
            @Body RegistroKilometraje registro
    );

    @PUT("registro-kilometraje/{id}")
    Call<Void> actualizarRegistro(
            @Path("id") int id,
            @Body RegistroKilometraje registro
    );
}
