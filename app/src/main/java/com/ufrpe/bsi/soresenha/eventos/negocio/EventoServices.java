package com.ufrpe.bsi.soresenha.eventos.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.persistencia.EventoDAO;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.infra.negocio.SoresenhaAppException;

import java.util.Date;
import java.util.List;

public class EventoServices {
    private EventoDAO eventoDAO;

    public EventoServices(Context context) {
        this.eventoDAO = new EventoDAO(context);
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
            eventoDAO.deletar(evento);
        } else {
            throw new SoresenhaAppException("Não é parceiro");
        }
    }

    public List<Evento> list() {
        return eventoDAO.list();
    }

}
