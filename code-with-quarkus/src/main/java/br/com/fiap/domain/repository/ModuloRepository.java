package br.com.fiap.domain.repository;

import br.com.fiap.domain.model.Modulo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ModuloRepository {

    Modulo salvarModulo(Modulo modulo);
    void excluir(Modulo modulo);

    List<Modulo> listarTodos();
    Optional<Modulo> buscarPorId(Long id);

    Modulo mapearModulo(ResultSet resultSet) throws SQLException;

}
