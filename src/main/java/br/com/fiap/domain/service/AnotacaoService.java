package br.com.fiap.domain.service;

import br.com.fiap.domain.model.Anotacao;

import java.util.List;
import java.util.Optional;

public interface AnotacaoService {
    Anotacao salvar(Anotacao anotacao);
    void excluirAnotacao(Long id);
    List<Anotacao> listarTodas();
    Optional<Anotacao> buscarPorId(Long id);
    List<Anotacao> buscarPorUsuario(Long usuarioId);
}
