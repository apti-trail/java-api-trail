package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Mensagens;
import br.com.fiap.domain.repository.MensagensRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMensagensRepository implements MensagensRepository {

    private final DatabaseConnection databaseConnection;

    public JdbcMensagensRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }


    @Override
    public Mensagens salvarMensagem(Mensagens mensagens) {
        String sql;
        boolean isNovaMensagem = mensagens.getIdMensagem() == null;

        if (isNovaMensagem) {
            sql = "INSERT INTO T_APTI_MENSAGENS (CONTEUDO, DT_HORA_ENVIO, IS_USUARIO, T_APTI_CHAT_ID_CHAT) " +
                    "VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE T_APTI_MENSAGENS SET CONTEUDO = ?, DT_HORA_ENVIO = ?, IS_USUARIO = ?, T_APTI_CHAT_ID_CHAT = ? " +
                    "WHERE ID_MENSAGEM = ?";
        }

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_MENSAGEM"})) {

            stmt.setString(1, mensagens.getConteudo());
            stmt.setTimestamp(2, Timestamp.valueOf(mensagens.getDataHoraEnvio()));
            stmt.setString(3, mensagens.getIsUsuario() ? "S" : "N");
            stmt.setLong(4, mensagens.getChat().getIdChat());

            if (!isNovaMensagem) {
                stmt.setLong(5, mensagens.getIdMensagem());
            }

            int affectedRows = stmt.executeUpdate();

            if (isNovaMensagem && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        mensagens.setIdMensagem(generatedKeys.getLong(1));
                    }
                }
            }

            return mensagens;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar mensagem", e);
        }
    }

    @Override
    public void excluir(Mensagens mensagens) {
        String sql = "DELETE FROM T_APTI_MENSAGENS WHERE ID_MENSAGEM = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, mensagens.getIdMensagem());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir mensagem com ID: " + mensagens.getIdMensagem(), e);
        }
    }


    @Override
    public List<Mensagens> listarTodas() {
        String sql = "SELECT ID_MENSAGEM, CONTEUDO, DT_HORA_ENVIO, IS_USUARIO, T_APTI_CHAT_ID_CHAT " +
                "FROM T_APTI_MENSAGENS ORDER BY DT_HORA_ENVIO ASC";

        List<Mensagens> mensagens = new ArrayList<>();

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Mensagens mensagem = mapearMensagem(resultSet);
                mensagens.add(mensagem);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar mensagens", e);
        }

        return mensagens;
    }

    @Override
    public Optional<Mensagens> buscarPorId(Long id) {
        String sql = "SELECT ID_MENSAGEM, CONTEUDO, DT_HORA_ENVIO, IS_USUARIO, T_APTI_CHAT_ID_CHAT " +
                "FROM T_APTI_MENSAGENS WHERE ID_MENSAGEM = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Mensagens mensagem = mapearMensagem(resultSet);
                    return Optional.of(mensagem);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar mensagem por ID: " + id, e);
        }

        return Optional.empty();
    }
}
