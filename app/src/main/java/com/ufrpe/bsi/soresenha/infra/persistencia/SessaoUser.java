package com.ufrpe.bsi.soresenha.infra.persistencia;

import android.app.Application;

public class SessaoUser extends Application {
    private String user;
    private String email;
    private String senha;

    @Override
    public void onCreate() {
        super.onCreate();
        user = "";
        email = "";
        senha = "";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
