package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Chat;
import br.com.fiap.domain.repository.ChatRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class JdbcChatRepository implements ChatRepository {


    private final DatabaseConnection databaseConnection;

    public JdbcChatRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    
    @Override
    public Chat salvarChat(Chat chat) {
        String sql;
        boolean isNovoChat = chat.getIdChat() == null;

        if (isNovoChat) {
            sql = "INSERT INTO T_APTI_CHAT (TITULO_CHAT, DT_CRIACAO, DT_ATUALIZACAO, T_APTI_USUARIO_ID_USUARIO) " +
                    "VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE T_APTI_CHAT SET TITULO_CHAT = ?, DT_ATUALIZACAO = ?, T_APTI_USUARIO_ID_USUARIO = ? " +
                    "WHERE ID_CHAT = ?";
        }

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, new String[]{"ID_CHAT"})) {

            if (isNovoChat) {
                stmt.setString(1, chat.getTitulo());
                stmt.setTimestamp(2, Timestamp.valueOf(chat.getDataCriacao()));
                stmt.setTimestamp(3, Timestamp.valueOf(chat.getDataAtualizacao()));
                stmt.setLong(4, chat.getUsuario().getId());
            } else {
                stmt.setString(1, chat.getTitulo());
                stmt.setTimestamp(2, Timestamp.valueOf(chat.getDataAtualizacao()));
                stmt.setLong(3, chat.getUsuario().getId());
                stmt.setLong(4, chat.getIdChat());
            }

            int affectedRows = stmt.executeUpdate();

            if (isNovoChat && affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        chat.setIdChat(generatedKeys.getLong(1));
                    }
                }
            }

            return chat;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar chat", e);
        }
    }

    @Override
    public void excluirChat(Long id) {

    }

    @Override
    public List<Chat> listarTodos() {
        return List.of();
    }

    @Override
    public Optional<Chat> buscarPorId(Long id) {
        return Optional.empty();
    }
}
