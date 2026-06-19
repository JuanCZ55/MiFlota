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
    @POST("mantenimiento")
    Call<Service> crearServicio(@Body Service service);

    @PUT("mantenimiento/{id}")
    Call<Service> actualizarServicio(@Path("id") int id, @Body Service service);

    @GET("mantenimiento/getlistadoservicio/{idVehiculo}")
    Call<List<Service>> listadoServicios(@Path("idVehiculo") int id, @Query("misRegistros") boolean misRegistros, @Query("estado") boolean estado);


}