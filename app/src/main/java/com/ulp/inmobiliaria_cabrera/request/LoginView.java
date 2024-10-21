package com.ulp.inmobiliaria_cabrera.request;

public class LoginView {

    public LoginView(String usuario, String password) {
        Email = usuario;
        Password = password;
    }

    public String Email;

    public String Password;
}