package com.ulp.inmobiliaria_cabrera.request;

public class ChangePasswordView {
    public int id;
    public String email;
    public String Password;

    public ChangePasswordView(int id, String email, String password) {
        this.id = id;
        this.email = email;
        Password = password;
    }
}
