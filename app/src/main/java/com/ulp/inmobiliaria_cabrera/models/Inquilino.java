package com.ulp.inmobiliaria_cabrera.models;

import java.io.Serializable;
import java.util.Date;

public class Inquilino  implements Serializable {
    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefonoArea;
    private String telefonoNumero;
    private String email;
    private String direccion;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    public Inquilino(){}

    public Inquilino(int id, String dni, String nombre, String apellido, String telefonoArea, String telefonoNumero, String email, String direccion, Date fechaCreacion, Date fechaActualizacion) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefonoArea = telefonoArea;
        this.telefonoNumero = telefonoNumero;
        this.email = email;
        this.direccion = direccion;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefonoArea() {
        return telefonoArea;
    }

    public void setTelefonoArea(String telefonoArea) {
        this.telefonoArea = telefonoArea;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoNumero() {
        return telefonoNumero;
    }

    public void setTelefonoNumero(String telefonoNumero) {
        this.telefonoNumero = telefonoNumero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getNombreCompleto(){
        return nombre + " " + apellido;
    }

    public String getTelefono(){
        return telefonoArea + " " + telefonoNumero;
    }

    @Override
    public String toString() {
        return "Inquilino{" +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefonoArea='" + telefonoArea + '\'' +
                ", telefonoNumero='" + telefonoNumero + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
