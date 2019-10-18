package com.ufrpe.bsi.soresenha.usuario.gui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.negocio.UsuarioServices;
import com.ufrpe.bsi.soresenha.usuario.persistencia.UsuarioDAO;

public class RegisterActivity extends AppCompatActivity {
    private boolean task = false;
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfSenha;
    private Button registrar_Button;
    UsuarioServices usuarioServices = new UsuarioServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editNome = findViewById(R.id.editRegister_name);
        editEmail = findViewById(R.id.editRegister_email);
        editSenha = findViewById(R.id.editRegister_senha);
        editConfSenha = findViewById(R.id.editRegister_confsenha);

        registrar_Button = findViewById(R.id.Registrarbutton);

        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF00BF")));
        
        registrar_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }
    private boolean validarCampos(){
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confSenha = editConfSenha.getText().toString();

        boolean res = true;
        editEmail.setError(null);
        editSenha.setError(null);
        editSenha.setError(null);
        editConfSenha.setError(null);
        View focusView = null;

        if (nome.isEmpty()){
            editNome.setError("O campo esta vazio!");
            focusView = editNome;
            res = false;
        } else if (!validarNome(nome)){
            editNome.setError("Nome inválido, não aceito caracteres especiais");
            focusView = editNome;
            res = false;
        }

        if(email.isEmpty()){
            editEmail.setError("O Campo esta vazio");
            focusView = editEmail;
            res = false;
        } else if(!validarEmail(email)){
            editEmail.setError("Email inválido");
            focusView = editEmail;
            res = false;
        }

        if (senha.isEmpty()){
            editSenha.setError("O Campo esta vazio");
            focusView = editSenha;
            res = false;
        }else if(!validarSenha(senha)) {
            editSenha.setError("Senha inválida");
            focusView = editSenha;
            res = false;
        } else if(!validarSenhaIguais(senha, confSenha)){
            editSenha.setError("Senhas devem ser iguais");
            focusView = editSenha;
            res = false;
        }
        return res;
    }

    private void cadastrar(){
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confSenha = editConfSenha.getText().toString();
        if (validarCampos()) {
            Usuario usuario = new Usuario(nome, email, senha);
            if (usuarioServices.checarEmail(email)) {
                usuarioServices.cadastraUsuario(usuario);
                Toast.makeText(RegisterActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
                task = false;
                callloginIntent();
            } else {
                Toast.makeText(RegisterActivity.this, "Usuário já cadastrado", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void callloginIntent() {startActivity(new Intent(RegisterActivity.this, LoginActivity.class));}


    private void showExceptionToast(Exception e) {Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();}

    private boolean validarNome (String nome) {return nome.matches("^[a-zA-ZÁÂÃÀÇÉÊÍÓÔÕÚÜáâãàçéêíóôõúü ]*$");}
    private boolean validarSenha(String senha) {return senha.length() > 5;}
    private boolean validarSenhaIguais(String senha, String confirmarSenha) {return senha.equals(confirmarSenha);}
    private boolean validarEmail(String email) {return Patterns.EMAIL_ADDRESS.matcher(email).matches();}
}
