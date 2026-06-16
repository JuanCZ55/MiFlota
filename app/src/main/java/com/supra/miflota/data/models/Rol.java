package com.supra.miflota.data.models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Rol implements Serializable {
    private int idRol;
    private String nombre;
    private boolean estado;
    private List<Usuario> usuarios = new ArrayList<>();

    public Rol() {
    }

    public Rol(String nombre, boolean estado) {
        this.nombre = nombre;
        this.estado = estado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
