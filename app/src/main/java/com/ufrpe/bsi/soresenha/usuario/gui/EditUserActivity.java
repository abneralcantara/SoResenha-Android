package com.ufrpe.bsi.soresenha.usuario.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.infra.gui.ConfigurationActivity;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.negocio.UsuarioServices;

public class EditUserActivity extends AppCompatActivity {

    private UsuarioServices usuarioServices = new UsuarioServices(this);
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        editNome = findViewById(R.id.editarNome);
        editEmail = findViewById(R.id.editarEmail);
        editSenha = findViewById(R.id.editarSenha);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editaSalvaDados();
            }
        });
    }

    private void editaSalvaDados() {
        Log.d("sara","0");
        if(validateFieldsUsuario()){
            Usuario userEditado = montarUsuario();
            Log.d("sara","1,1");
            if(usuarioServices.emailCadastrado(userEditado) == null){
                Log.d("sara","2,1");
                usuarioServices.alterarDados(userEditado);
                Log.d("sara","3");
                atualizarIntent();
                showToast("Os dados foram editados");
            } else{
                showToast("já cadastrado");
            }
        }
    }
    private Usuario montarUsuario() {
        Usuario usuarioEditado = new Usuario();
        usuarioEditado.setNome(editNome.getText().toString());
        usuarioEditado.setEmail(editEmail.getText().toString());
        usuarioEditado.setSenha(editSenha.getText().toString());
        return usuarioEditado;
    }

    private boolean validateFieldsUsuario(){
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        resetError();
        boolean res = true;
        View focusView = null;

        if (!validarName(nome) ){
            editNome.setError("Nome inválido, não aceito caracteres especiais");
            focusView = editNome;
            res = false;
        }
        //valida tamanho minimo
        if(!validarLenght(senha) && !senha.isEmpty()) {
            editSenha.setError("Senha inválida(menor que 5 caracteres");
            focusView = editSenha;
            res = false;
        }
        //valida email
        if(!validarEmail(email) && !email.isEmpty()){
            editEmail.setError("Email inválido");
            focusView = editEmail;
            res = false;
        }
        return res;
    }

    private void resetError() {
        editEmail.setError(null);
        editSenha.setError(null);
        editSenha.setError(null);
    }

    private boolean validarEmail(String email) {return Patterns.EMAIL_ADDRESS.matcher(email).matches();}

    private boolean validarLenght(String str) {return str.length() > 5;}

    private boolean validarName(String nome) {return nome.matches("^[a-zA-ZÁÂÃÀÇÉÊÍÓÔÕÚÜáâãàçéêíóôõúü ]*$");}
    @Override
    public void onBackPressed() {startActivity(new Intent(EditUserActivity.this, ConfigurationActivity.class));}

    private void showToast(String s) { Toast.makeText(EditUserActivity.this, s, Toast.LENGTH_LONG).show();}
    private void atualizarIntent() {startActivity(new Intent(EditUserActivity.this, LoginActivity.class));}

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
