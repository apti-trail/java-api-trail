package br.com.fiap.domain.model;

import java.time.LocalDate;

public class Trilha {

    private Long idTrilha;
    private String titulo;
    private int progresso;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    private Usuario usuario;

    public Trilha(Long idTrilha, String titulo, int progresso, LocalDate dataCriacao, LocalDate dataAtualizacao, Usuario usuario) {
        this.idTrilha = idTrilha;
        this.titulo = titulo;
        this.progresso = progresso;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.usuario = usuario;
    }

    public Trilha() {}

    public Long getIdTrilha() {
        return idTrilha;
    }

    public void setIdTrilha(Long idTrilha) {
        this.idTrilha = idTrilha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getProgresso() {
        return progresso;
    }

    public void setProgresso(int progresso) {
        this.progresso = progresso;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
