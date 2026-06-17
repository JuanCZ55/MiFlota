package com.supra.miflota.data.network.ApiServices;

import com.supra.miflota.data.models.ChecklistDiario;
import com.supra.miflota.data.models.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RevisionApiService {

    @GET("/checklist/{idVehiculo}")
    Call<List<ChecklistDiario>> listaRevisiones(
            @Path("idVehiculo") int idVehiculo,
            @Query("misRegistros") boolean misRegistros,
            @Query("estado") boolean estado,
            @Header("Authorization") String token
    );


}


