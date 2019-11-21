package com.ufrpe.bsi.soresenha.avaliacao.dominio;

public enum TipoAvaliacao {
    NAOLIKE(0),
    LIKE(1);

    private Double value;

    TipoAvaliacao(double i) {
        this.value = i;
    }

    public Double getValue() {
        return value;
    }
}
