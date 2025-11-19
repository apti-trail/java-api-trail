package br.com.fiap.interfaces.mapper;

import br.com.fiap.domain.model.Usuario;
import br.com.fiap.interfaces.dto.UsuarioDTO;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail()
        );
    }

    public static Usuario toEntity(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) return null;

        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setEmail(usuarioDTO.getEmail());
        return usuario;
    }
}
