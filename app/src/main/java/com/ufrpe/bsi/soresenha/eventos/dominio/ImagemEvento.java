package com.ufrpe.bsi.soresenha.eventos.dominio;

import android.graphics.Bitmap;

public class ImagemEvento {
    long id;
    Bitmap imagem;

    public ImagemEvento(Bitmap bitmap) {
        this.imagem = bitmap;
    }

    public ImagemEvento() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }
}
