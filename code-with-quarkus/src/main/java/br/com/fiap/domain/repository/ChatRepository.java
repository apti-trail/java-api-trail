package br.com.fiap.domain.repository;

import br.com.fiap.domain.model.Chat;
import java.util.List;
import java.util.Optional;

public interface ChatRepository {

    Chat salvarChat(Chat chat);
    void excluirChat(Long id);

    List<Chat> listarTodos();
    Optional<Chat> buscarPorId(Long id);


}
