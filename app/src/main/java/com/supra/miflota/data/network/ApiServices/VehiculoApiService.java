package com.supra.miflota.data.network.ApiServices;
import com.supra.miflota.data.models.Vehiculo;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VehiculoApiService {
    @GET("vehiculos/notPaginated")
    Call<List<Vehiculo>> obtenerVehiculos();

    @GET("vehiculos/obtener/{idVehiculo}")
    Call<Vehiculo> obtenerVehiculo(
            @Path("idVehiculo") int idVehiculo
    );
}