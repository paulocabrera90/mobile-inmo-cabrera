package com.ulp.inmobiliaria_cabrera.models;

import java.io.Serializable;
import java.util.Date;

public class TipoInmueble implements Serializable {
    private int id;
    private String descripcion;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    public TipoInmueble(int id, Date fechaCreacion, String descripcion, Date fechaActualizacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.descripcion = descripcion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @Override
    public String toString() {
        return this.descripcion;
    }
}
