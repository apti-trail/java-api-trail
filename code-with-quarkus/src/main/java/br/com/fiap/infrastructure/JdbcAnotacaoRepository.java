package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Anotacao;
import br.com.fiap.domain.repository.AnotacaoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JdbcAnotacaoRepository implements AnotacaoRepository {


    private final DatabaseConnection databaseConnection;

    public JdbcAnotacaoRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public Anotacao salvar(Anotacao anotacao) {
        String sql;
        boolean isNovaAnotacao = anotacao.getIdAnotacao() == null;

        if (isNovaAnotacao) {
            sql = "INSERT INTO T_APTI_ANOTACAO (TITULO_ANOTACAO, CONTEUDO, DT_CRIACAO, T_APTI_USUÁRIO_ID_USUARIO) " +
                    "VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE T_APTI_ANOTACAO SET TITULO_ANOTACAO = ?, CONTEUDO = ?, DT_CRIACAO = ?, " +
                    "T_APTI_USUÁRIO_ID_USUARIO = ? WHERE ID_ANOTACAO = ?";
        }

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_ANOTACAO"})) {

            stmt.setString(1, anotacao.getTitulo());
            stmt.setString(2, anotacao.getConteudo());
            stmt.setDate(3, Date.valueOf(anotacao.getDataCriacao()));
            stmt.setLong(4, anotacao.getUsuario().getId());

            if (!isNovaAnotacao) {
                stmt.setLong(5, anotacao.getIdAnotacao());
            }

            int affectedRows = stmt.executeUpdate();

            if (isNovaAnotacao && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        anotacao.setIdAnotacao(generatedKeys.getLong(1));
                    }
                }
            }

            return anotacao;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar anotação", e);
        }
    }

    @Override
    public void excluirAnotacao(Long id) {
        String sql = "DELETE FROM T_APTI_ANOTACAO WHERE ID_ANOTACAO = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir anotação com ID: " + id, e);
        }
    }

    @Override
    public List<Anotacao> listarTodas() {
        String sql = "SELECT ID_ANOTACAO, TITULO_ANOTACAO, CONTEUDO, DT_CRIACAO, T_APTI_USUÁRIO_ID_USUARIO " +
                "FROM T_APTI_ANOTACAO ORDER BY DT_CRIACAO DESC";

        List<Anotacao> anotacoes = new ArrayList<>();

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Anotacao anotacao = mapearAnotacao(resultSet);
                anotacoes.add(anotacao);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar anotações", e);
        }

        return anotacoes;
    }

    @Override
    public Optional<Anotacao> buscarPorId(Long id) {
        String sql = "SELECT ID_ANOTACAO, TITULO_ANOTACAO, CONTEUDO, DT_CRIACAO, T_APTI_USUÁRIO_ID_USUARIO " +
                "FROM T_APTI_ANOTACAO WHERE ID_ANOTACAO = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Anotacao anotacao = mapearAnotacao(resultSet);
                    return Optional.of(anotacao);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar anotação por ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public Anotacao mapearAnotacao(ResultSet resultSet) {
        return null;
    }


}
