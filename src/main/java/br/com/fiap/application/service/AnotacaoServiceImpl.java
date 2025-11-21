package br.com.fiap.application.service;


import br.com.fiap.domain.model.Anotacao;
import br.com.fiap.domain.repository.AnotacaoRepository;
import br.com.fiap.domain.service.AnotacaoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AnotacaoServiceImpl implements AnotacaoService {

    @Inject
    AnotacaoRepository anotacaoRepository;

    @Override
    public Anotacao salvar(Anotacao anotacao) {
        if (anotacao.getIdAnotacao() == null) {
            anotacao.setDataCriacao(LocalDate.now());
        }
        return anotacaoRepository.salvar(anotacao);
    }

    @Override
    public void excluirAnotacao(Long id) {
        anotacaoRepository.excluirAnotacao(id);
    }

    @Override
    public List<Anotacao> listarTodas() {
        return anotacaoRepository.listarTodas();
    }

    @Override
    public Optional<Anotacao> buscarPorId(Long id) {
        return anotacaoRepository.buscarPorId(id);
    }

    @Override
    public List<Anotacao> buscarPorUsuario(Long usuarioId) {
        return listarTodas().stream()
                .filter(anotacao -> anotacao.getUsuario().getId().equals(usuarioId))
                .toList();
    }
}
