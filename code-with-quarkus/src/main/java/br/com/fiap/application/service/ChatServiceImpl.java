package br.com.fiap.application.service;

import br.com.fiap.domain.model.Chat;
import br.com.fiap.domain.repository.ChatRepository;
import br.com.fiap.domain.service.ChatService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ChatServiceImpl implements ChatService {

    @Inject
    ChatRepository chatRepository;

    @Override
    public Chat salvarChat(Chat chat) {
        if (chat.getIdChat() == null) {
            chat.setDataCriacao(LocalDate.from(LocalDateTime.now()));
        }
        chat.setDataAtualizacao(LocalDate.from(LocalDateTime.now()));
        return chatRepository.salvarChat(chat);
    }

    @Override
    public void excluirChat(Long id) {
        chatRepository.excluirChat(id);
    }

    @Override
    public List<Chat> listarTodos() {
        return chatRepository.listarTodos();
    }

    @Override
    public Optional<Chat> buscarPorId(Long id) {
        return chatRepository.buscarPorId(id);
    }

    @Override
    public List<Chat> buscarPorUsuario(Long usuarioId) {
        return listarTodos().stream()
                .filter(chat -> chat.getUsuario().getId().equals(usuarioId))
                .toList();
    }
}