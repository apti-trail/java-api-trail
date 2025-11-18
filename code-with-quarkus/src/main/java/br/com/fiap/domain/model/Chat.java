package br.com.fiap.domain.model;

import java.time.LocalDate;

public class Chat {

    private Long idChat;
    private String titulo;
    private LocalDate dataCriacao;
    private LocalDate dataAtualizacao;
    private Usuario usuario;

    public Chat(Long idChat, String titulo, LocalDate dataCriacao, LocalDate dataAtualizacao, Usuario usuario) {
        this.idChat = idChat;
        this.titulo = titulo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.usuario = usuario;
    }

    public Chat() {}

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

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataAtualizacao() {
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
