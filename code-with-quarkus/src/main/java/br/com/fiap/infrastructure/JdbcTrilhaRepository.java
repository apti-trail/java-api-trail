package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Trilha;
import br.com.fiap.domain.repository.TrilhaRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTrilhaRepository implements TrilhaRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcTrilhaRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public Trilha salvarTrilha(Trilha trilha) {
        String sql;
        boolean isNovaTrilha = trilha.getIdTrilha() == null;

        if (isNovaTrilha) {
            sql = "INSERT INTO T_APTI_TRILHA (TITULO_TRILHA, PROGRESSO, DT_CRIACAO, DT_ATUALIZACAO, T_APTI_USUARIO_ID_USUARIO) " +
                    "VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE T_APTI_TRILHA SET TITULO_TRILHA = ?, PROGRESSO = ?, DT_ATUALIZACAO = ?, " +
                    "T_APTI_USUARIO_ID_USUARIO = ? WHERE ID_TRILHA = ?";
        }

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_TRILHA"})) {

            if (isNovaTrilha) {
                stmt.setString(1, trilha.getTitulo());
                stmt.setInt(2, trilha.getProgresso());
                stmt.setDate(3, Date.valueOf(trilha.getDataCriacao()));
                stmt.setDate(4, Date.valueOf(trilha.getDataAtualizacao()));
                stmt.setLong(5, trilha.getUsuario().getId());
            } else {
                stmt.setString(1, trilha.getTitulo());
                stmt.setInt(2, trilha.getProgresso());
                stmt.setDate(3, Date.valueOf(trilha.getDataAtualizacao()));
                stmt.setLong(4, trilha.getUsuario().getId());
                stmt.setLong(5, trilha.getIdTrilha());
            }

            int affectedRows = stmt.executeUpdate();

            if (isNovaTrilha && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        trilha.setIdTrilha(generatedKeys.getLong(1));
                    }
                }
            }

            return trilha;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar trilha", e);
        }
    }

    @Override
    public void excluir(Trilha trilha) {
        String sql = "DELETE FROM T_APTI_TRILHA WHERE ID_TRILHA = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, trilha.getIdTrilha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir trilha com ID: " + trilha.getIdTrilha(), e);
        }
    }

    @Override
    public List<Trilha> listarTodas() {
        String sql = "SELECT ID_TRILHA, TITULO_TRILHA, PROGRESSO, DT_CRIACAO, DT_ATUALIZACAO, T_APTI_USUARIO_ID_USUARIO " +
                "FROM T_APTI_TRILHA ORDER BY DT_ATUALIZACAO DESC";

        List<Trilha> trilhas = new ArrayList<>();

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Trilha trilha = mapearTrilha(resultSet);
                trilhas.add(trilha);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar trilhas", e);
        }

        return trilhas;
    }

    @Override
    public Optional<Trilha> buscarPorId(Long id) {
        return Optional.empty();
    }
}
