package com.ulp.inmobiliaria_cabrera.models.enumMod;

public enum EstadoContrato {
    VIGENTE("Vigente"),
    CANCELADO("Cancelado"),
    FINALIZADO("Finalizado");

    private final String displayName;

    EstadoContrato(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
