package br.com.fiap.domain.model;

import java.time.LocalDateTime;

public class Mensagens {

    private Long idMensagem;
    private String conteudo;
    private LocalDateTime dataHoraEnvio;
    private Boolean isUsuario;
    private Chat chat;

    public Mensagens(Long idMensagem, String conteudo, LocalDateTime dataHoraEnvio, Boolean isUsuario, Chat chat) {
        this.idMensagem = idMensagem;
        this.conteudo = conteudo;
        this.dataHoraEnvio = dataHoraEnvio;
        this.isUsuario = isUsuario;
        this.chat = chat;
    }

    public Mensagens() {}

    public Long getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Long idMensagem) {
        this.idMensagem = idMensagem;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataHoraEnvio() {
        return dataHoraEnvio;
    }

    public void setDataHoraEnvio(LocalDateTime dataHoraEnvio) {
        this.dataHoraEnvio = dataHoraEnvio;
    }

    public Boolean getUsuario() {
        return isUsuario;
    }

    public void setUsuario(Boolean usuario) {
        isUsuario = usuario;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
