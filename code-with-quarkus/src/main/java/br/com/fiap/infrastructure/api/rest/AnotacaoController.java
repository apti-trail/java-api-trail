package br.com.fiap.infrastructure.api.rest;

import br.com.fiap.interfaces.dto.AnotacaoDTO;
import br.com.fiap.domain.model.Anotacao;
import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.service.AnotacaoService;
import br.com.fiap.domain.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/anotacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnotacaoController {

    @Inject
    AnotacaoService anotacaoService;

    @Inject
    UsuarioService usuarioService;

    @POST
    public Response criarAnotacao(AnotacaoDTO anotacaoDTO) {
        try {
            Usuario usuario = usuarioService.buscarPorId(anotacaoDTO.getUsuarioId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

            Anotacao anotacao = new Anotacao();
            anotacao.setTitulo(anotacaoDTO.getTitulo());
            anotacao.setConteudo(anotacaoDTO.getConteudo());
            anotacao.setUsuario(usuario);

            Anotacao anotacaoSalva = anotacaoService.salvar(anotacao);
            return Response.status(Response.Status.CREATED)
                    .entity(toDTO(anotacaoSalva))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar anotação: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarAnotacao(@PathParam("id") Long id) {
        try {
            return anotacaoService.buscarPorId(id)
                    .map(this::toDTO)
                    .map(dto -> Response.ok(dto).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar anotação: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response listarAnotacoes() {
        try {
            List<AnotacaoDTO> anotacoes = anotacaoService.listarTodas().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return Response.ok(anotacoes).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar anotações: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirAnotacao(@PathParam("id") Long id) {
        try {
            anotacaoService.excluirAnotacao(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir anotação: " + e.getMessage())
                    .build();
        }
    }

    private AnotacaoDTO toDTO(Anotacao anotacao) {
        AnotacaoDTO dto = new AnotacaoDTO();
        dto.setIdAnotacao(anotacao.getIdAnotacao());
        dto.setTitulo(anotacao.getTitulo());
        dto.setConteudo(anotacao.getConteudo());
        dto.setDataCriacao(anotacao.getDataCriacao());
        dto.setUsuarioId(anotacao.getUsuario().getId());
        return dto;
    }
}