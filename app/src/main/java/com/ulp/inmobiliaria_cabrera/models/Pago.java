package com.ulp.inmobiliaria_cabrera.models;

import com.ulp.inmobiliaria_cabrera.models.enumMod.EstadoPago;

import java.io.Serializable;
import java.util.Date;

public class Pago implements Serializable {

    private int id;
    private int contratoId;
    private int numeroPago;
    private Date fechaPago;
    private String detalle = "";
    private double importe;
    private EstadoPago estado = EstadoPago.PAGADO;
    private Date fechaAnulacion;
    private Date fechaCreacion = new Date();
    private Date fechaActualizacion = new Date();
    private Contrato contrato;

    public Pago(){};

    public Pago(int id, Contrato contrato, Date fechaActualizacion, Date fechaCreacion, Date fechaAnulacion, EstadoPago estado, double importe, String detalle, Date fechaPago, int numeroPago, int contratoId) {
        this.id = id;
        this.contrato = contrato;
        this.fechaActualizacion = fechaActualizacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaAnulacion = fechaAnulacion;
        this.estado = estado;
        this.importe = importe;
        this.detalle = detalle;
        this.fechaPago = fechaPago;
        this.numeroPago = numeroPago;
        this.contratoId = contratoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContratoId() {
        return contratoId;
    }

    public void setContratoId(int contratoId) {
        this.contratoId = contratoId;
    }

    public int getNumeroPago() {
        return numeroPago;
    }

    public void setNumeroPago(int numeroPago) {
        this.numeroPago = numeroPago;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public Date getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Date fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
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

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }

    @Override
    public String toString() {
        return "Pago{" +
                "contratoId=" + contratoId +
                ", numeroPago=" + numeroPago +
                ", fechaPago=" + fechaPago +
                ", detalle='" + detalle + '\'' +
                ", importe=" + importe +
                ", estado=" + estado +
                ", fechaAnulacion=" + fechaAnulacion +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaActualizacion=" + fechaActualizacion +
                ", contrato=" + contrato +
                '}';
    }


}
