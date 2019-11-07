package com.ufrpe.bsi.soresenha.usuario.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.eventos.persistencia.EventoDAO;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;
import com.ufrpe.bsi.soresenha.usuario.persistencia.UsuarioDAO;

public class UsuarioServices {
    private UsuarioDAO usuarioDAO;
    private EventoDAO eventoDAO;

    public UsuarioServices(Context context) {
        this.usuarioDAO = new UsuarioDAO(context);
        this.eventoDAO = new EventoDAO(context);
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

    public void deletarUser(Usuario usuario) {
        eventoDAO.deletarPorCriador(usuario.getId());
        usuarioDAO.deletar(usuario);
    }

    public void alterarDados(Usuario usuario) {
        usuarioDAO.update(usuario);
    }

    public Usuario getByID(long id) {
        return usuarioDAO.getByID(id);
    }
}

