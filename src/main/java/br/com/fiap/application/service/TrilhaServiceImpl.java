package br.com.fiap.application.service;


import br.com.fiap.domain.model.Trilha;
import br.com.fiap.domain.repository.TrilhaRepository;
import br.com.fiap.domain.service.TrilhaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class TrilhaServiceImpl implements TrilhaService {

    @Inject
    TrilhaRepository trilhaRepository;

    @Override
    public Trilha salvarTrilha(Trilha trilha) {
        if (trilha.getIdTrilha() == null) {
            trilha.setDataCriacao(LocalDate.now());
        }
        trilha.setDataAtualizacao(LocalDate.now());
        return trilhaRepository.salvarTrilha(trilha);
    }

    @Override
    public void excluir(Trilha trilha) {
        trilhaRepository.excluir(trilha);
    }

    @Override
    public List<Trilha> listarTodas() {
        return trilhaRepository.listarTodas();
    }

    @Override
    public Optional<Trilha> buscarPorId(Long id) {
        return trilhaRepository.buscarPorId(id);
    }

    @Override
    public List<Trilha> buscarPorUsuario(Long usuarioId) {
        // Implementação usando JDBC direto ou filtrando da lista
        return listarTodas().stream()
                .filter(trilha -> trilha.getUsuario().getId().equals(usuarioId))
                .toList();
    }

    @Override
    public void atualizarProgresso(Long trilhaId, int progresso) {
        Optional<Trilha> trilhaOpt = buscarPorId(trilhaId);
        if (trilhaOpt.isPresent()) {
            Trilha trilha = trilhaOpt.get();
            trilha.setProgresso(Math.min(100, Math.max(0, progresso)));
            trilha.setDataAtualizacao(LocalDate.now());
            salvarTrilha(trilha);
        }
    }
}