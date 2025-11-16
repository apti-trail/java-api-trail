package br.com.fiap.domain.model;

import java.time.LocalDateTime;

public class Mensagens {

    private Long idMensagem;
    private String conteudo;
    private LocalDateTime dataHoraEnvio;
    private Boolean isUsuario;
    private Usuario usuario;

    public Mensagens(Long idMensagem, String conteudo, LocalDateTime dataHoraEnvio, Boolean isUsuario, Usuario usuario) {
        this.idMensagem = idMensagem;
        this.conteudo = conteudo;
        this.dataHoraEnvio = dataHoraEnvio;
        this.isUsuario = isUsuario;
        this.usuario = usuario;
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

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setUsuario(Boolean usuario) {
        isUsuario = usuario;
    }
}
