package com.ufrpe.bsi.soresenha.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.infra.persistencia.SessaoUser;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.gui.EditUserActivity;
import com.ufrpe.bsi.soresenha.usuario.gui.LoginActivity;
import com.ufrpe.bsi.soresenha.usuario.negocio.UsuarioServices;

public class ConfigurationActivity extends AppCompatActivity {

    private UsuarioServices usuarioServices = new UsuarioServices(this);
    private SessaoUsuario sessaoUsuario;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void deleteUser(View view){
        final Usuario usuario = sessaoUsuario.instance.getUsuario();
        usuarioServices.deletarUser(usuario);
        Intent newLogsIntent = new Intent(ConfigurationActivity.this, LoginActivity.class);
        startActivity(newLogsIntent);
    }


    public void callEdituser(View view){
        Intent newLogsIntent = new Intent(ConfigurationActivity.this, EditUserActivity.class);
        startActivity(newLogsIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


