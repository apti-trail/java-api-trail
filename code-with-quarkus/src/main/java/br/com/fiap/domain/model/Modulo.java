package br.com.fiap.domain.model;

import java.time.LocalDate;

public class Modulo {

    private Long idModulo;
    private String titulo;
    private String conteudo;
    private int ordem;
    private boolean concluido;
    private LocalDate dataConclusao;
    private Trilha trilha;

    public Modulo(Long idModulo, String titulo, String conteudo, int ordem, boolean concluido, LocalDate dataConclusao, Trilha trilha) {
        this.idModulo = idModulo;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.ordem = ordem;
        this.concluido = concluido;
        this.dataConclusao = dataConclusao;
        this.trilha = trilha;
    }

    public Modulo() {}

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

    public Trilha getTrilha() {
        return trilha;
    }

    public void setTrilha(Trilha trilha) {
        this.trilha = trilha;
    }
}
