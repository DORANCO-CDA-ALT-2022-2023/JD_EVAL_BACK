package fr.doranco.services.impl;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.doranco.config.AppConfig;
import fr.doranco.cryptage.AlgoAbstract;
import fr.doranco.cryptage.IAlgoCrypto;
import fr.doranco.cryptage.impl.AlgoAES;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtService {
  private static String SECRET_KEY_STRING;
  private static SecretKey SEC_KEY;
  private static long EXPIRATION_TIME;

  IAlgoCrypto aesCrypto;

  private static final Logger LOGGER = LogManager.getLogger(JwtService.class);


  public JwtService() {
    SECRET_KEY_STRING = AppConfig.getAppConfig()
                          .getProperty(AppConfig.PROPERTY_KEY_AES);

    aesCrypto = new AlgoAES();

    SEC_KEY = aesCrypto.generateKey();

    byte[] KEY_MOCK_BYTE = aesCrypto.encrypte(SECRET_KEY_STRING, SEC_KEY);
    try {
      SECRET_KEY_STRING = AlgoAbstract.getMessagFromBytes(KEY_MOCK_BYTE);
      System.err.println(SECRET_KEY_STRING);

      LOGGER.atInfo().log("SECRET_KEY {} :: EXPIRATION_TIME {}", SECRET_KEY_STRING, EXPIRATION_TIME);
    } catch (UnsupportedEncodingException e) {
      LOGGER.atError().log("Encodage probleme :: {}", e.getMessage());
    }

    // after 8h
    EXPIRATION_TIME = 3600000 + 8;


  }

  // POUR TESTING
  public JwtService(String jwt_key, long exprirationTime) {
    SECRET_KEY_STRING = jwt_key;

    EXPIRATION_TIME = exprirationTime;
  }



  public static String generateToken(String idUser, String role) {
    System.err.println(SECRET_KEY_STRING);
    Key key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    // install id avec id role (claims)
    Claims claims = Jwts.claims().setSubject(idUser);
    claims.put("role_id", role);

    Date now = new Date();
    Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

    return Jwts.builder()
               .setClaims(claims)
               .setIssuedAt(now)
               .setExpiration(expiration)
               .signWith(key, SignatureAlgorithm.HS256)
               .compact();
  }


  public static Jws<Claims> validateToken(String token) {
    Key key = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());

    return Jwts.parserBuilder()
               .setSigningKey(key)
               .build()
               .parseClaimsJws(token);
  }
}
