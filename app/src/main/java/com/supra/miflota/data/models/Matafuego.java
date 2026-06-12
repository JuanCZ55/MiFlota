package com.supra.miflota.data.models;

public class Matafuego {
    private int idMatafuego;
    private int nroSerie;
    private String proveedor;
    private String fechaCarga;
    private String fechaVencimiento;
    private boolean estado;
    private Vehiculo vehiculo;

    public Matafuego() {
    }

    public Matafuego(int nroSerie, String proveedor, String fechaCarga, String fechaVencimiento, boolean estado) {
        this.nroSerie = nroSerie;
        this.proveedor = proveedor;
        this.fechaCarga = fechaCarga;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
    }

    public int getIdMatafuego() {
        return idMatafuego;
    }

    public void setIdMatafuego(int idMatafuego) {
        this.idMatafuego = idMatafuego;
    }

    public int getNroSerie() {
        return nroSerie;
    }

    public void setNroSerie(int nroSerie) {
        this.nroSerie = nroSerie;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(String fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
}
