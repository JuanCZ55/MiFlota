package com.supra.miflota.data.network.ApiServices;

import com.supra.miflota.data.models.ChecklistDiario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RevisionApiService {

    @GET("checklist/limited/{idVehiculo}")
    Call<List<ChecklistDiario>> listaRevisiones(
            @Path("idVehiculo") int idVehiculo,
            @Query("misRegistros") boolean misRegistros,
            @Query("estado") boolean estado,
            @Header("Authorization") String token
    );

    @POST("checklist")
    Call<ChecklistDiario> crearRevision(
            @Header("Authorization") String token,
            @Body ChecklistDiario revision
    );

    @PUT("checklist/{idChecklistDiario}")
    Call<ChecklistDiario> editarRevision(
            @Header("Authorization") String token,
            @Path("idChecklistDiario") int idChecklistDiario,
            @Body ChecklistDiario revision
    );

}


