package br.com.fiap.domain.service;

import br.com.fiap.domain.model.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatService {
    Chat salvarChat(Chat chat);
    void excluirChat(Long id);
    List<Chat> listarTodos();
    Optional<Chat> buscarPorId(Long id);
    List<Chat> buscarPorUsuario(Long usuarioId);
}
