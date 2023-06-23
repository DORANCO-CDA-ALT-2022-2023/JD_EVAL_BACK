package fr.doranco.provider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.doranco.dto.utilisateur.ResponseAuthDto;
import fr.doranco.services.impl.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

@Provider
public class AuthFilter implements ContainerRequestFilter {

  @Context
  ResourceInfo resourceInfo;

  private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);


  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    Method method = resourceInfo.getResourceMethod();

    // GET VALUES FROM TOKEN
    Cookie cookie = requestContext.getCookies().get("accessToken");
    if (cookie == null) {
      // NO token == "Unauthorized" (401)
      LOGGER.atWarn().log("Cookies vide");
      // requestContext.abortWith(this.getAccesDeniedResponse());
    }

    String token = cookie.getValue();

    // VALIDATE JWT
    Jws<Claims> jws = null;
    Claims claims = null;
    int extractedUserId = -1;
    String extractedRole = null;
    if (StringUtils.isNotEmpty(cookie.getValue()))
      try {
        jws = JwtService.validateToken(token);
        claims = jws.getBody();

        extractedUserId = Integer.parseInt(claims.getSubject());
        extractedRole = (String) claims.get(JwtService.ROLE_ID);
      } catch (Exception e) {
        requestContext.abortWith(this.getAccesDeniedWhenTokenDead());
        return;
      }



    System.err.println("extractedUserId : " + extractedUserId + " extractedRole : " + extractedRole);
    System.err.println("ctx : " + requestContext.getSecurityContext().isUserInRole("Admin"));

    Set<String> rolesSet = null;
    if (method.isAnnotationPresent(RolesAllowed.class)) {
      RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);

      rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

      if (rolesSet.contains("Admin") &&
          !extractedRole.equals("Admin")) {
        LOGGER.atWarn().log("Seulement Admin droit");

        requestContext.abortWith(this.getAccesDeniedResponse());
        return;
      }

      if (rolesSet.contains("Magasinier") &&
          !extractedRole.equals("Magasinier")) {
        LOGGER.atWarn().log("Seulement Magasinier droit");

        requestContext.abortWith(this.getAccesDeniedResponse());
        return;
      }
    }



    if (method.isAnnotationPresent(PermitAll.class)) {
      LOGGER.atInfo().log("In @PermitAll");
    }
  }



  public Response getAccesDeniedResponse() {
    return Response.status(Status.FORBIDDEN)
                   .entity(ResponseAuthDto.builder()
                                          .message("Vous n'avez pas de droit à cette resource")
                                          .build())
                   .build();
  }


  public Response getAccesDeniedWhenTokenDead() {
    return Response.status(Status.FORBIDDEN)
                   .entity(ResponseAuthDto.builder()
                                          .message("Vous n'avez pas de droit à cette resource")
                                          .build())
                   .cookie(new NewCookie(new Cookie("accessToken", null)))
                   .build();
  }


  public Response getAccesForbiddenResponse() {
    return Response.status(Status.FORBIDDEN)
                   .entity("Accès bloque à tous !")
                   .build();
  }
}
