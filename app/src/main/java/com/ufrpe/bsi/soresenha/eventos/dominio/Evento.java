package com.ufrpe.bsi.soresenha.eventos.dominio;

public class Evento {

    private long id;
    private String nome;
    private String descricao;
    private String preco;
    private int idCriador;

    public Evento(){}

    public Evento(String nome, String descricao, int idCriador){
        this.nome = nome;
        this.descricao = descricao;
        this.idCriador = idCriador;
    }

    public Evento(String nome, String descricao, String preco){
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Evento(long id, String nome, String descricao, String preco){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdCriador() {
        return idCriador;
    }

    public void setIdCriador(int idCriador) {
        this.idCriador = idCriador;
    }
}
