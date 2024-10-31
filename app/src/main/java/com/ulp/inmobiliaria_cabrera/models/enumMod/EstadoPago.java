package com.ulp.inmobiliaria_cabrera.models.enumMod;

public enum EstadoPago {
    PAGADO("Pagado"),
    PENDIENTE("Pendiente"),
    ANULADO("Anulado");

    private final String displayName;

    EstadoPago(String displayName) {
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
