package fr.doranco.rest.impl;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import fr.doranco.dto.utilisateur.ResponseAuthDto;
import fr.doranco.dto.utilisateur.SignUpDto;
import fr.doranco.dto.utilisateur.logInDto;
import fr.doranco.entities.Utilisateur;
import fr.doranco.exception.ErrorException;
import fr.doranco.rest.RestAbstract;
import fr.doranco.services.impl.UtilisateurServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("user")
@Tag(
    name = "Utilisateurs",
    description = "API relative au Utilisateurs (creation, modification, gestion)")
public class UtilisateurRestImpl extends RestAbstract {

  UtilisateurServiceImpl service = new UtilisateurServiceImpl();


  @Path("signup")
  @POST
  @Produces(MediaType.APPLICATION_JSON + CHARSET)
  @Consumes(MediaType.APPLICATION_JSON + CHARSET)
  @Operation(
      summary = "Creation d'un compte",
      description = "Par défault le compte n'est pas acitivé (il faut vallider par e-mail)")
  @ApiResponse(
      responseCode = "201",
      description = "Utilisateur avec email {email} est enregistré!")
  @ApiResponse(
      responseCode = "400",
      description = "Verification de violation (ex. \"password\": \"ne doit pas être nul\", \"email\": \"Email n'est pas correcte\"")
  @ApiResponse(
      responseCode = "500",
      description = "Server erreur (ex. \"message\": \"Erreur pendant de création d'un utilisateur\"")
  public Response signUp(
                         @RequestBody(
                             description = "Reception email et password",
                             required = true,
                             content = @Content(
                                 mediaType = MediaType.APPLICATION_JSON,
                                 schema = @Schema(
                                     implementation = SignUpDto.class)))
                         @Valid SignUpDto dto) {
    LOGGER.atInfo().log("SignUpDto :: {}", dto);

    ResponseAuthDto response = null;

    try {
      response = service.createCompte(dto);
      return Response.status(Status.CREATED)
                     .entity(response)
                     .build();
    } catch (ErrorException e) {
      return Response.status(e.getCode())
                     .entity(e.getCode() != 400 ? ResponseAuthDto.builder()
                                                                 .message(e.getMessage())
                                                                 .build() :
                                                e.getMessage())
                     .build();
    } catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR)
                     .entity(ResponseAuthDto.builder()
                                            .message(e.getMessage())
                                            .build())
                     .build();
    }
  }


  @Path("login")
  @POST
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON + CHARSET)
  @Consumes(MediaType.APPLICATION_JSON + CHARSET)
  @Operation(
      summary = "Authentification d'un utilisateur",
      description = "Reponse avec JWT (dans le cookies) ou nous avons ID utilisateur et ID rôle")
  @ApiResponse(
      responseCode = "200",
      description = "Bienvenu {email} !")
  @ApiResponse(
      responseCode = "204",
      description = "Pas de utilisateur dans BDD, donc \"Not Contete\"")
  @ApiResponse(
      responseCode = "401",
      description = "Unauthorized | \"message\": \"Mot de passe ou e-mail sont incorrectes\"")
  @ApiResponse(
      responseCode = "400",
      description = "Verification de violation (ex. \"password\": \"ne doit pas être nul\", \"email\": \"Email n'est pas correcte\"")
  @ApiResponse(
      responseCode = "500",
      description = "Server erreur")
  public Response logIn(
                        @RequestBody(
                            description = "Reception email et password",
                            required = true,
                            content = @Content(
                                mediaType = MediaType.APPLICATION_JSON,
                                schema = @Schema(
                                    implementation = logInDto.class)))
                        @Valid logInDto dto) throws Exception {
    LOGGER.atInfo().log("logIn :: {}", dto);

    ResponseAuthDto response = null;

    try {
      response = service.authentification(dto);

      return Response.status(Status.OK)
                     .entity(ResponseAuthDto.builder()
                                            .message(response.getMessage())
                                            .build())
                     .cookie(new NewCookie(response.getCookieWithJwt()))
                     .build();
    } catch (ErrorException e) {
      return Response.status(e.getCode())
                     .entity(e.getCode() != 400 ? ResponseAuthDto.builder()
                                                                 .message(e.getMessage())
                                                                 .build() :
                                                e.getMessage())
                     .build();
    } catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR)
                     .entity(ResponseAuthDto.builder()
                                            .message(e.getMessage())
                                            .build())
                     .build();
    }
  }


  @Path("all")
  @GET
  @RolesAllowed({"Admin"})
  @Produces(MediaType.APPLICATION_JSON + CHARSET)
  @Consumes(MediaType.APPLICATION_JSON + CHARSET)
  @Operation(
      summary = "Endpoint pour récupérer la liste de tous les utilisateurs dans SI (accessible seulement pour l'admin)",
      description = "Reponse avec JWT (dans le cookies) ou nous avons ID utilisateur et ID rôle")
  @ApiResponse(
      responseCode = "200",
      description = "La liste de toutes utilisateur")
  @ApiResponse(
      responseCode = "204",
      description = "Aucune de utilisateur dans BDD, donc \"Not Contete\"")
  @ApiResponse(
      responseCode = "403",
      description = "Forbidden | \"message\": \"Mot de passe ou e-mail sont incorrectes\"")
  @ApiResponse(
      responseCode = "400",
      description = "Verification de violation (ex. \"password\": \"ne doit pas être nul\", \"email\": \"Email n'est pas correcte\"")
  @ApiResponse(
      responseCode = "500",
      description = "Server erreur")
  public Response getAllForAdmin(@QueryParam("role") String role) throws Exception {
    LOGGER.atInfo().log("logIn :: {}", role);

    ResponseAuthDto response = null;

    try {
      List<Utilisateur> liste = service.getAllUser(role);

      return Response.status(Status.OK)
                     .entity(this.mapper.writeValueAsString(liste))
                     .build();
    } catch (ErrorException e) {
      return Response.status(e.getCode())
                     .entity(e.getCode() != 400 ? ResponseAuthDto.builder()
                                                                 .message(e.getMessage())
                                                                 .build() :
                                                e.getMessage())
                     .build();
    } catch (Exception e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR)
                     .entity(ResponseAuthDto.builder()
                                            .message(e.getMessage())
                                            .build())
                     .build();
    }
  }


}
