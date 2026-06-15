package com.supra.miflota.data.network.ApiServices;

import com.supra.miflota.data.models.Service;
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

public interface ServiceApiService {

    @GET("api/mantenimiento")
    Call<PagedResponse<Service>> obtenerTodosLosServicios(
            @Query("numeroPagina") int numeroPagina,
            @Query("tamanoPagina") int tamanoPagina
    );
    @GET("GetListadoRegistrosService")
    Call<List<Service>> obtenerListadoDeRegistros(
            @Query("misRegistros") boolean misRegistros,
            @Query("estado") boolean estado
    );

    @GET("mantenimiento/{id}")
    Call<Service> obtenerServicioPorId(
            @Path("id") int id
    );

    @POST("mantenimiento")
    Call<Service> crearServicio(
            @Body Service service
    );

    @PUT("mantenimiento/{id}")
    Call<Void> actualizarServicio(
            @Path("id") int id,
            @Body Service service
    );

    @DELETE("mantenimiento/{id}")
    Call<Void> eliminarServicio(
            @Path("id") int id
    );

    @PATCH("mantenimiento/baja/{id}")
    Call<Void> darDeBajaServicio(
            @Path("id") int id
    );

    @PATCH("mantenimiento/alta/{id}")
    Call<Void> restaurarServicio(
            @Path("id") int id
    );

    @GET("mantenimiento/vehiculo/{vehiculoId}")
    Call<PagedResponse<Service>> obtenerServiciosPorVehiculo(
            @Path("vehiculoId") int vehiculoId,
            @Query("nroPagina") int nroPagina,
            @Query("tamanoPagina") int tamanoPagina
    );

    @PATCH("mantenimiento/servicio/resuelto/{id}")
    Call<Void> marcarServicioResuelto(
            @Path("id") int id
    );

    @PATCH("mantenimiento/servicio/no/resuelto/{id}")
    Call<Void> marcarServicioComoNoResuelto(
            @Path("id") int id
    );
}