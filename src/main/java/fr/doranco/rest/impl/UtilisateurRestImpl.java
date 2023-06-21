package fr.doranco.rest.impl;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.doranco.dto.impl.ResponseAuthDto;
import fr.doranco.dto.impl.SignUpDto;
import fr.doranco.rest.RestAbstract;
import fr.doranco.services.impl.UtilisateurServiceImpl;

/**
 * Root resource for User (exposed at "user" path)
 */
@Path("user")
public class UtilisateurRestImpl extends RestAbstract {

  UtilisateurServiceImpl service = new UtilisateurServiceImpl();

  @Path("signup")
  @POST
  @Produces(MediaType.APPLICATION_JSON + CHARSET)
  @Consumes(MediaType.APPLICATION_JSON + CHARSET)
  public Response addCompte(@Valid SignUpDto dto) {

    Set<ConstraintViolation<SignUpDto>> violations = validator.validate(dto);

    if (!violations.isEmpty()) {
      ObjectNode errorJson = this.mapper.createObjectNode();

      // StringBuilder messageBuilder = new StringBuilder();
      for (ConstraintViolation<SignUpDto> violation : violations) {
        String fieldName = violation.getPropertyPath().toString();
        String errorMessageString = violation.getMessage();

        errorJson.put(fieldName, errorMessageString);
      }

      String errorResponse;
      try {
        errorResponse = this.mapper.writeValueAsString(errorJson);
      } catch (JsonProcessingException e) {
        return Response.status(Status.INTERNAL_SERVER_ERROR)
                       .entity("Error pendant generation de reponse")
                       .build();
      }

      return Response.status(Status.CONFLICT)
                     .entity(errorResponse)
                     .build();
    }
    //
    // this.isValid(dto);

    ResponseAuthDto response = service.signUp(dto);

    return Response.status(Status.CREATED)
                   .entity(response)
                   .build();
  }



  
}
