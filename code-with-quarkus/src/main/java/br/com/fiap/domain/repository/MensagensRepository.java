package br.com.fiap.domain.repository;

import br.com.fiap.domain.model.Mensagens;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MensagensRepository {

    Mensagens salvarMensagem(Mensagens mensagens);
    void excluir(Mensagens mensagens);

    List<Mensagens> listarTodas();
    Optional<Mensagens> buscarPorId(Long id);

    Mensagens mapearMensagem(ResultSet resultSet) throws SQLException;

}
