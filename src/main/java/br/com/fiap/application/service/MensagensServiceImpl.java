package br.com.fiap.application.service;

import br.com.fiap.domain.model.Mensagens;
import br.com.fiap.domain.repository.MensagensRepository;
import br.com.fiap.domain.service.MensagensService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MensagensServiceImpl implements MensagensService {

    @Inject
    MensagensRepository mensagensRepository;

    @Override
    public Mensagens salvarMensagem(Mensagens mensagens) {
        if (mensagens.getIdMensagem() == null) {
            mensagens.setDataHoraEnvio(LocalDateTime.now());
        }
        return mensagensRepository.salvarMensagem(mensagens);
    }

    @Override
    public void excluir(Mensagens mensagens) {
        mensagensRepository.excluir(mensagens);
    }

    @Override
    public List<Mensagens> listarTodas() {
        return mensagensRepository.listarTodas();
    }

    @Override
    public Optional<Mensagens> buscarPorId(Long id) {
        return mensagensRepository.buscarPorId(id);
    }

    @Override
    public List<Mensagens> buscarPorChat(Long chatId) {
        return listarTodas().stream()
                .filter(mensagem -> mensagem.getChat().getIdChat().equals(chatId))
                .toList();
    }
}
