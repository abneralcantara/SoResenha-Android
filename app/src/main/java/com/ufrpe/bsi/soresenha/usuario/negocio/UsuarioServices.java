package com.ufrpe.bsi.soresenha.usuario.negocio;

import android.content.Context;
import android.util.Log;

import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.persistencia.UsuarioDAO;

public class UsuarioServices {
    private UsuarioDAO usuarioDAO;
    private Usuario usuarioSessao = SessaoUsuario.instance.getUsuario();

    public UsuarioServices(Context context) {
        this.usuarioDAO = new UsuarioDAO(context);
    }

    public Usuario getUsuario(String email, String password) {
        return usuarioDAO.get(email, password);
    }
    public Usuario emailCadastrado(Usuario usuario) {

        return this.usuarioDAO.get(usuario.getEmail(),usuario.getSenha());
    }

    public boolean checarEmail(String email) {
        return usuarioDAO.checarEmail(email);
    }

    public long cadastraUsuario(Usuario usuario) {
        return usuarioDAO.cadastrar(usuario);
    }

    public void deletarUser(Usuario usuario) { usuarioDAO.deletar(usuario);}

    public void alterarDados(Usuario usuario){
        Log.d("sara","1");
        if (!usuario.getEmail().isEmpty() && !usuario.getEmail().equals(usuarioSessao.getEmail())){
            usuarioSessao.setEmail(usuario.getEmail());
            usuarioDAO.alterarEmail(usuarioSessao);
        }
        if (!usuario.getNome().isEmpty() && !usuario.getNome().equals(usuarioSessao.getNome())){
            usuarioSessao.setNome(usuario.getNome());
            usuarioDAO.alterarNome(usuarioSessao);
        }
        if (!usuario.getSenha().isEmpty() && !usuario.getSenha().equals(usuarioSessao.getSenha())){
            usuarioSessao.setSenha(usuario.getSenha());
            usuarioDAO.alterarSenha(usuarioSessao);
        }
    }
}
