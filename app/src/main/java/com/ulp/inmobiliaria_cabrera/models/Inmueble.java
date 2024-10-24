package com.ulp.inmobiliaria_cabrera.models;


import java.util.Date;

public class Inmueble {
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
    private Propietario propietario; // Not mapped in the database
    private Date fechaCreacion;
    private Date fechaActualizacion;

    // Getter and Setter methods

    public String getNombreInmueble() {
        return direccion + " - " + (tipo != null ? tipo.getDescripcion() : "");
    }

    // Getters and Setters omitted for brevity
}
