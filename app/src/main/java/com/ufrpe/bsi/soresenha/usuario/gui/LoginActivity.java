package com.ufrpe.bsi.soresenha.usuario.gui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.infra.gui.MenuActivity;
import com.ufrpe.bsi.soresenha.infra.persistencia.SessaoUser;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.negocio.UsuarioServices;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail;
    private EditText editSenha;
    private UsuarioServices usuarioServices = new UsuarioServices(this);
    private SessaoUser sessaoUser ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF00BF")));
        editEmail = findViewById(R.id.editEmail_login);
        editSenha = findViewById(R.id.editSenha_login);
        Button buttonEntrar = findViewById(R.id.logar_button);
        Button cadastreSe = (Button) findViewById(R.id.Gotoregistrar_button);
        configurarListeners(buttonEntrar, cadastreSe);
    }

    private void configurarListeners(Button buttonEntrar, Button cadastreSe) {
        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String email = editEmail.getText().toString();
            String senha = editSenha.getText().toString();
            Usuario res = usuarioServices.getUsuario(email,senha);
            if (res != null){
                //sessaoUser.setEmail(email);
                //sessaoUser.setSenha(senha);
                Toast.makeText(LoginActivity.this,"Login efetuado com sucesso", Toast.LENGTH_LONG).show();
                Intent inicioIntent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(inicioIntent);
            } else {
                Toast.makeText(LoginActivity.this,"E-mail ou senha inválidos", Toast.LENGTH_LONG).show();
                editSenha.setText("");
            }
            }
        });
        cadastreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(cadastroIntent);
            }
        });
    }
}
