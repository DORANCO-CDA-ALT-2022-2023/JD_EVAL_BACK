package fr.doranco.service.impls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import fr.doranco.services.impl.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

class JwtServiceTest {

  private static final int ID_USER = 1;
  private static final String ROLE = "Admin";

  private static String tokenMock;

  @Test
  void testWhenGenerateToken_thenIsValid() {

    tokenMock = JwtService.generateToken(Integer.toString(ID_USER), ROLE);

    try {
      Jws<Claims> jws = JwtService.validateToken(tokenMock);
      Claims claims = jws.getBody();

      String extractedUserId = (String) claims.getSubject();
//      String extractedRole = (String) claims.get("role");

      assertEquals(ID_USER, extractedUserId);
//      assertEquals(ROLE, extractedRole);

    } catch (JwtException e) {
      // assertThrows(ExpiredJwtException.class, () -> JwtService.validateToken(tokenMock));
      fail("Token is invalid");
    }

  }

}
