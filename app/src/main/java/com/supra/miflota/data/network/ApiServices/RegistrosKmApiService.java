package com.supra.miflota.data.network.ApiServices;

import com.supra.miflota.data.models.RegistroKilometraje;
import com.supra.miflota.data.network.ApiResponsesHelpers.KmByPatenteResponse;
// Asegúrate de importar la clase de respuesta que te dejo más abajo
// import com.supra.miflota.data.models.RegistroPatenteResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RegistrosKmApiService {

    @GET("registro-kilometraje/{id}")
    Call<RegistroKilometraje> obtenerRegistroPorId(
            @Path("id") int id
    );

    @GET("registro-kilometraje")
    Call<List<RegistroKilometraje>> obtenerListadoDeRegistros(
            @Query("misRegistros") boolean misRegistros,
            @Query("estado") boolean estado,
            @Query("idVehiculo") int idVehiculo
    );

    @GET("registro-kilometraje/latest/{idVehiculo}")
    Call<RegistroKilometraje> obtenerUltimoRegistroPorVehiculo(
            @Path("idVehiculo") int idVehiculo
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

    @DELETE("registro-kilometraje/{id}")
    Call<Void> eliminarRegistro(
            @Path("id") int id
    );

    @PATCH("registro-kilometraje/baja/{id}")
    Call<Void> darDeBajaRegistro(
            @Path("id") int id
    );

    @PATCH("registro-kilometraje/alta/{id}")
    Call<Void> restaurarRegistro(
            @Path("id") int id
    );

    @GET("registro-kilometraje/listado/{patente}")
    Call<List<KmByPatenteResponse>> obtenerRegistrosPorPatente(
            @Path("patente") String patente,
            @Query("nroPagina") int nroPagina,
            @Query("tamanoPagina") int tamanoPagina
    );
}
