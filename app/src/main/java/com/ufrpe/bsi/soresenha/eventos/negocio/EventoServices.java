package com.ufrpe.bsi.soresenha.eventos.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.avaliacao.negocio.AvaliacaoServices;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.persistencia.EventoDAO;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.infra.negocio.SoresenhaAppException;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.util.Date;
import java.util.List;

public class EventoServices {
    private EventoDAO eventoDAO;
    private AvaliacaoServices avaliacaoServices;

    public EventoServices(Context context) {
        this.eventoDAO = new EventoDAO(context);
        this.avaliacaoServices = new AvaliacaoServices(context);
    }

    public Evento get(long eventId) {
        return eventoDAO.get(eventId);
    }

    public void update(Evento evento) throws SoresenhaAppException {
        if (SessaoUsuario.instance.isParceiro()) {
            if (evento.getDate().before(new Date())) {
                throw new SoresenhaAppException("Data já passou");
            }
            eventoDAO.update(evento);
        } else {
            throw new SoresenhaAppException("Não é parceiro");
        }
    }

    public long criar(Evento evento) throws SoresenhaAppException {
        if (SessaoUsuario.instance.isParceiro()) {
            if (evento.getDate().before(new Date())) {
                throw new SoresenhaAppException("Data já passou");
            }
            return eventoDAO.cadastrar(evento);
        } else {
            throw new SoresenhaAppException("Não é parceiro");
        }
    }

    public void deletar(Evento evento) throws SoresenhaAppException {
        if (SessaoUsuario.instance.isParceiro()) {
            avaliacaoServices.deleteByEventoId(evento.getId());
            eventoDAO.deletar(evento);

        } else {
            throw new SoresenhaAppException("Não é parceiro");
        }
    }

    public List<Evento> list() {
        return eventoDAO.list();
    }

    public List<Usuario> listParticipantes(Evento evento){
        return eventoDAO.getListParticipantes(evento);
    }
}
