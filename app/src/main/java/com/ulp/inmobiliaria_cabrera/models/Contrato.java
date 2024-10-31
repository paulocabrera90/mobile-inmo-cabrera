package com.ulp.inmobiliaria_cabrera.models;

import com.ulp.inmobiliaria_cabrera.models.enumMod.EstadoContrato;

import java.io.Serializable;
import java.util.Date;

public class Contrato implements Serializable {

    private int id;
    private int idInmueble;
    private int idInquilino;
    private Date fechaDesde;
    private Date fechaHasta;
    private double montoAlquiler;
    private Date fechaFinalizacionAnticipada;
    private Double multa;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private int cantidadCuotas;
    private int cuotasPagas;
    private boolean pagado;
    private EstadoContrato estado;
    private Inquilino inquilino;

    private Inmueble inmueble;

    public Contrato() {
    }

    public Contrato(int idInmueble, int idInquilino, Date fechaDesde, Date fechaHasta, double montoAlquiler, Date fechaFinalizacionAnticipada, Double multa, Date fechaCreacion, Date fechaActualizacion, int cantidadCuotas, int cuotasPagas, boolean pagado, EstadoContrato estado) {
        this.idInmueble = idInmueble;
        this.idInquilino = idInquilino;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.montoAlquiler = montoAlquiler;
        this.fechaFinalizacionAnticipada = fechaFinalizacionAnticipada;
        this.multa = multa;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.cantidadCuotas = cantidadCuotas;
        this.cuotasPagas = cuotasPagas;
        this.pagado = pagado;
        this.estado = estado;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public double getMontoAlquiler() {
        return montoAlquiler;
    }

    public void setMontoAlquiler(double montoAlquiler) {
        this.montoAlquiler = montoAlquiler;
    }

    public Date getFechaFinalizacionAnticipada() {
        return fechaFinalizacionAnticipada;
    }

    public void setFechaFinalizacionAnticipada(Date fechaFinalizacionAnticipada) {
        this.fechaFinalizacionAnticipada = fechaFinalizacionAnticipada;
    }

    public Double getMulta() {
        return multa;
    }

    public void setMulta(Double multa) {
        this.multa = multa;
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

    public int getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(int cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public int getCuotasPagas() {
        return cuotasPagas;
    }

    public void setCuotasPagas(int cuotasPagas) {
        this.cuotasPagas = cuotasPagas;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "id=" + id +
                ", idInmueble=" + idInmueble +
                ", idInquilino=" + idInquilino +
                ", fechaDesde=" + fechaDesde +
                ", fechaHasta=" + fechaHasta +
                ", montoAlquiler=" + montoAlquiler +
                ", fechaFinalizacionAnticipada=" + fechaFinalizacionAnticipada +
                ", multa=" + multa +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", cantidadCuotas=" + cantidadCuotas +
                ", cuotasPagas=" + cuotasPagas +
                ", pagado=" + pagado +
                ", estado=" + estado +
                '}';
    }
}
