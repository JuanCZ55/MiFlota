package com.supra.miflota.ui.kilometrajeLista;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.supra.miflota.data.models.RegistroKilometraje;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiServices.RegistrosKmApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KilometrajeListaViewModel extends AndroidViewModel {

    MutableLiveData<List<RegistroKilometraje>> listaRegistroKilometraje = new MutableLiveData<>();
    MutableLiveData<String> errorMessage = new MutableLiveData<>();
    RegistrosKmApiService registrosKmApiService;
    MutableLiveData<Boolean> todosOPropios = new MutableLiveData<Boolean>(false);
    MutableLiveData<Boolean> activoYInactivo = new MutableLiveData<Boolean>(true);
    public KilometrajeListaViewModel(@NonNull Application application) {
        super(application);
        registrosKmApiService = ApiClient.getClient(application.getApplicationContext()).create(RegistrosKmApiService.class);
    }

    public LiveData<List<RegistroKilometraje>> getListaRegistroKilometraje() {
        return listaRegistroKilometraje;
    }

    public void setListaRegistroKilometraje(List<RegistroKilometraje> listaRegistroKilometraje) {
        this.listaRegistroKilometraje.setValue(listaRegistroKilometraje);
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.setValue(errorMessage);
    }
    public LiveData<Boolean> getTodosOPropios() {
        return todosOPropios;
    }
    public void setTodosOPropios() {

        this.todosOPropios.setValue(!todosOPropios.getValue());
    }
    public LiveData<Boolean> getActivoYInactivo() {
        return activoYInactivo;
    }
    public void setActivoYInactivo() {
        this.activoYInactivo.setValue(!activoYInactivo.getValue());
    }
    public void cargarLista(){
        SharedPreferences pref = getApplication().getSharedPreferences("DataVehiculo",
                Context.MODE_PRIVATE);
        int idVehiculo = pref.getInt("id_vehiculo", 0);

        registrosKmApiService.obtenerListadoDeRegistros(todosOPropios.getValue(),
            activoYInactivo.getValue(),
                idVehiculo).enqueue(new Callback<List<RegistroKilometraje>>() {
            @Override
            public void onResponse(Call<List<RegistroKilometraje>> call, Response<List<RegistroKilometraje>> response) {
                if(!response.isSuccessful()){
                    errorMessage.setValue("Ocurrio un error al cargar los registros");
                    return;
                }
                List<RegistroKilometraje> registroKilometrajeList = response.body();
                if(registroKilometrajeList == null){
                    errorMessage.setValue("No se encontraron registros");
                    return;
                }
                listaRegistroKilometraje.postValue(registroKilometrajeList);
                return;
            }

            @Override
            public void onFailure(Call<List<RegistroKilometraje>> call, Throwable t) {
                errorMessage.setValue("Error de conexión: " + t.getMessage());
            }
        });

    }
    public DataGraficos generarDatosParaGraficos(){
        DataGraficos dataGraficos = new DataGraficos();
        try{
            ArrayList<String> fechasAL = new ArrayList<>();
            ArrayList<Object> valoresAL = new ArrayList<>();
            List<RegistroKilometraje> copyLista = listaRegistroKilometraje.getValue();
            for(int i = copyLista.size() - 1; i >= 0; i--){
                fechasAL.add(copyLista.get(i).getFechaRegistro().split("T")[0]);
                valoresAL.add(copyLista.get(i).getKilometraje());
            }
            String[] fechas = fechasAL.toArray(new String[0]);
            Object[] valores = valoresAL.toArray(new Object[0]);
            dataGraficos = new DataGraficos(fechas,valores);
        }catch (Exception e){
            errorMessage.setValue("Error al cargar los datos para el gráfico");
            return null;
        }
        return dataGraficos;
    }
    public class DataGraficos{
        private String[] fechas;
        private Object[] valores;
        public DataGraficos() {
        }
        public DataGraficos(String[] fechas, Object[] valores) {
            this.fechas = fechas;
            this.valores = valores;
        }
        public String[] getFechas() {
            return fechas;
        }
        public void setFechas(String[] fechas) {
            this.fechas = fechas;
        }
        public Object[] getValores() {
            return valores;
        }
        public void setValores(Object[] valores) {
            this.valores = valores;
        }
    }
}