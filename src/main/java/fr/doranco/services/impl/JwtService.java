package fr.doranco.services.impl;

import java.security.Key;
import java.util.Date;
import fr.doranco.config.AppConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtService {
  private static final String SECRET_KEY = AppConfig.getAppConfig()
                                                    .getProperty(AppConfig.PROPERTY_JWT_KEY);

  // la duré de vié est 12h
  private static final long EXPIRATION_TIME = 3600000 + 12;

  
  public static String generateToken(String idUser, String role) {
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

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
    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    return Jwts.parserBuilder()
               .setSigningKey(key)
               .build()
               .parseClaimsJws(token);
  }
}
