package com.ufrpe.bsi.soresenha.eventos.dominio;

public class Evento {

    private long id;
    private String nome;
    private String descricao;
    private String criador;

    public Evento(){}
    public Evento(String nome, String descricao, String criador){
        this.nome = nome;
        this.descricao = descricao;
    }

    public Evento(String nome, String descricao){
        this.nome = nome;
        this.criador = criador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCriador() {
        return criador;
    }

    public void setCriador(String criador) {
        this.criador = criador;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
