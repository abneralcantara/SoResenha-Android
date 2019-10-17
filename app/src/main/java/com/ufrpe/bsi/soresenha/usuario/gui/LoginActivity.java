package com.ufrpe.bsi.soresenha.usuario.gui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.infra.gui.Menu_Activity;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText editEmail, editSenha;
    private Button buttonEntrar;
    private TextView txtCadastreSe;
    private String email_logado ;
    DBHelper db = new DBHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF00BF")));

        editEmail = (EditText)findViewById(R.id.editEmail_login);
        editSenha = (EditText)findViewById(R.id.editSenha_login);
        buttonEntrar = (Button)findViewById(R.id.logar_button);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                Boolean res = db.checarUsuario(new Usuario(email,senha));
                if (res == true){
                    Toast.makeText(LoginActivity.this,"Login efetuado com sucesso", Toast.LENGTH_LONG).show();
                    email_logado = email;
                    Usuario usuario = new Usuario("...", email, senha);
                    Intent inicioIntent = new Intent(getApplicationContext(), Menu_Activity.class);
                    startActivity(inicioIntent);


                } else {
                    Toast.makeText(LoginActivity.this,"E-mail ou senha inválidos", Toast.LENGTH_LONG).show();
                    editSenha.setText("");
                }
            }
        });

        Button CadastreSe = (Button) findViewById(R.id.Gotoregistrar_button);
        CadastreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastroIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(cadastroIntent);
            }
        });
    }
}
