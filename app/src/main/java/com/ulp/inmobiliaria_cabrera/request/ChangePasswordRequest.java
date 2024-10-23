package com.ulp.inmobiliaria_cabrera.request;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordRequest {
    public int id;
    @SerializedName("currentPassword")
    public String CurrentPassword;

    @SerializedName("newPassword")
    public String NewPassword;

    public ChangePasswordRequest(int id, String currentPassword, String newPassword) {
        this.id = id;
        CurrentPassword = currentPassword;
        NewPassword = newPassword;
    }
}
