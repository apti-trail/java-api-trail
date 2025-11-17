package br.com.fiap.domain.repository;

import br.com.fiap.domain.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Usuario salvar(Usuario usuario);
    void excluir(Long id);

    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorEmail(String email);
    List<Usuario> listarTodos();

}
