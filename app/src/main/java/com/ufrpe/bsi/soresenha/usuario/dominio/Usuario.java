package com.ufrpe.bsi.soresenha.usuario.dominio;

public class Usuario {
    private long id;
    private String nome;
    private String email;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(){}

    public Usuario(long id){
        this.id = id;
    }

    public Usuario(String nome, String email, String senha, TipoUsuario tipoUsuario){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipoUsuario;
    }

    public Usuario(String email, String senha){
        this.email = email;
        this.senha = senha;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipoUsuario) { this.tipo = tipoUsuario; }

}
