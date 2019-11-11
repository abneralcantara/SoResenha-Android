package com.ufrpe.bsi.soresenha.avaliacao.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.avaliacao.dominio.Avaliacao;
import com.ufrpe.bsi.soresenha.avaliacao.persistencia.AvaliacaoDAO;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.util.List;

public class AvaliacaoServices {
    private AvaliacaoDAO avaliacaoDAO;

    public AvaliacaoServices(Context context){
        this.avaliacaoDAO = new AvaliacaoDAO(context);
    }

    public long criar(Avaliacao avaliacao) {
        return avaliacaoDAO.cadastrar(avaliacao);

    }

    public List<Avaliacao> list(Evento evento) {
        return avaliacaoDAO.list(evento.getId());
    }

    public boolean existePresenca(Usuario usuario, Evento evento) {
        return avaliacaoDAO.existsByUserId(usuario.getId(), evento.getId());
    }

    public void deleteByEventoId(long id) {
        avaliacaoDAO.deleteByEventoId(id);
    }

    public Avaliacao get(long idUser, long idEvento) {
        return avaliacaoDAO.get(idUser, idEvento);
    }

    public void update(Avaliacao avaliacao) {
        avaliacaoDAO.update(avaliacao);
    }

    public int countLikes(Evento evento) {
        return avaliacaoDAO.countLikes(evento.getId());
    }
}
