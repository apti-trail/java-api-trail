package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
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
        String sql = "SELECT ID_USUARIO, NM_USUARIO, EMAIL, SENHA FROM T_APTI_USUARIO WHERE ID_USUARIO = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = mapearUsuario(resultSet);
                    return Optional.of(usuario);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID: " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = "SELECT ID_USUARIO, NM_USUARIO, EMAIL, SENHA FROM T_APTI_USUARIO WHERE EMAIL = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Usuario usuario = mapearUsuario(resultSet);
                    return Optional.of(usuario);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por email: " + email, e);
        }

        return Optional.empty();
    }


    @Override
    public List<Usuario> listarTodos() {
        String sql = "SELECT ID_USUARIO, NM_USUARIO, EMAIL, SENHA FROM T_APTI_USUARIO ORDER BY NM_USUARIO";

        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Usuario usuario = mapearUsuario(resultSet);
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários", e);
        }

        return usuarios;
    }

    @Override
    public Usuario mapearUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getLong("ID_USUARIO"));
        usuario.setUsername(resultSet.getString("NM_USUARIO"));
        usuario.setEmail(resultSet.getString("EMAIL"));
        usuario.setSenha(resultSet.getString("SENHA"));
        return usuario;
    }

}
