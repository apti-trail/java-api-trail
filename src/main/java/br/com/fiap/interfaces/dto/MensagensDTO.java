package br.com.fiap.interfaces.dto;

import java.time.LocalDateTime;

public class MensagensDTO {
    private Long idMensagem;
    private String conteudo;
    private LocalDateTime dataHoraEnvio;
    private boolean isUsuario;
    private Long chatId;

    // Construtores
    public MensagensDTO() {
    }

    public MensagensDTO(Long idMensagem, String conteudo, LocalDateTime dataHoraEnvio, boolean isUsuario, Long chatId) {
        this.idMensagem = idMensagem;
        this.conteudo = conteudo;
        this.dataHoraEnvio = dataHoraEnvio;
        this.isUsuario = isUsuario;
        this.chatId = chatId;
    }

    // Getters e Setters
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

    public boolean isUsuario() {
        return isUsuario;
    }

    public void setUsuario(boolean usuario) {
        isUsuario = usuario;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}