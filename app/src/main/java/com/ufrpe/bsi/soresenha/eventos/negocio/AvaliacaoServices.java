package com.ufrpe.bsi.soresenha.eventos.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.eventos.dominio.Avaliacao;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.dominio.TipoAvaliacao;
import com.ufrpe.bsi.soresenha.eventos.persistencia.AvaliacaoDAO;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.infra.negocio.SoresenhaAppException;

import java.util.Date;
import java.util.List;

public class AvaliacaoServices {
    private AvaliacaoDAO avaliacaoDAO;

    public AvaliacaoServices(Context context){
        this.avaliacaoDAO = new AvaliacaoDAO(context);
    }

    public Avaliacao get(String idEvento, String idUser) {
        Avaliacao avaliacao =  avaliacaoDAO.get(idEvento, idUser);;
        return avaliacao;
    }

    public long criar(Avaliacao avaliacao) {
        return avaliacaoDAO.cadastrar(avaliacao);

    }

    public void darLike(String  IDuser, String IDevento, TipoAvaliacao tipoAvaliacao){
        avaliacaoDAO.update(new Avaliacao(IDuser, IDevento, tipoAvaliacao));
    }

    public Boolean checkLike(Avaliacao avaliacao){
        if (avaliacaoDAO.get(avaliacao.getIdUser(),avaliacao.getIdEvento()) != null) {
            return true;
        }
        return false;
    }

    public int countLike(Evento evento){
        return 0;
    }

    public List<Avaliacao> list(Evento evento) {
        List<Avaliacao> list = avaliacaoDAO.list(String.valueOf(evento.getId()));
        return list;
    }

}
