package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Modulo;
import br.com.fiap.domain.repository.ModuloRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JdbcModuloRepository implements ModuloRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcModuloRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public Modulo salvarModulo(Modulo modulo) {
        String sql;
        boolean isNovoModulo = modulo.getIdModulo() == null;

        if (isNovoModulo) {
            sql = "INSERT INTO T_APTI_MODULO (TITULO_MODULO, CONTEUDO, ORDEM, CONCLUIDO, DT_CONCLUSAO, T_APTI_TRILHA_ID_TRILHA) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        } else {
            sql = "UPDATE T_APTI_MODULO SET TITULO_MODULO = ?, CONTEUDO = ?, ORDEM = ?, CONCLUIDO = ?, DT_CONCLUSAO = ?, " +
                    "T_APTI_TRILHA_ID_TRILHA = ? WHERE ID_MODULO = ?";
        }

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_MODULO"})) {

            stmt.setString(1, modulo.getTitulo());
            stmt.setString(2, modulo.getConteudo());
            stmt.setInt(3, modulo.getOrdem());
            stmt.setString(4, modulo.isConcluido() ? "S" : "N");

            if (modulo.getDataConclusao() != null) {
                stmt.setDate(5, Date.valueOf(modulo.getDataConclusao()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setLong(6, modulo.getTrilha().getIdTrilha());

            if (!isNovoModulo) {
                stmt.setLong(7, modulo.getIdModulo());
            }

            int affectedRows = stmt.executeUpdate();

            if (isNovoModulo && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        modulo.setIdModulo(generatedKeys.getLong(1));
                    }
                }
            }

            return modulo;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar módulo", e);
        }
    }

    @Override
    public void excluir(Modulo modulo) {

    }

    @Override
    public List<Modulo> listarTodos() {
        return List.of();
    }

    @Override
    public Optional<Modulo> buscarPorId(Long id) {
        return Optional.empty();
    }
}
