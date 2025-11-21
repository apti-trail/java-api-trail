package br.com.fiap.infrastructure;

import br.com.fiap.domain.model.Chat;
import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.repository.ChatRepository;
import br.com.fiap.domain.repository.UsuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class JdbcChatRepository implements ChatRepository {


    private final DatabaseConnection databaseConnection;

    @Inject
    UsuarioRepository usuarioRepository;

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
                stmt.setTimestamp(2, Timestamp.valueOf(chat.getDataCriacao().atStartOfDay()));
                stmt.setTimestamp(3, Timestamp.valueOf(chat.getDataAtualizacao().atStartOfDay()));
                stmt.setLong(4, chat.getUsuario().getId());
            } else {
                stmt.setString(1, chat.getTitulo());
                stmt.setTimestamp(2, Timestamp.valueOf(chat.getDataAtualizacao().atStartOfDay()));
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
        String sql = "DELETE FROM T_APTI_CHAT WHERE ID_CHAT = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir chat com ID: " + id, e);
        }
    }

    @Override
    public List<Chat> listarTodos() {
        String sql = "SELECT ID_CHAT, TITULO_CHAT, DT_CRIACAO, DT_ATUALIZACAO, T_APTI_USUARIO_ID_USUARIO " +
                "FROM T_APTI_CHAT ORDER BY DT_ATUALIZACAO DESC";

        List<Chat> chats = new ArrayList<>();

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Chat chat = mapearChat(resultSet);
                chats.add(chat);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar chats", e);
        }

        return chats;
    }

    @Override
    public Chat mapearChat(ResultSet resultSet) throws SQLException {
        Chat chat = new Chat();
        chat.setIdChat(resultSet.getLong("ID_CHAT"));
        chat.setTitulo(resultSet.getString("TITULO_CHAT"));

        Timestamp dataCriacao = resultSet.getTimestamp("DT_CRIACAO");
        if (dataCriacao != null) {
            chat.setDataCriacao(dataCriacao.toLocalDateTime().toLocalDate());
        }

        Timestamp dataAtualizacao = resultSet.getTimestamp("DT_ATUALIZACAO");
        if (dataAtualizacao != null) {
            chat.setDataAtualizacao(dataAtualizacao.toLocalDateTime().toLocalDate());
        }

        Long usuarioId = resultSet.getLong("T_APTI_USUARIO_ID_USUARIO");
        if (usuarioId > 0) {
            try {
                Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(usuarioId);
                usuarioOpt.ifPresent(chat::setUsuario);
            } catch (Exception e) {
                System.err.println("Erro ao carregar usuário ID: " + usuarioId + " - " + e.getMessage());
            }
        }

        return chat;
    }


    @Override
    public Optional<Chat> buscarPorId(Long id) {
        String sql = "SELECT ID_CHAT, TITULO_CHAT, DT_CRIACAO, DT_ATUALIZACAO, T_APTI_USUARIO_ID_USUARIO " +
                "FROM T_APTI_CHAT WHERE ID_CHAT = ?";

        try (Connection conn = this.databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Chat chat = mapearChat(resultSet);
                    return Optional.of(chat);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar chat por ID: " + id, e);
        }

        return Optional.empty();
    }
}
