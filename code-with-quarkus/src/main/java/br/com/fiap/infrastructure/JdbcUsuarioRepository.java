package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.repository.UsuarioRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JdbcUsuarioRepository implements UsuarioRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcUsuarioRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public Usuario salvar(Usuario usuario) {
        String sql;
        boolean isNovoUsuario = usuario.getId() == null;

        if (isNovoUsuario) {
            sql = "INSERT INTO T_APTI_USUARIO (NM_USUARIO, EMAIL, SENHA) VALUES (?, ?, ?)";
        } else {
            sql = "UPDATE T_APTI_USUARIO SET NM_USUARIO = ?, EMAIL = ?, SENHA = ? WHERE ID_USUARIO = ?";
        }

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_USUARIO"})) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());

            if (!isNovoUsuario) {
                stmt.setLong(4, usuario.getId());
            }

            int affectedRows = stmt.executeUpdate();

            if (isNovoUsuario && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setId(generatedKeys.getLong(1));
                    }
                }
            }

            return usuario;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar usuário", e);
        }
    }

    @Override
    public void excluir(Long id) {
        String sql = "DELETE FROM T_APTI_USUARIO WHERE ID_USUARIO = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário com ID: " + id, e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return Optional.empty();
    }

    @Override
    public List<Usuario> listarTodos() {
        return List.of();
    }
}
