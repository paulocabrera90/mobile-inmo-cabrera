package com.ulp.inmobiliaria_cabrera.request;

public class ConfirmPasswordResetRequest {
    public String email;
    public String verificationNumber;

    public ConfirmPasswordResetRequest(String email, String verificationNumber) {
        this.email = email;
        this.verificationNumber = verificationNumber;
    }
}
