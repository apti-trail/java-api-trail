package br.com.fiap.infrastructure.api.rest;

import br.com.fiap.interfaces.dto.LoginDTO;
import br.com.fiap.interfaces.dto.UsuarioDTO;
import br.com.fiap.domain.model.Usuario;
import br.com.fiap.domain.service.UsuarioService;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioController {

    @Inject
    UsuarioService usuarioService;

    @POST
    public Response criarUsuario(UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = new Usuario();
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setSenha("senha_default"); // Em produção, usar hash + tratamento específico

            Usuario usuarioSalvo = usuarioService.salvar(usuario);
            UsuarioDTO responseDTO = toDTO(usuarioSalvo);

            return Response.status(Response.Status.CREATED)
                    .entity(responseDTO)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar usuário: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarUsuario(@PathParam("id") Long id) {
        try {
            return usuarioService.buscarPorId(id)
                    .map(this::toDTO)
                    .map(dto -> Response.ok(dto).build())
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar usuário: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response listarUsuarios() {
        try {
            List<UsuarioDTO> usuarios = usuarioService.listarTodos().stream()
                    .map(this::toDTO)
                    .collect(Collectors.toList());
            return Response.ok(usuarios).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar usuários: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarUsuario(@PathParam("id") Long id, UsuarioDTO usuarioDTO) {
        try {
            return usuarioService.buscarPorId(id)
                    .map(usuario -> {
                        usuario.setUsername(usuarioDTO.getUsername());
                        usuario.setEmail(usuarioDTO.getEmail());
                        Usuario usuarioAtualizado = usuarioService.salvar(usuario);
                        return Response.ok(toDTO(usuarioAtualizado)).build();
                    })
                    .orElse(Response.status(Response.Status.NOT_FOUND).build());
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar usuário: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response excluirUsuario(@PathParam("id") Long id) {
        try {
            usuarioService.excluir(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao excluir usuário: " + e.getMessage())
                    .build();
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginDTO loginDTO) {
        try {
            boolean loginValido = usuarioService.validarLogin(loginDTO.getEmail(), loginDTO.getSenha());
            if (loginValido) {
                return usuarioService.buscarPorEmail(loginDTO.getEmail())
                        .map(this::toDTO)
                        .map(dto -> Response.ok(dto).build())
                        .orElse(Response.status(Response.Status.UNAUTHORIZED).build());
            }
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Credenciais inválidas")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao realizar login: " + e.getMessage())
                    .build();
        }
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getEmail()
        );
    }
}