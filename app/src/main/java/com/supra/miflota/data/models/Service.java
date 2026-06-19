package com.supra.miflota.data.models;

import java.io.Serializable;

public class Service implements Serializable {
    private int idService;
    private boolean bujias;
    private boolean bombaCombustible;
    private boolean filtroDeAire;
    private boolean filtroDeAceite;
    private boolean filtroDeCombustible;
    private boolean correaPolyV;
    private boolean correaDentada;
    private boolean alineoBalanceo;
    private boolean bombaAgua;
    private boolean bombaAceite;
    private boolean aceite;
    private boolean excepcional;
    private String servicioExcepcional;
    private boolean realizado;
    private String proveedor;
    private int kmService;
    private String detalle;
    private String fecha;
    private int idVehiculo;
    private Vehiculo vehiculo;
    private boolean estado;
    private boolean currentUser;

    public Service() {
    }

    public Service(int kmService, String proveedor, String fecha, boolean estado) {
        this.kmService = kmService;
        this.proveedor = proveedor;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Service(int idService, boolean bujias, boolean bombaCombustible, boolean filtroDeAire, boolean filtroDeAceite, boolean filtroDeCombustible, boolean correaPolyV, boolean correaDentada, boolean alineoBalanceo, boolean bombaAgua, boolean bombaAceite, boolean aceite, boolean excepcional, String servicioExcepcional, boolean realizado, String proveedor, int kmService, String detalle, String fecha, int idVehiculo, boolean estado) {
        this.idService = idService;
        this.bujias = bujias;
        this.bombaCombustible = bombaCombustible;
        this.filtroDeAire = filtroDeAire;
        this.filtroDeAceite = filtroDeAceite;
        this.filtroDeCombustible = filtroDeCombustible;
        this.correaPolyV = correaPolyV;
        this.correaDentada = correaDentada;
        this.alineoBalanceo = alineoBalanceo;
        this.bombaAgua = bombaAgua;
        this.bombaAceite = bombaAceite;
        this.aceite = aceite;
        this.excepcional = excepcional;
        this.servicioExcepcional = servicioExcepcional;
        this.realizado = realizado;
        this.proveedor = proveedor;
        this.kmService = kmService;
        this.detalle = detalle;
        this.fecha = fecha;
        this.idVehiculo = idVehiculo;
        this.estado = estado;
    }

    public Service(boolean aceite, boolean filtroDeAceite, boolean bombaAceite, boolean filtroDeAire, boolean filtroDeCombustible, boolean bombaCombustible, boolean alineoBalanceo, boolean bombaAgua, boolean correaPolyV, boolean correaDentada, boolean bujias, int kmService, boolean excepcional, String servicioExcepcional, String proveedor, int idVehiculo, String detalle, String fecha, boolean realizado, boolean estado, boolean currentUser) {
        this.aceite = aceite;
        this.filtroDeAceite = filtroDeAceite;
        this.bombaAceite = bombaAceite;
        this.filtroDeAire = filtroDeAire;
        this.filtroDeCombustible = filtroDeCombustible;
        this.bombaCombustible = bombaCombustible;
        this.alineoBalanceo = alineoBalanceo;
        this.bombaAgua = bombaAgua;
        this.correaPolyV = correaPolyV;
        this.correaDentada = correaDentada;
        this.bujias = bujias;
        this.kmService = kmService;
        this.excepcional = excepcional;
        this.servicioExcepcional = servicioExcepcional;
        this.proveedor = proveedor;
        this.idVehiculo = idVehiculo;
        this.detalle = detalle;
        this.fecha = fecha;
        this.realizado = realizado;
        this.estado = estado;
        this.currentUser = currentUser;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public boolean isBujias() {
        return bujias;
    }

    public void setBujias(boolean bujias) {
        this.bujias = bujias;
    }

    public boolean isBombaCombustible() {
        return bombaCombustible;
    }

    public void setBombaCombustible(boolean bombaCombustible) {
        this.bombaCombustible = bombaCombustible;
    }

    public boolean isFiltroDeAire() {
        return filtroDeAire;
    }

    public void setFiltroDeAire(boolean filtroDeAire) {
        this.filtroDeAire = filtroDeAire;
    }

    public boolean isFiltroDeAceite() {
        return filtroDeAceite;
    }

    public void setFiltroDeAceite(boolean filtroDeAceite) {
        this.filtroDeAceite = filtroDeAceite;
    }

    public boolean isFiltroDeCombustible() {
        return filtroDeCombustible;
    }

    public void setFiltroDeCombustible(boolean filtroDeCombustible) {
        this.filtroDeCombustible = filtroDeCombustible;
    }

    public boolean isCorreaPolyV() {
        return correaPolyV;
    }

    public void setCorreaPolyV(boolean correaPolyV) {
        this.correaPolyV = correaPolyV;
    }

    public boolean isCorreaDentada() {
        return correaDentada;
    }

    public void setCorreaDentada(boolean correaDentada) {
        this.correaDentada = correaDentada;
    }

    public boolean isAlineoBalanceo() {
        return alineoBalanceo;
    }

    public void setAlineoBalanceo(boolean alineoBalanceo) {
        this.alineoBalanceo = alineoBalanceo;
    }

    public boolean isBombaAgua() {
        return bombaAgua;
    }

    public void setBombaAgua(boolean bombaAgua) {
        this.bombaAgua = bombaAgua;
    }

    public boolean isBombaAceite() {
        return bombaAceite;
    }

    public void setBombaAceite(boolean bombaAceite) {
        this.bombaAceite = bombaAceite;
    }

    public boolean isAceite() {
        return aceite;
    }

    public void setAceite(boolean aceite) {
        this.aceite = aceite;
    }

    public boolean isExcepcional() {
        return excepcional;
    }

    public void setExcepcional(boolean excepcional) {
        this.excepcional = excepcional;
    }

    public String getServicioExcepcional() {
        return servicioExcepcional;
    }

    public void setServicioExcepcional(String servicioExcepcional) {
        this.servicioExcepcional = servicioExcepcional;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getKmService() {
        return kmService;
    }

    public void setKmService(int kmService) {
        this.kmService = kmService;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    public boolean isCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        this.currentUser = currentUser;
    }
}
