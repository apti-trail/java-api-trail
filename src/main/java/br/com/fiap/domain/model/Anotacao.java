package br.com.fiap.domain.model;

import java.time.LocalDate;

public class Anotacao {

    private Long idAnotacao;
    private String titulo;
    private String conteudo;
    private LocalDate dataCriacao;
    private Usuario usuario;

    public Anotacao(Long idAnotacao, String titulo, String conteudo, LocalDate dataCriacao, Usuario usuario) {
        this.idAnotacao = idAnotacao;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.usuario = usuario;
    }

    public Anotacao() {}

    public Long getIdAnotacao() {
        return idAnotacao;
    }

    public void setIdAnotacao(Long idAnotacao) {
        this.idAnotacao = idAnotacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
