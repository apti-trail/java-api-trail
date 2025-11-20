package br.com.fiap.interfaces.dto;

import java.time.LocalDate;

public class ChatDTO {
    private Long idChat;
    private String titulo;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    private Long usuarioId;


    public ChatDTO() {
    }

    public ChatDTO(Long idChat, String titulo, LocalDate dataCriacao, LocalDate dataAtualizacao, Long usuarioId) {
        this.idChat = idChat;
        this.titulo = titulo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.usuarioId = usuarioId;
    }


    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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