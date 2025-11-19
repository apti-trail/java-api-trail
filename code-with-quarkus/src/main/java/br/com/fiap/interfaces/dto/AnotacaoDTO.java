package br.com.fiap.interfaces.dto;

import java.time.LocalDate;

public class AnotacaoDTO {
    private Long idAnotacao;
    private String titulo;
    private String conteudo;
    private LocalDate dataCriacao;
    private Long usuarioId;


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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
