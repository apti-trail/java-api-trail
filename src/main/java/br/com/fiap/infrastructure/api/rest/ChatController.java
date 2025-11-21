package br.com.fiap.infrastructure.api.rest;

import br.com.fiap.interfaces.dto.ChatDTO;
import br.com.fiap.domain.model.Chat;
import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.service.ChatService;
import br.com.fiap.domain.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/chats")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatController {

    @Inject
    ChatService chatService;

    @Inject
    UsuarioService usuarioService;

    @POST
    public Response criarChat(ChatDTO chatDTO) {
        try {
            Usuario usuario = usuarioService.buscarPorId(chatDTO.getUsuarioId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

            Chat chat = new Chat();
            chat.setTitulo(chatDTO.getTitulo());
            chat.setUsuario(usuario);

            Chat chatSalvo = chatService.salvarChat(chat);

            ChatDTO responseDTO = new ChatDTO();
            responseDTO.setIdChat(chatSalvo.getIdChat());
            responseDTO.setTitulo(chatSalvo.getTitulo());
            responseDTO.setDataCriacao(chatSalvo.getDataCriacao());
            responseDTO.setDataAtualizacao(chatSalvo.getDataAtualizacao());
            responseDTO.setUsuarioId(chatSalvo.getUsuario().getId());

            return Response.status(Response.Status.CREATED).entity(responseDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar chat: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarChat(@PathParam("id") Long id) {
        try {
            return chatService.buscarPorId(id)
                    .map(chat -> {
                        ChatDTO dto = new ChatDTO();
                        dto.setIdChat(chat.getIdChat());
                        dto.setTitulo(chat.getTitulo());
                        dto.setDataCriacao(chat.getDataCriacao());
                        dto.setDataAtualizacao(chat.getDataAtualizacao());
                        dto.setUsuarioId(chat.getUsuario().getId());
                        return dto;
                    })
                    .map(dto -> Response.ok(dto).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar chat: " + e.getMessage()).build();
        }
    }

    @GET
    public Response listarChats() {
        try {
            List<ChatDTO> chats = chatService.listarTodos().stream()
                    .map(chat -> {
                        ChatDTO dto = new ChatDTO();
                        dto.setIdChat(chat.getIdChat());
                        dto.setTitulo(chat.getTitulo());
                        dto.setDataCriacao(chat.getDataCriacao());
                        dto.setDataAtualizacao(chat.getDataAtualizacao());
                        dto.setUsuarioId(chat.getUsuario().getId());
                        return dto;
                    })
                    .collect(Collectors.toList());
            return Response.ok(chats).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar chats: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public Response listarChatsPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        try {
            List<ChatDTO> chats = chatService.buscarPorUsuario(usuarioId).stream()
                    .map(chat -> {
                        ChatDTO dto = new ChatDTO();
                        dto.setIdChat(chat.getIdChat());
                        dto.setTitulo(chat.getTitulo());
                        dto.setDataCriacao(chat.getDataCriacao());
                        dto.setDataAtualizacao(chat.getDataAtualizacao());
                        dto.setUsuarioId(chat.getUsuario().getId());
                        return dto;
                    })
                    .collect(Collectors.toList());
            return Response.ok(chats).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar chats do usuário: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirChat(@PathParam("id") Long id) {
        try {
            chatService.excluirChat(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir chat: " + e.getMessage()).build();
        }
    }
}