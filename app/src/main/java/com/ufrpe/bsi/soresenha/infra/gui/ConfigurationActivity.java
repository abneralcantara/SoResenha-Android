package com.ufrpe.bsi.soresenha.infra.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.infra.persistencia.SessaoUser;
import com.ufrpe.bsi.soresenha.usuario.negocio.UsuarioServices;

public class ConfigurationActivity extends AppCompatActivity {

    private UsuarioServices usuarioServices = new UsuarioServices(this);
    private String emailatual = SessaoUser.getEmail();
    private String senhaatual = SessaoUser.getSenha();

// não precisa mexer aqui pois foi um erro meu achando que era o CRUD de usuario
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        Button editnome = (Button) findViewById(R.id.editNome_button);
        editnome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newNameIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(newNameIntent);
            }
        });

        Button editemail = (Button) findViewById(R.id.editEmail_button);
        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newEmailIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(newEmailIntent);
            }
        });

        Button editsenha = (Button) findViewById(R.id.editSenha_button);
        editsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newSenhaIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(newSenhaIntent);
            }
        });

        Button deletuser = (Button) findViewById(R.id.deletaruser_button);
        deletuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioServices.deletarUser(usuarioServices.getUsuario(emailatual, senhaatual));
            }
        });
    }

}


