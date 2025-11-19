package br.com.fiap.domain.repository;

import br.com.fiap.domain.model.Trilha;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface TrilhaRepository {

    Trilha salvarTrilha(Trilha trilha);
    void excluir(Trilha trilha);

    List<Trilha> listarTodas();
    Optional<Trilha> buscarPorId(Long id);

    Trilha mapearTrilha(ResultSet resultSet) throws SQLException;
}
