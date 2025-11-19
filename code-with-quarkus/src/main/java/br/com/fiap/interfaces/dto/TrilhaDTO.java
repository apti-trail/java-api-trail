package br.com.fiap.interfaces.dto;

import java.time.LocalDate;

public class TrilhaDTO {
    private Long idTrilha;
    private String titulo;
    private int progresso;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    private Long usuarioId;


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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}