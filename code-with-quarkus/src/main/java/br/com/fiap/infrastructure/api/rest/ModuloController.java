package br.com.fiap.infrastructure.api.rest;

import br.com.fiap.interfaces.dto.ModuloDTO;
import br.com.fiap.domain.model.Modulo;
import br.com.fiap.domain.model.Trilha;
import br.com.fiap.domain.service.ModuloService;
import br.com.fiap.domain.service.TrilhaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/modulos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModuloController {

    @Inject
    ModuloService moduloService;

    @Inject
    TrilhaService trilhaService;

    @POST
    public Response criarModulo(ModuloDTO moduloDTO) {
        try {
            Trilha trilha = trilhaService.buscarPorId(moduloDTO.getTrilhaId())
                    .orElseThrow(() -> new NotFoundException("Trilha não encontrada"));

            Modulo modulo = new Modulo();
            modulo.setTitulo(moduloDTO.getTitulo());
            modulo.setConteudo(moduloDTO.getConteudo());
            modulo.setOrdem(moduloDTO.getOrdem());
            modulo.setConcluido(moduloDTO.isConcluido());
            modulo.setDataConclusao(moduloDTO.getDataConclusao());
            modulo.setTrilha(trilha);

            Modulo moduloSalvo = moduloService.salvarModulo(modulo);
            return Response.status(Response.Status.CREATED)
                    .entity(toDTO(moduloSalvo))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar módulo: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarModulo(@PathParam("id") Long id) {
        try {
            return moduloService.buscarPorId(id)
                    .map(this::toDTO)
                    .map(dto -> Response.ok(dto).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar módulo: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response listarModulos() {
        try {
            List<ModuloDTO> modulos = moduloService.listarTodos().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return Response.ok(modulos).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar módulos: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}/concluir")
    public Response marcarComoConcluido(@PathParam("id") Long id) {
        try {
            moduloService.marcarComoConcluido(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao marcar módulo como concluído: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirModulo(@PathParam("id") Long id) {
        try {
            return moduloService.buscarPorId(id)
                    .map(modulo -> {
                        moduloService.excluir(modulo);
                        return Response.noContent().build();
                    })
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir módulo: " + e.getMessage())
                    .build();
        }
    }

    private ModuloDTO toDTO(Modulo modulo) {
        ModuloDTO dto = new ModuloDTO();
        dto.setIdModulo(modulo.getIdModulo());
        dto.setTitulo(modulo.getTitulo());
        dto.setConteudo(modulo.getConteudo());
        dto.setOrdem(modulo.getOrdem());
        dto.setConcluido(modulo.isConcluido());
        dto.setDataConclusao(modulo.getDataConclusao());
        dto.setTrilhaId(modulo.getTrilha().getIdTrilha());
        return dto;
    }
}