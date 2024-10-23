package com.ulp.inmobiliaria_cabrera.request;

import com.google.gson.annotations.SerializedName;

public class ResetChangePasswordRequest {
    public String email;

    @SerializedName("newPassword")
    public String newPassword;

    public String verificationNumber;

    public ResetChangePasswordRequest(String email, String newPassword, String verificationNumber) {
        this.email = email;
        this.newPassword = newPassword;
        this.verificationNumber = verificationNumber;
    }
}
