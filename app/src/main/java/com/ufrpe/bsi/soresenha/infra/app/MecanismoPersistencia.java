package com.ufrpe.bsi.soresenha.infra.app;

public enum MecanismoPersistencia {
    MEMORIA("Memória"), HSQLDB("HSQLDB"), FIREBASE("Firebase");
    private final String descricao;

    MecanismoPersistencia(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
