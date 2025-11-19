package br.com.fiap.domain.service;

import br.com.fiap.domain.model.Modulo;

import java.util.List;
import java.util.Optional;

public interface ModuloService {
    Modulo salvarModulo(Modulo modulo);
    void excluir(Modulo modulo);
    List<Modulo> listarTodos();
    Optional<Modulo> buscarPorId(Long id);
    List<Modulo> buscarPorTrilha(Long trilhaId);
    void marcarComoConcluido(Long moduloId);
    void marcarComoNaoConcluido(Long moduloId);
}
