package com.supra.miflota.ui.kilometraje;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.MutableBoolean;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.supra.miflota.data.models.RegistroKilometraje;
import com.supra.miflota.data.network.ApiClient;
import com.supra.miflota.data.network.ApiResponsesHelpers.ApiErrorResponse;
import com.supra.miflota.data.network.ApiServices.RegistrosKmApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KilometrajeViewModel extends AndroidViewModel {

    // ==========================================
    // 1. VARIABLES DE ESTADO Y DATOS (LiveData)
    // ==========================================

    // Almacena el registro actual que se está visualizando/editando
    private MutableLiveData<RegistroKilometraje> registroKilometrajeMutableLiveData = new MutableLiveData<>();

    // Almacena los mensajes de error para mostrarlos en la UI (ej. Toasts)
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    // Banderas (Flags) que controlan el comportamiento de la interfaz (Fragment)
    private MutableLiveData<Boolean> isCrear = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isEdicion = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> isOnlyView = new MutableLiveData<>(false);

    // Servicio de Retrofit para llamadas a la API
    private RegistrosKmApiService apiService;

    // ==========================================
    // 2. CONSTRUCTOR
    // ==========================================
    public KilometrajeViewModel(@NonNull Application application) {
        super(application);
        // Inicializamos Retrofit pasando el contexto de la aplicación
        apiService = ApiClient.getClient(application.getApplicationContext()).create(RegistrosKmApiService.class);
    }

    // ==========================================
    // 3. GETTERS Y SETTERS (Exposición a la UI)
    // ==========================================
    public void setRegistroKilometrajeMutableLiveData(RegistroKilometraje registroKilometraje) {
        this.registroKilometrajeMutableLiveData.setValue(registroKilometraje);
    }
    public LiveData<RegistroKilometraje> getRegistroKilometrajeMutableLiveData() {
        return registroKilometrajeMutableLiveData;
    }

    public LiveData<String> getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage.setValue(errorMessage); }

    public void setIsCrear(boolean isCrear) { this.isCrear.setValue(isCrear); }
    public LiveData<Boolean> getIsCrear() { return isCrear; }

    public void setIsEdicion(boolean isEdicion) { this.isEdicion.setValue(isEdicion); }
    public LiveData<Boolean> getIsEdicion() { return isEdicion; }

    public void setIsOnlyView(boolean isOnlyView) { this.isOnlyView.setValue(isOnlyView); }
    public LiveData<Boolean> getIsOnlyView() { return isOnlyView; }

    // ==========================================
    // 4. LÓGICA DE NAVEGACIÓN Y CONFIGURACIÓN
    // ==========================================

    /**
     * Recibe el Bundle desde el Fragment y determina en qué modo se debe abrir la pantalla.
     * Si el Bundle trae un registro, pasamos a modo Lectura/Edición.
     * Si es nulo, significa que venimos a Crear un registro nuevo.
     */
    public void descomprimirBundle(Bundle bundle){
        if(bundle != null){
            RegistroKilometraje registroKilometraje = (RegistroKilometraje) bundle.getSerializable("registroKilometraje");
            setearConfiguracion(registroKilometraje);
            setRegistroKilometrajeMutableLiveData(registroKilometraje);
        }else {
            isCrear.setValue(true); // Modo: Nuevo Registro
        }
    }

    /**
     * Evalúa si el usuario actual tiene permisos para editar el registro recibido.
     * Activa el modo Edición si es el dueño, de lo contrario activa Solo Vista.
     */
    public void setearConfiguracion(RegistroKilometraje registroKilometraje){
        if(registroKilometraje.isCurrentUser())
            isEdicion.setValue(true);
        else
            isOnlyView.setValue(true);
    }

    // ==========================================
    // 5. LLAMADAS A LA API (Retrofit)
    // ==========================================

    /**
     * Actualiza un registro existente en el servidor.
     */
    public void editarKilometraje(String kilometrajeEnviado, Boolean estadoEnviado){

        // 1. Validación de entrada
        if(kilometrajeEnviado.isEmpty()){
            setErrorMessage("El kilometraje no puede estar vacio");
            return;
        }
        // 2. Preparación de datos
        int kilometrajeParsed;
        try{
            kilometrajeParsed = Integer.parseInt(kilometrajeEnviado);
        }catch(NumberFormatException ex){
            setErrorMessage("Por favor, ingrese un número de kilometraje válido.");
            return;
        }
        RegistroKilometraje registroKilometrajeNew = registroKilometrajeMutableLiveData.getValue();
        if(registroKilometrajeNew == null){
            setErrorMessage("Error interno: No se encontraron los datos del registro");
            return;
        }
        registroKilometrajeNew.setKilometraje(kilometrajeParsed);
        registroKilometrajeNew.setEstado(estadoEnviado);

        // 3. Petición a la API
        apiService.actualizarRegistro(registroKilometrajeNew.getIdRegistroKilometraje(),
                registroKilometrajeNew).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    // Éxito: Actualizamos UI y mantenemos el modo Edición
                    setRegistroKilometrajeMutableLiveData(registroKilometrajeNew);
                    setIsEdicion(true);
                    setIsCrear(false);
                    errorMessage.setValue("Registro actualizado exitosamente");
                }else{
                    // Error HTTP: Capturamos el error enviado por la API y lo parseamos con Gson
                    try{
                        String errorJson = response.errorBody().string();
                        Gson gson = new Gson();
                        ApiErrorResponse apiErrorResponse = gson.fromJson(errorJson, ApiErrorResponse.class);
                        setErrorMessage(apiErrorResponse.getMessage());
                    }catch (Exception e){
                        setErrorMessage("Ocurrio un error desconocido. Codigo: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Fallo crítico: Sin internet, timeout, etc.
                errorMessage.setValue("Error en el servidor");
            }
        });
    }

    /**
     * Crea un nuevo registro y lo envía al servidor.
     */
    public void crearKilometraje(String kilometraje){
        // 1. Validación de entrada
        if(kilometraje.isEmpty()){
            setErrorMessage("El kilometraje no puede estar vacio");
            return;
        }

        // 2. Construcción del nuevo objeto
        int kilometrajeParsed;
        try{
            kilometrajeParsed = Integer.parseInt(kilometraje);
        }catch(NumberFormatException ex){
            setErrorMessage("Por favor, ingrese un número de kilometraje válido.");
            return;
        }
        RegistroKilometraje registroKilometrajeNew = new RegistroKilometraje();
        registroKilometrajeNew.setKilometraje(kilometrajeParsed);

        // 3. Obtención del ID del vehículo asociado desde SharedPreferences
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("DataVehiculo", Application.MODE_PRIVATE);
        int idVehiculo = sharedPreferences.getInt("id_vehiculo", 0);
        registroKilometrajeNew.setIdVehiculo(idVehiculo);
        registroKilometrajeNew.setEstado(true);

        // 4. Petición a la API
        apiService.crearRegistro(registroKilometrajeNew).enqueue(new Callback<RegistroKilometraje>() {
            @Override
            public void onResponse(Call<RegistroKilometraje> call, Response<RegistroKilometraje> response) {
                if(response.isSuccessful()){
                    // Éxito: La API devuelve el objeto creado (seguramente con su nuevo ID).
                    RegistroKilometraje registroKilometraje = response.body();
                    if(registroKilometraje != null){
                        // Actualizamos el LiveData y cambiamos el modo de Crear a Edición
                        setRegistroKilometrajeMutableLiveData(registroKilometraje);
                        setIsEdicion(true);
                        setIsCrear(false);
                    }
                    errorMessage.setValue("Registro creado exitosamente");
                    return;
                }

                // Error HTTP: Capturamos el errorBody de la API
                try{
                    String errorJson = response.errorBody().string();
                    Gson gson = new Gson();
                    ApiErrorResponse apiErrorResponse = gson.fromJson(errorJson, ApiErrorResponse.class);
                    setErrorMessage(apiErrorResponse.getMessage());
                }catch (Exception e){
                    setErrorMessage("Ocurrio un error desconocido. Codigo: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RegistroKilometraje> call, Throwable t) {
                // Fallo crítico: Error de red o parseo
                errorMessage.setValue("Error al crear el registro");
            }
        });
    }
}