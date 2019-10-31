package com.ufrpe.bsi.soresenha.infra.negocio;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import com.ufrpe.bsi.soresenha.infra.app.MecanismoPersistencia;
import com.ufrpe.bsi.soresenha.infra.app.SoResenhaApp;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SessaoUsuario {
    public static final SessaoUsuario instance = new SessaoUsuario();
    private Map<String, Object> values = new HashMap<>();


    public Usuario getUsuario(){return (Usuario) values.get("sessao.Usuario");}
    public void setUsuario(Usuario usuario){setValue("sessao.Usuario", usuario);}


    @SuppressWarnings("WeakerAccess")
    public void setValue(String key, Object value) {
        values.put(key, value);
    }

    public void reset() {
        this.values.clear();
    }
}

