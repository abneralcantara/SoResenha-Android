package com.ufrpe.bsi.soresenha.infra.persistencia;

import android.app.Application;

public class SessaoUser extends Application {
    private static String user;
    private static String email;
    private static String senha;


    @Override
    public void onCreate() {
        super.onCreate();
        user = "";
        email = "";
        senha = "";
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        SessaoUser.user = user;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SessaoUser.email = email;
    }

    public static String getSenha() {
        return senha;
    }

    public static void setSenha(String senha) {
        SessaoUser.senha = senha;
    }






}
