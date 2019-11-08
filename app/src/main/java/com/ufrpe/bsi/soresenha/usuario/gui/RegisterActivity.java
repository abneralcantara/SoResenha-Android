package com.ufrpe.bsi.soresenha.usuario.gui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.usuario.dominio.TipoUsuario;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.negocio.UsuarioServices;

public class RegisterActivity extends AppCompatActivity {
    private boolean task = false;
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfSenha;
    private CheckBox checkParceiro;
    private Button registrarButton;
    private UsuarioServices usuarioServices = new UsuarioServices(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editNome = findViewById(R.id.editRegister_name);
        editEmail = findViewById(R.id.editRegister_email);
        editSenha = findViewById(R.id.editRegister_senha);
        editConfSenha = findViewById(R.id.editRegister_confsenha);
        checkParceiro = findViewById(R.id.editRegister_serparceiro);
        registrarButton = findViewById(R.id.Registrarbutton);
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF00BF")));
        registrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });
    }

    private boolean validarCampos() {
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confSenha = editConfSenha.getText().toString();
        limparErros();
        View focusView = null;
        if (!resultadoValidacoes(nome, email, senha, confSenha)) return false;
        return true;
    }

    private boolean resultadoValidacoes(String nome, String email, String senha, String confSenha) {
        if (!validarNomeExiste(nome)) return false;
        if (!validarEmailExiste(email)) return false;
        if (!validarSenha(senha, confSenha)) return false;
        return true;
    }

    private boolean validarSenha(String senha, String confSenha) {
        if (senha.isEmpty()) {
            editSenha.setError("O Campo esta vazio");
            return false;
        } else if (!validarSenha(senha)) {
            editSenha.setError("Senha inválida");
            return false;
        } else if (!validarSenhaIguais(senha, confSenha)) {
            editSenha.setError("Senhas devem ser iguais");
            return false;
        }
        return true;
    }

    private boolean validarEmailExiste(String email) {
        if (email.isEmpty()) {
            editEmail.setError("O Campo esta vazio");
            return false;
        } else if (!validarEmail(email)) {
            editEmail.setError("Email inválido");
            return false;
        }
        return true;
    }

    private boolean validarNomeExiste(String nome) {
        if (nome.isEmpty()) {
            editNome.setError("O campo esta vazio!");
            return false;
        } else if (!validarNome(nome)) {
            editNome.setError("Nome inválido, não aceito caracteres especiais");
            return false;
        }
        return true;
    }

    private void limparErros() {
        editEmail.setError(null);
        editSenha.setError(null);
        editSenha.setError(null);
        editConfSenha.setError(null);
    }

    private void cadastrar() {
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confSenha = editConfSenha.getText().toString();
        TipoUsuario isparceiro = validarparceiro();
        if (validarCampos()) {
            Usuario usuario = new Usuario(nome, email, senha, isparceiro);
            if (usuarioServices.checarEmail(email)) {
                usuarioServices.cadastraUsuario(usuario);
                Toast.makeText(this, "Cadastro realizado com sucesso", Toast.LENGTH_LONG).show();
                task = false;
                callloginIntent();
            } else {
                Toast.makeText(this, "Usuário já cadastrado", Toast.LENGTH_LONG).show();
            }
        }
    }

    private TipoUsuario validarparceiro() {
        if (checkParceiro.isChecked()) {return TipoUsuario.PARCEIRO;}
        return TipoUsuario.NORMAL;
    }

    private void callloginIntent() {
        startActivity(new Intent(this, LoginActivity.class));
    }


    private void showExceptionToast(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private boolean validarNome(String nome) {
        return nome.matches("^[a-zA-ZÁÂÃÀÇÉÊÍÓÔÕÚÜáâãàçéêíóôõúü ]*$");
    }

    private boolean validarSenha(String senha) {
        return senha.length() > 5;
    }

    private boolean validarSenhaIguais(String senha, String confirmarSenha) {
        return senha.equals(confirmarSenha);
    }

    private boolean validarEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
