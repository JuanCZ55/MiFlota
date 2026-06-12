package com.supra.miflota.data.models;

import java.util.List;
import java.util.ArrayList;

public class Vehiculo {
    private int idVehiculo;
    private String marca;
    private String modelo;
    private int anio;
    private String patente;
    private String color;
    private int cantidadNeumaticos;
    private int cantidadAuxilios;
    private String numeroChasis;
    private String numeroMotor;
    // Usamos Integer porque en la BD admite nulos (int?)
    private Integer idMatafuego;
    private Matafuego matafuego;
    private List<ChecklistDiario> checklistsDiarios;
    private List<RegistroKilometraje> registrosKilometraje;
    private List<Service> services;
    private boolean estado;

    public Vehiculo() {
    }

    public Vehiculo(String marca, String modelo, int anio, String patente, String color, int cantidadNeumaticos, int cantidadAuxilios, String numeroChasis, String numeroMotor, boolean estado) {
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.patente = patente;
        this.color = color;
        this.cantidadNeumaticos = cantidadNeumaticos;
        this.cantidadAuxilios = cantidadAuxilios;
        this.numeroChasis = numeroChasis;
        this.numeroMotor = numeroMotor;
        this.estado = estado;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCantidadNeumaticos() {
        return cantidadNeumaticos;
    }

    public void setCantidadNeumaticos(int cantidadNeumaticos) {
        this.cantidadNeumaticos = cantidadNeumaticos;
    }

    public int getCantidadAuxilios() {
        return cantidadAuxilios;
    }

    public void setCantidadAuxilios(int cantidadAuxilios) {
        this.cantidadAuxilios = cantidadAuxilios;
    }

    public String getNumeroChasis() {
        return numeroChasis;
    }

    public void setNumeroChasis(String numeroChasis) {
        this.numeroChasis = numeroChasis;
    }

    public String getNumeroMotor() {
        return numeroMotor;
    }

    public void setNumeroMotor(String numeroMotor) {
        this.numeroMotor = numeroMotor;
    }

    public Integer getIdMatafuego() {
        return idMatafuego;
    }

    public void setIdMatafuego(Integer idMatafuego) {
        this.idMatafuego = idMatafuego;
    }

    public Matafuego getMatafuego() {
        return matafuego;
    }

    public void setMatafuego(Matafuego matafuego) {
        this.matafuego = matafuego;
    }

    public List<ChecklistDiario> getChecklistsDiarios() {
        return checklistsDiarios;
    }

    public void setChecklistsDiarios(List<ChecklistDiario> checklistsDiarios) {
        this.checklistsDiarios = checklistsDiarios;
    }

    public List<RegistroKilometraje> getRegistrosKilometraje() {
        return registrosKilometraje;
    }

    public void setRegistrosKilometraje(List<RegistroKilometraje> registrosKilometraje) {
        this.registrosKilometraje = registrosKilometraje;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
