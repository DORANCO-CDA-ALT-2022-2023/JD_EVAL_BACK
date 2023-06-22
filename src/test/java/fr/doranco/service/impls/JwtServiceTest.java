package fr.doranco.service.impls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.doranco.cryptage.AlgoAbstract;
import fr.doranco.cryptage.IAlgoCrypto;
import fr.doranco.cryptage.impl.AlgoAES;
import fr.doranco.services.impl.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

class JwtServiceTest {

  private static final int ID_USER = 1;
  private static final String ROLE = "Admin";

  static IAlgoCrypto aesCrypto;

  private static String KEY_MOCK = "MON_KEY_FORT";
  static SecretKey key = null;
  private static final long TIME_LIFE = 100000;

  private static String tokenMock;

  private static JwtService jwtService;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    aesCrypto = new AlgoAES();

    key = aesCrypto.generateKey();

    byte[] KEY_MOCK_BYTE = aesCrypto.encrypte(KEY_MOCK, key);
    KEY_MOCK = AlgoAbstract.getMessagFromBytes(KEY_MOCK_BYTE);

    jwtService = new JwtService(KEY_MOCK, TIME_LIFE);
  }

  @Test
  void testWhenGenerateToken_thenIsValid() {

    tokenMock = JwtService.generateToken(Integer.toString(ID_USER), ROLE);

    assertNotNull(tokenMock);

    try {
      Jws<Claims> jws = JwtService.validateToken(tokenMock);
      Claims claims = jws.getBody();

      int extractedUserId = Integer.parseInt(claims.getSubject());
      String extractedRole = (String) claims.get(JwtService.ROLE_ID);

      System.out.println("extractedUserId : " + extractedUserId);
      assertEquals(ID_USER, extractedUserId);
      assertEquals(ROLE, extractedRole);

    } catch (JwtException e) {
      // assertThrows(ExpiredJwtException.class, () -> JwtService.validateToken(tokenMock));
      fail("Token is invalid");
    }

  }

}
