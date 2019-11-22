package com.ufrpe.bsi.soresenha.eventos.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.avaliacao.dominio.Avaliacao;
import com.ufrpe.bsi.soresenha.avaliacao.negocio.AvaliacaoServices;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.persistencia.EventoDAO;
import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.infra.negocio.SoresenhaAppException;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import external.SlopeOne;

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

    public List<Evento> listEventoCriado(Usuario usuario){
        return eventoDAO.getListCriado(usuario);
    }

    public List<Usuario> listParticipantes(Evento evento){
        return eventoDAO.getListParticipantes(evento);
    }

    public List<Evento> recommend(Usuario usuario) {
        List<Evento> eventos = eventoDAO.list();
        SlopeOne.slopeOne(generateInputData(), eventos);
        Map<Long, HashMap<Long, Double>> values = SlopeOne.getOutput();
        if (values.containsKey(usuario.getId())) {
            sortList(usuario, eventos, values);
        }
        removeVisited(eventos, usuario);
        return eventos;
    }

    private void removeVisited(List<Evento> eventoList, Usuario usuario) {
        List<Evento> seen = new ArrayList<>();
        for (Evento e : eventoList) {
            if (avaliacaoServices.existePresenca(usuario, e)) {
                seen.add(e);
            }
        }
        for (Evento s : seen) {
            eventoList.remove(s);
        }
    }

    private Map<Long, HashMap<Long, Double>> generateInputData() {
        List<Avaliacao> avaliacoes = avaliacaoServices.list();
        Map<Long, HashMap<Long, Double>> inputData = new HashMap<>();
        for (Avaliacao a : avaliacoes) {
            if (!inputData.containsKey(a.getUsuario().getId())) {
                inputData.put(a.getUsuario().getId(), new HashMap<Long, Double>());
            }
            inputData.get(a.getUsuario().getId()).put(a.getEvento().getId(), a.getTipoAvaliacao().getValue());
        }
        return inputData;
    }

    private void sortList(Usuario usuario, List<Evento> eventos, Map<Long, HashMap<Long, Double>> values) {
        final Map<Long, Double> reccs = values.get(usuario.getId());
        Collections.sort(eventos, new Comparator<Evento>() {
            @Override
            public int compare(Evento e1, Evento e2) {
                Double e1s = reccs.get(e1.getId());
                Double e2s = reccs.get(e2.getId());
                if (e1s.equals(null))
                    return 1;
                if (e2s.equals(null))
                    return -1;
                if(e1s.equals(e2s))
                    return 0;
                if (e2s < e1s)
                    return -1;
                else
                    return 1;
            }
        });
    }
}
