package com.ulp.inmobiliaria_cabrera.models;


import java.io.Serializable;
import java.util.Date;

public class Inmueble implements Serializable {
    private int id;
    private String direccion;
    private int idTipoInmuebleUso;
    private TipoInmuebleUso tipoUso;
    private int idTipoInmueble;
    private TipoInmueble tipo;
    private int ambientes;
    private String coordenadaLat = "-33.30158732843527";
    private String coordenadaLon = "-66.33797013891889";
    private double precio;  // Use double for decimal values
    private int idPropietario;
    private boolean activo;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String image;

    // Getter and Setter methods

    public String getNombreInmueble() {
        return direccion + " - " + (tipo != null ? tipo.getDescripcion() : "");
    }

    public Inmueble(String direccion,
                    int idTipoInmuebleUso,
                    int idTipoInmueble,
                    int ambientes,
                    String coordenadaLat,
                    double precio,
                    String coordenadaLon,
                    int idPropietario,
                    boolean activo,
                    String image) {
        this.direccion = direccion;
        this.idTipoInmuebleUso = idTipoInmuebleUso;
        this.idTipoInmueble = idTipoInmueble;
        this.ambientes = ambientes;
        this.coordenadaLat = coordenadaLat;
        this.precio = precio;
        this.coordenadaLon = coordenadaLon;
        this.idPropietario = idPropietario;
        this.activo = activo;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getIdTipoInmuebleUso() {
        return idTipoInmuebleUso;
    }

    public void setIdTipoInmuebleUso(int idTipoInmuebleUso) {
        this.idTipoInmuebleUso = idTipoInmuebleUso;
    }

    public TipoInmuebleUso getTipoUso() {
        return tipoUso;
    }

    public void setTipoUso(TipoInmuebleUso tipoUso) {
        this.tipoUso = tipoUso;
    }

    public int getIdTipoInmueble() {
        return idTipoInmueble;
    }

    public void setIdTipoInmueble(int idTipoInmueble) {
        this.idTipoInmueble = idTipoInmueble;
    }

    public TipoInmueble getTipo() {
        return tipo;
    }

    public void setTipo(TipoInmueble tipo) {
        this.tipo = tipo;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getCoordenadaLat() {
        return coordenadaLat;
    }

    public void setCoordenadaLat(String coordenadaLat) {
        this.coordenadaLat = coordenadaLat;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCoordenadaLon() {
        return coordenadaLon;
    }

    public void setCoordenadaLon(String coordenadaLon) {
        this.coordenadaLon = coordenadaLon;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Inmueble{" +
                "id=" + id +
                ", direccion='" + direccion + '\'' +
                ", idTipoInmuebleUso=" + idTipoInmuebleUso +
                ", tipoUso=" + tipoUso +
                ", idTipoInmueble=" + idTipoInmueble +
                ", tipo=" + tipo +
                ", ambientes=" + ambientes +
                ", coordenadaLat='" + coordenadaLat + '\'' +
                ", coordenadaLon='" + coordenadaLon + '\'' +
                ", precio=" + precio +
                ", idPropietario=" + idPropietario +
                ", activo=" + activo +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                '}';
    }
}
