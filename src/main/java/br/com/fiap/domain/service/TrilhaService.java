package br.com.fiap.domain.service;

import br.com.fiap.domain.model.Trilha;

import java.util.List;
import java.util.Optional;

public interface TrilhaService {
    Trilha salvarTrilha(Trilha trilha);
    void excluir(Trilha trilha);
    List<Trilha> listarTodas();
    Optional<Trilha> buscarPorId(Long id);
    List<Trilha> buscarPorUsuario(Long usuarioId);
    void atualizarProgresso(Long trilhaId, int progresso);
}
