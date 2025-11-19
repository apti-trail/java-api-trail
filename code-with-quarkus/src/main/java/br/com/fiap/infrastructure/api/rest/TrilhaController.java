package br.com.fiap.infrastructure.api.rest;

import br.com.fiap.interfaces.dto.TrilhaDTO;
import br.com.fiap.domain.model.Trilha;
import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.service.TrilhaService;
import br.com.fiap.domain.service.UsuarioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/trilhas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrilhaController {

    @Inject
    TrilhaService trilhaService;

    @Inject
    UsuarioService usuarioService;

    @POST
    public Response criarTrilha(TrilhaDTO trilhaDTO) {
        try {
            Usuario usuario = usuarioService.buscarPorId(trilhaDTO.getUsuarioId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

            Trilha trilha = new Trilha();
            trilha.setTitulo(trilhaDTO.getTitulo());
            trilha.setProgresso(trilhaDTO.getProgresso());
            trilha.setUsuario(usuario);

            Trilha trilhaSalva = trilhaService.salvarTrilha(trilha);
            return Response.status(Response.Status.CREATED)
                    .entity(toDTO(trilhaSalva))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar trilha: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarTrilha(@PathParam("id") Long id) {
        try {
            return trilhaService.buscarPorId(id)
                    .map(this::toDTO)
                    .map(dto -> Response.ok(dto).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar trilha: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response listarTrilhas() {
        try {
            List<TrilhaDTO> trilhas = trilhaService.listarTodas().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return Response.ok(trilhas).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar trilhas: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/progresso")
    public Response atualizarProgresso(@PathParam("id") Long id, @QueryParam("progresso") int progresso) {
        try {
            trilhaService.atualizarProgresso(id, progresso);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar progresso: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirTrilha(@PathParam("id") Long id) {
        try {
            return trilhaService.buscarPorId(id)
                    .map(trilha -> {
                        trilhaService.excluir(trilha);
                        return Response.noContent().build();
                    })
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir trilha: " + e.getMessage())
                    .build();
        }
    }

    private TrilhaDTO toDTO(Trilha trilha) {
        TrilhaDTO dto = new TrilhaDTO();
        dto.setIdTrilha(trilha.getIdTrilha());
        dto.setTitulo(trilha.getTitulo());
        dto.setProgresso(trilha.getProgresso());
        dto.setDataCriacao(trilha.getDataCriacao());
        dto.setDataAtualizacao(trilha.getDataAtualizacao());
        dto.setUsuarioId(trilha.getUsuario().getId());
        return dto;
    }
}