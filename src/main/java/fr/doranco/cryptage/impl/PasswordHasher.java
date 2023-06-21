package fr.doranco.cryptage.impl;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
  private static final int LOG_ROUNDS = 10;

  public static String getHash(String password) {
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(LOG_ROUNDS));
    return hashedPassword;
  }

  public static boolean verifyPassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }
}
