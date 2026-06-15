package com.supra.miflota.data.network.ApiServices;
import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.data.network.ApiResponsesHelpers.PagedResponse;

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
public interface VehiculoApiService {


    @GET("vehiculos")
    Call<PagedResponse<Vehiculo>> obtenerVehiculos(
            @Query("nroPagina") int nroPagina,
            @Query("tamanoPagina") int tamanoPagina,
            @Query("estado") Boolean estado
    );

    @GET("vehiculos/{id}")
    Call<Vehiculo> obtenerVehiculoPorId(
            @Path("id") int id
    );

    @POST("vehiculos")
    Call<Vehiculo> crearVehiculo(
            @Body Vehiculo vehiculo
    );

    @PUT("vehiculos/{id}")
    Call<Void> actualizarVehiculo(
            @Path("id") int id,
            @Body Vehiculo vehiculo
    );

    @DELETE("vehiculos/{id}")
    Call<Void> eliminarVehiculo(
            @Path("id") int id
    );
    @PATCH("vehiculos/baja/{id}")
    Call<Void> darDeBajaVehiculo(
            @Path("id") int id
    );
    @PATCH("vehiculos/alta/{id}")
    Call<Void> darDeAltaVehiculo(
            @Path("id") int id
    );
    @GET("vehiculos/buscarPorPatente/{patente}")
    Call<Vehiculo> buscarVehiculoPorPatente(
            @Path("patente") String patente
    );
    @GET("vehiculos/buscarPorPatenteLike/{patente}")
    Call<List<Vehiculo>> buscarVehiculoPorPatenteLike(
            @Path("patente") String patente
    );
    @PUT("vehiculos/asignarMatafuego/{idVehiculo}/{idMatafuego}")
    Call<Void> asignarMatafuegoAVehiculo(
            @Path("idVehiculo") int idVehiculo,
            @Path("idMatafuego") int idMatafuego
    );
}