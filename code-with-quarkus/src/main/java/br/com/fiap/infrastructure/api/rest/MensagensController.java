package br.com.fiap.infrastructure.api.rest;

import br.com.fiap.interfaces.dto.MensagensDTO;
import br.com.fiap.domain.model.Mensagens;
import br.com.fiap.domain.model.Chat;
import br.com.fiap.domain.service.MensagensService;
import br.com.fiap.domain.service.ChatService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/mensagens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MensagensController {

    @Inject
    MensagensService mensagensService;

    @Inject
    ChatService chatService;

    @POST
    public Response criarMensagem(MensagensDTO mensagensDTO) {
        try {
            Chat chat = chatService.buscarPorId(mensagensDTO.getChatId())
                    .orElseThrow(() -> new NotFoundException("Chat não encontrado"));

            Mensagens mensagem = new Mensagens();
            mensagem.setConteudo(mensagensDTO.getConteudo());
            mensagem.setIsUsuario(mensagensDTO.isUsuario());
            mensagem.setChat(chat);

            Mensagens mensagemSalva = mensagensService.salvarMensagem(mensagem);

            MensagensDTO responseDTO = new MensagensDTO();
            responseDTO.setIdMensagem(mensagemSalva.getIdMensagem());
            responseDTO.setConteudo(mensagemSalva.getConteudo());
            responseDTO.setDataHoraEnvio(mensagemSalva.getDataHoraEnvio());
            responseDTO.setUsuario(mensagemSalva.getIsUsuario());
            responseDTO.setChatId(mensagemSalva.getChat().getIdChat());

            return Response.status(Response.Status.CREATED).entity(responseDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar mensagem: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarMensagem(@PathParam("id") Long id) {
        try {
            return mensagensService.buscarPorId(id)
                    .map(mensagem -> {
                        MensagensDTO dto = new MensagensDTO();
                        dto.setIdMensagem(mensagem.getIdMensagem());
                        dto.setConteudo(mensagem.getConteudo());
                        dto.setDataHoraEnvio(mensagem.getDataHoraEnvio());
                        dto.setUsuario(mensagem.getIsUsuario());
                        dto.setChatId(mensagem.getChat().getIdChat());
                        return dto;
                    })
                    .map(dto -> Response.ok(dto).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar mensagem: " + e.getMessage()).build();
        }
    }

    @GET
    public Response listarMensagens() {
        try {
            List<MensagensDTO> mensagens = mensagensService.listarTodas().stream()
                    .map(mensagem -> {
                        MensagensDTO dto = new MensagensDTO();
                        dto.setIdMensagem(mensagem.getIdMensagem());
                        dto.setConteudo(mensagem.getConteudo());
                        dto.setDataHoraEnvio(mensagem.getDataHoraEnvio());
                        dto.setUsuario(mensagem.getIsUsuario());
                        dto.setChatId(mensagem.getChat().getIdChat());
                        return dto;
                    })
                    .collect(Collectors.toList());
            return Response.ok(mensagens).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar mensagens: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/chat/{chatId}")
    public Response listarMensagensPorChat(@PathParam("chatId") Long chatId) {
        try {
            List<MensagensDTO> mensagens = mensagensService.buscarPorChat(chatId).stream()
                    .map(mensagem -> {
                        MensagensDTO dto = new MensagensDTO();
                        dto.setIdMensagem(mensagem.getIdMensagem());
                        dto.setConteudo(mensagem.getConteudo());
                        dto.setDataHoraEnvio(mensagem.getDataHoraEnvio());
                        dto.setUsuario(mensagem.getIsUsuario());
                        dto.setChatId(mensagem.getChat().getIdChat());
                        return dto;
                    })
                    .collect(Collectors.toList());
            return Response.ok(mensagens).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar mensagens do chat: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirMensagem(@PathParam("id") Long id) {
        try {
            return mensagensService.buscarPorId(id)
                    .map(mensagem -> {
                        mensagensService.excluir(mensagem);
                        return Response.noContent().build();
                    })
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir mensagem: " + e.getMessage()).build();
        }
    }
}