package br.com.fiap.interfaces.dto;

import java.time.LocalDate;

public class ModuloDTO {
    private Long idModulo;
    private String titulo;
    private String conteudo;
    private int ordem;
    private boolean concluido;
    private LocalDate dataConclusao;
    private Long trilhaId;



    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
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

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public Long getTrilhaId() {
        return trilhaId;
    }

    public void setTrilhaId(Long trilhaId) {
        this.trilhaId = trilhaId;
    }
}
