package com.supra.miflota.data.network.ApiServices;

import com.supra.miflota.data.models.ChecklistDiario;

import java.util.List;

import okhttp3.ResponseBody;
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
            @Query("estado") boolean estado
    );

    @POST("checklist")
    Call<ChecklistDiario> crearRevision(
            @Body ChecklistDiario revision
    );

    @PUT("checklist/{id}")
    Call<ResponseBody> editarRevision(
            @Path("id") int id,
            @Body ChecklistDiario revision
    );

}


