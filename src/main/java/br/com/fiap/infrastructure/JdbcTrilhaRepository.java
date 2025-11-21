package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Trilha;
import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.repository.TrilhaRepository;
import br.com.fiap.domain.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JdbcTrilhaRepository implements TrilhaRepository {

    private final DatabaseConnection databaseConnection;

    @Inject
    UsuarioRepository usuarioRepository;

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
        String sql = "SELECT ID_TRILHA, TITULO_TRILHA, PROGRESSO, DT_CRIACAO, DT_ATUALIZACAO, T_APTI_USUARIO_ID_USUARIO " +
                "FROM T_APTI_TRILHA WHERE ID_TRILHA = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Trilha trilha = mapearTrilha(resultSet);
                    return Optional.of(trilha);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilha por ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public Trilha mapearTrilha(ResultSet resultSet) throws SQLException {
        Trilha trilha = new Trilha();
        trilha.setIdTrilha(resultSet.getLong("ID_TRILHA"));
        trilha.setTitulo(resultSet.getString("TITULO_TRILHA"));
        trilha.setProgresso(resultSet.getInt("PROGRESSO"));

        Date dataCriacao = resultSet.getDate("DT_CRIACAO");
        if (dataCriacao != null) {
            trilha.setDataCriacao(dataCriacao.toLocalDate());
        }

        Date dataAtualizacao = resultSet.getDate("DT_ATUALIZACAO");
        if (dataAtualizacao != null) {
            trilha.setDataAtualizacao(dataAtualizacao.toLocalDate());
        }

        Long usuarioId = resultSet.getLong("T_APTI_USUARIO_ID_USUARIO");
        if (usuarioId != 0) {
            Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
            usuarioOpt.ifPresent(trilha::setUsuario);
        }

        return trilha;
    }

    @Override
    public List<Trilha> buscarPorUsuario(Long usuarioId) {
        String sql = "SELECT ID_TRILHA, TITULO_TRILHA, PROGRESSO, DT_CRIACAO, DT_ATUALIZACAO, T_APTI_USUARIO_ID_USUARIO " +
                "FROM T_APTI_TRILHA WHERE T_APTI_USUARIO_ID_USUARIO = ? ORDER BY DT_ATUALIZACAO DESC";

        List<Trilha> trilhas = new ArrayList<>();

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, usuarioId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    Trilha trilha = mapearTrilha(resultSet);
                    trilhas.add(trilha);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar trilhas por usuário: " + usuarioId, e);
        }

        return trilhas;
    }

}
