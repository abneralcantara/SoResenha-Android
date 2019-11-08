package com.ufrpe.bsi.soresenha.usuario.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ufrpe.bsi.soresenha.R;
import com.ufrpe.bsi.soresenha.infra.gui.ConfigurationActivity;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.usuario.dominio.TipoUsuario;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.negocio.UsuarioServices;

public class EditUserActivity extends AppCompatActivity {

    private UsuarioServices usuarioServices = new UsuarioServices(this);
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private Button btnSalvar;
    private CheckBox isParceiro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editNome = findViewById(R.id.editarNome);
        editEmail = findViewById(R.id.editarEmail);
        editSenha = findViewById(R.id.editarSenha);
        btnSalvar = findViewById(R.id.btnSalvar);
        isParceiro = findViewById(R.id.parceiroEditUser);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editaSalvaDados();
            }
        });
        isParceiro.setChecked(SessaoUsuario.instance.isParceiro());
    }

    private void editaSalvaDados() {
        if(validateFieldsUsuario()){
            Usuario userEditado = montarUsuario();
            String emailOld = SessaoUsuario.instance.getUsuario().getEmail();
            if(usuarioServices.emailCadastrado(userEditado) == null || emailOld.equals(userEditado.getEmail())){
                usuarioServices.alterarDados(userEditado);
                atualizarIntent();
                showToast("Os dados foram editados");
            } else{
                showToast("Email já cadastrado");
            }
        }
    }
    private Usuario montarUsuario() {
        Usuario usuarioEditado = SessaoUsuario.instance.getUsuario();
        String novoNome = editNome.getText().toString().isEmpty() ? usuarioEditado.getNome() : editNome.getText().toString();
        String novoEmail = editEmail.getText().toString().isEmpty() ? usuarioEditado.getEmail() : editEmail.getText().toString();
        String novaSenha = editSenha.getText().toString().isEmpty() ? usuarioEditado.getSenha() : editSenha.getText().toString();
        usuarioEditado.setNome(novoNome);
        usuarioEditado.setEmail(novoEmail);
        usuarioEditado.setSenha(novaSenha);
        usuarioEditado.setTipo(isParceiro.isChecked() ? TipoUsuario.PARCEIRO : TipoUsuario.NORMAL);
        return usuarioEditado;
    }

    private boolean validateFieldsUsuario(){
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        resetError();
        boolean res = true;
        res = validarCampos(nome, email, senha, res);
        return res;
    }

    private boolean validarCampos(String nome, String email, String senha, boolean res) {
        if (!validarName(nome) ){
            editNome.setError("Nome inválido, não aceito caracteres especiais");
            res = false;
        }
        if(!validarLenght(senha) && !senha.isEmpty()) {
            editSenha.setError("Senha inválida (menor que 5 caracteres)");
            res = false;
        }
        if(!validarEmail(email) && !email.isEmpty()){
            editEmail.setError("Email inválido");
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
