package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Modulo;
import br.com.fiap.domain.repository.ModuloRepository;

import java.sql.*;
import java.util.ArrayList;
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
        String sql = "DELETE FROM T_APTI_MODULO WHERE ID_MODULO = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, modulo.getIdModulo());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir módulo com ID: " + modulo.getIdModulo(), e);
        }
    }

    @Override
    public List<Modulo> listarTodos() {
        String sql = "SELECT ID_MODULO, TITULO_MODULO, CONTEUDO, ORDEM, CONCLUIDO, DT_CONCLUSAO, T_APTI_TRILHA_ID_TRILHA " +
                "FROM T_APTI_MODULO ORDER BY ORDEM ASC";

        List<Modulo> modulos = new ArrayList<>();

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Modulo modulo = mapearModulo(resultSet);
                modulos.add(modulo);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar módulos", e);
        }

        return modulos;
    }

    @Override
    public Optional<Modulo> buscarPorId(Long id) {
        String sql = "SELECT ID_MODULO, TITULO_MODULO, CONTEUDO, ORDEM, CONCLUIDO, DT_CONCLUSAO, T_APTI_TRILHA_ID_TRILHA " +
                "FROM T_APTI_MODULO WHERE ID_MODULO = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Modulo modulo = mapearModulo(resultSet);
                    return Optional.of(modulo);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar módulo por ID: " + id, e);
        }

        return Optional.empty();
    }


    
}
