package br.com.fiap.interfaces.dto;

public class UsuarioDTO {
    private Long id;
    private String username;
    private String email;
    private String senha;


    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String username, String email, String senha) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.senha = null;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
