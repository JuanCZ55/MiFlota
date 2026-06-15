package com.supra.miflota.data.network.ApiResponsesHelpers;

import java.util.Date;

public class KmByPatenteResponse {
    private int idRegistroPatente;
    private int idVehiculo;
    private int kilometraje;
    private Date fechaRegistro;
    private boolean estado;
    private String marca;
    private String modelo;
    private String patente;
    private int nroPagina;
    private int totalPaginasCalculadas;

    public KmByPatenteResponse() {
    }

    public KmByPatenteResponse(int idRegistroPatente, int idVehiculo, int kilometraje, Date fechaRegistro, boolean estado, String marca, String modelo, String patente, int nroPagina, int totalPaginasCalculadas) {
        this.idRegistroPatente = idRegistroPatente;
        this.idVehiculo = idVehiculo;
        this.kilometraje = kilometraje;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.nroPagina = nroPagina;
        this.totalPaginasCalculadas = totalPaginasCalculadas;
    }

    public int getIdRegistroPatente() {
        return idRegistroPatente;
    }

    public void setIdRegistroPatente(int idRegistroPatente) {
        this.idRegistroPatente = idRegistroPatente;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(int kilometraje) {
        this.kilometraje = kilometraje;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public int getNroPagina() {
        return nroPagina;
    }

    public void setNroPagina(int nroPagina) {
        this.nroPagina = nroPagina;
    }

    public int getTotalPaginasCalculadas() {
        return totalPaginasCalculadas;
    }

    public void setTotalPaginasCalculadas(int totalPaginasCalculadas) {
        this.totalPaginasCalculadas = totalPaginasCalculadas;
    }
}
