package com.supra.miflota.data.models;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {
    private int idUsuario;
    private String gmail;
    private String contrasena;
    private String avatarUrl;
    private int idRol;
    private Rol rol;
    private int idPersona;
    private Persona persona;
    private boolean estado;

    public Usuario() {
    }

    public Usuario(String gmail, String contrasena, String avatarUrl, int idRol, int idPersona, boolean estado) {
        this.gmail = gmail;
        this.contrasena = contrasena;
        this.avatarUrl = avatarUrl;
        this.idRol = idRol;
        this.idPersona = idPersona;
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
