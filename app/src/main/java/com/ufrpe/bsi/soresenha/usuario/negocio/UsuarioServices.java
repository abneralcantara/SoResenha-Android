package com.ufrpe.bsi.soresenha.usuario.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.persistencia.UsuarioDAO;

public class UsuarioServices {
    private UsuarioDAO usuarioDAO;

    public UsuarioServices(Context context) {
        this.usuarioDAO = new UsuarioDAO(context);
    }

    public Usuario getUsuario(String email, String password) {
        return usuarioDAO.get(email, password);
    }

    public boolean checarEmail(String email) {
        return usuarioDAO.checarEmail(email);
    }

    public long cadastraUsuario(Usuario usuario) {
        return usuarioDAO.cadastrar(usuario);
    }

    public void deletarUser(Usuario usuario) { usuarioDAO.deletar(usuario);}


}
