package com.supra.miflota.data.models;

import java.io.Serializable;

public class RegistroKilometraje implements Serializable {
    private int idRegistroKilometraje;
    private int kilometraje;
    private String fechaRegistro;
    private boolean estado;
    private int idVehiculo;
    private Vehiculo vehiculo;

    public RegistroKilometraje() {
    }

    public RegistroKilometraje(int idVehiculo, String fechaRegistro, int kilometraje, boolean estado) {
        this.idVehiculo = idVehiculo;
        this.fechaRegistro = fechaRegistro;
        this.kilometraje = kilometraje;
        this.estado = estado;
    }

    public int getIdRegistroKilometraje() {
        return idRegistroKilometraje;
    }

    public void setIdRegistroKilometraje(int idRegistroKilometraje) {
        this.idRegistroKilometraje = idRegistroKilometraje;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
