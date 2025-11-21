package br.com.fiap.domain.model;

public class Usuario {

    private Long id;
    private String Username;
    private String email;
    private String senha;

    //construtor
    public Usuario(Long id, String username, String email, String senha) {
        this.id = id;
        Username = username;
        this.email = email;
        this.senha = senha;
    }


    //construtor vazio
    public Usuario(){}


    //getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
