package br.com.fiap.domain.service;

import br.com.fiap.domain.model.Mensagens;

import java.util.List;
import java.util.Optional;

public interface MensagensService {
    Mensagens salvarMensagem(Mensagens mensagens);
    void excluir(Mensagens mensagens);
    List<Mensagens> listarTodas();
    Optional<Mensagens> buscarPorId(Long id);
    List<Mensagens> buscarPorChat(Long chatId);
}
