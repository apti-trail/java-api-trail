package br.com.fiap.application.service;

import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.repository.UsuarioRepository;
import br.com.fiap.domain.service.UsuarioService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.salvar(usuario);
    }

    @Override
    public void excluir(Long id) {
        usuarioRepository.excluir(id);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.buscarPorId(id);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    @Override
    public boolean validarLogin(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorEmail(email);
        return usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(senha);
    }
}