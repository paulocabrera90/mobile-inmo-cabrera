package com.ulp.inmobiliaria_cabrera.request;

import com.google.gson.annotations.SerializedName;

public class ChangePasswordView {
    public int id;
    @SerializedName("currentPassword")
    public String CurrentPassword;

    @SerializedName("newPassword")
    public String NewPassword;

    public ChangePasswordView(int id, String currentPassword, String newPassword) {
        this.id = id;
        CurrentPassword = currentPassword;
        NewPassword = newPassword;
    }
}
