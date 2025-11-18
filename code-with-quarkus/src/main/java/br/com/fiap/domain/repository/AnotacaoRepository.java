package br.com.fiap.domain.repository;

import br.com.fiap.domain.model.Anotacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface AnotacaoRepository {

    Anotacao salvar(Anotacao anotacao); //Não coloquei editar, pois esse métod serve para criar E editar
    void excluirAnotacao(Long id);

    List<Anotacao> listarTodas();
    Optional<Anotacao> buscarPorId(Long id);

    Anotacao mapearAnotacao(ResultSet resultSet) throws SQLException;

}
