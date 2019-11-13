package com.ufrpe.bsi.soresenha.eventos.negocio;

import android.content.Context;

import com.ufrpe.bsi.soresenha.eventos.dominio.ImagemEvento;
import com.ufrpe.bsi.soresenha.eventos.persistencia.ImagensDAO;

import java.util.List;

public class ImagemService {

    private ImagensDAO imagensDAO;

    public ImagemService(Context context) {
        this.imagensDAO = new ImagensDAO(context);
    }

    public List<ImagemEvento> getEventoID(long id) {
        return imagensDAO.getByEventoID(id);
    }

    public void insert(long eventId, ImagemEvento img) {
        imagensDAO.inserir(eventId, img);
    }

}
