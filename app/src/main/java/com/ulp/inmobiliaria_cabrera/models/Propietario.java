package com.ulp.inmobiliaria_cabrera.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Propietario implements Serializable {
    private int id;

    @NotBlank(message = "El documento es obligatorio.")
    @Pattern(regexp = "^\\d{7,8}$", message = "El documento debe tener 7 u 8 dígitos.")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio.")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio.")
    private String apellido;

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico no es válido.")
    private String email;

    @NotBlank(message = "La dirección es obligatoria.")
    private String direccion;
    private String usuario;
    private String password;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    @NotBlank(message = "El area es obligatorio.")
    @Pattern(regexp = "^\\d+$", message = "El area solo debe tener dígitos.")
    private String telefonoArea;

    @NotBlank(message = "El numero de telefono es obligatorio.")
    @Pattern(regexp = "^\\d+$", message = "El numero solo debe tener dígitos.")
    private String telefonoNumero;
    private int estado = 1;

    //private List<Inmueble> inmuebles;

    public String getNombreCompleto() {
        return apellido + " " + nombre;
    }

    public Propietario(String dni, String nombre, String apellido, String email, String direccion, String telefonoArea, String telefonoNumero) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
        this.telefonoArea = telefonoArea;
        this.telefonoNumero = telefonoNumero;
        this.estado = 1;
    }

    public Propietario(int id, String dni, String nombre, String apellido, String email, String direccion, String usuario, String password, Date fechaCreacion, Date fechaActualizacion, String telefonoArea, String telefonoNumero, int estado) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
        this.usuario = usuario;
        this.password = password;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.telefonoArea = telefonoArea;
        this.telefonoNumero = telefonoNumero;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotBlank(message = "El documento es obligatorio.") @Pattern(regexp = "^\\d{7,8}$", message = "El documento debe tener 7 u 8 dígitos.") String getDni() {
        return dni;
    }

    public void setDni(@NotBlank(message = "El documento es obligatorio.") @Pattern(regexp = "^\\d{7,8}$", message = "El documento debe tener 7 u 8 dígitos.") String dni) {
        this.dni = dni;
    }

    public @NotBlank(message = "El nombre es obligatorio.") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotBlank(message = "El nombre es obligatorio.") String nombre) {
        this.nombre = nombre;
    }

    public @NotBlank(message = "El apellido es obligatorio.") String getApellido() {
        return apellido;
    }

    public void setApellido(@NotBlank(message = "El apellido es obligatorio.") String apellido) {
        this.apellido = apellido;
    }

    public @NotBlank(message = "El correo electrónico es obligatorio.") @Email(message = "El correo electrónico no es válido.") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "El correo electrónico es obligatorio.") @Email(message = "El correo electrónico no es válido.") String email) {
        this.email = email;
    }

    public @NotBlank(message = "La dirección es obligatoria.") String getDireccion() {
        return direccion;
    }

    public void setDireccion(@NotBlank(message = "La dirección es obligatoria.") String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFecha_creacion() {
        return fechaCreacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fechaCreacion = fecha_creacion;
    }

    public Date getFecha_actualizacion() {
        return fechaActualizacion;
    }

    public void setFecha_actualizacion(Date fecha_actualizacion) {
        this.fechaActualizacion = fecha_actualizacion;
    }

    public @NotBlank(message = "El area es obligatorio.") @Pattern(regexp = "^\\d+$", message = "El area solo debe tener dígitos.") String getTelefono_area() {
        return telefonoArea;
    }

    public void setTelefono_area(@NotBlank(message = "El area es obligatorio.") @Pattern(regexp = "^\\d+$", message = "El area solo debe tener dígitos.") String telefono_area) {
        this.telefonoArea = telefono_area;
    }

    public @NotBlank(message = "El numero de telefono es obligatorio.") @Pattern(regexp = "^\\d+$", message = "El numero solo debe tener dígitos.") String getTelefono_numero() {
        return telefonoNumero;
    }

    public void setTelefono_numero(@NotBlank(message = "El numero de telefono es obligatorio.") @Pattern(regexp = "^\\d+$", message = "El numero solo debe tener dígitos.") String telefono_numero) {
        this.telefonoNumero = telefono_numero;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Propietario{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_Creacion=" + fechaCreacion +
                ", fecha_Actualizacion=" + fechaActualizacion +
                ", telefono_Area='" + telefonoArea + '\'' +
                ", telefono_Numero='" + telefonoNumero + '\'' +
                ", estado=" + estado +
                '}';
    }
}
