package com.ulp.inmobiliaria_cabrera.request;

public class LoginView {

    public LoginView(String usuario, String password) {
        Usuario = usuario;
        Password = password;
    }

    public String Usuario;

    public String Password;
}