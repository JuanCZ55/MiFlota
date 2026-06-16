package com.supra.miflota.data.models;

import java.io.Serializable;

public class ChecklistDiario implements Serializable {
    private int idChecklistDiario;
    private boolean faroDelanteroIzquierdo;
    private boolean faroDelanteroDerecho;
    private boolean faroTraseroIzquierdo;
    private boolean faroTraseroDerecho;
    private boolean liquidoFrenos;
    private boolean nivelAceite;
    private boolean presionNeumaticos;
    private boolean nivelFrenos;
    private boolean nivelRefrigerante;
    private boolean nivelAguaParabrisas;
    private boolean matafuegoVigente;
    private String observaciones;
    private String fecha;
    private int idVehiculo;
    private Vehiculo vehiculo;
    private boolean estado;

    public ChecklistDiario() {
    }

    public ChecklistDiario(int idVehiculo, String fecha, boolean faroDelanteroIzquierdo, boolean faroDelanteroDerecho, boolean faroTraseroIzquierdo, boolean faroTraseroDerecho, boolean liquidoFrenos, boolean nivelAceite, boolean presionNeumaticos, boolean nivelFrenos, boolean matafuegoVigente, boolean nivelRefrigerante, boolean nivelAguaParabrisas, String observaciones, boolean estado) {
        this.idVehiculo = idVehiculo;
        this.fecha = fecha;
        this.faroDelanteroIzquierdo = faroDelanteroIzquierdo;
        this.faroDelanteroDerecho = faroDelanteroDerecho;
        this.faroTraseroIzquierdo = faroTraseroIzquierdo;
        this.faroTraseroDerecho = faroTraseroDerecho;
        this.liquidoFrenos = liquidoFrenos;
        this.nivelAceite = nivelAceite;
        this.presionNeumaticos = presionNeumaticos;
        this.nivelFrenos = nivelFrenos;
        this.matafuegoVigente = matafuegoVigente;
        this.nivelRefrigerante = nivelRefrigerante;
        this.nivelAguaParabrisas = nivelAguaParabrisas;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public int getIdChecklistDiario() {
        return idChecklistDiario;
    }

    public void setIdChecklistDiario(int idChecklistDiario) {
        this.idChecklistDiario = idChecklistDiario;
    }

    public boolean isFaroDelanteroIzquierdo() {
        return faroDelanteroIzquierdo;
    }

    public void setFaroDelanteroIzquierdo(boolean faroDelanteroIzquierdo) {
        this.faroDelanteroIzquierdo = faroDelanteroIzquierdo;
    }

    public boolean isFaroDelanteroDerecho() {
        return faroDelanteroDerecho;
    }

    public void setFaroDelanteroDerecho(boolean faroDelanteroDerecho) {
        this.faroDelanteroDerecho = faroDelanteroDerecho;
    }

    public boolean isFaroTraseroIzquierdo() {
        return faroTraseroIzquierdo;
    }

    public void setFaroTraseroIzquierdo(boolean faroTraseroIzquierdo) {
        this.faroTraseroIzquierdo = faroTraseroIzquierdo;
    }

    public boolean isFaroTraseroDerecho() {
        return faroTraseroDerecho;
    }

    public void setFaroTraseroDerecho(boolean faroTraseroDerecho) {
        this.faroTraseroDerecho = faroTraseroDerecho;
    }

    public boolean isLiquidoFrenos() {
        return liquidoFrenos;
    }

    public void setLiquidoFrenos(boolean liquidoFrenos) {
        this.liquidoFrenos = liquidoFrenos;
    }

    public boolean isNivelAceite() {
        return nivelAceite;
    }

    public void setNivelAceite(boolean nivelAceite) {
        this.nivelAceite = nivelAceite;
    }

    public boolean isPresionNeumaticos() {
        return presionNeumaticos;
    }

    public void setPresionNeumaticos(boolean presionNeumaticos) {
        this.presionNeumaticos = presionNeumaticos;
    }

    public boolean isNivelFrenos() {
        return nivelFrenos;
    }

    public void setNivelFrenos(boolean nivelFrenos) {
        this.nivelFrenos = nivelFrenos;
    }

    public boolean isNivelRefrigerante() {
        return nivelRefrigerante;
    }

    public void setNivelRefrigerante(boolean nivelRefrigerante) {
        this.nivelRefrigerante = nivelRefrigerante;
    }

    public boolean isNivelAguaParabrisas() {
        return nivelAguaParabrisas;
    }

    public void setNivelAguaParabrisas(boolean nivelAguaParabrisas) {
        this.nivelAguaParabrisas = nivelAguaParabrisas;
    }

    public boolean isMatafuegoVigente() {
        return matafuegoVigente;
    }

    public void setMatafuegoVigente(boolean matafuegoVigente) {
        this.matafuegoVigente = matafuegoVigente;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}