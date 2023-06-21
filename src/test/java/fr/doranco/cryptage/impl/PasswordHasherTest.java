package fr.doranco.cryptage.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PasswordHasherTest {
  
  public static final String PASSWORD = "BEST_PASSWORD_IN_THE_WORLD";
  public static String hash = null;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    hash = PasswordHasher.getHash(PASSWORD);
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  @Test
  void testVerifyHash_thenTrue() {
    assertTrue(PasswordHasher.verifyPassword(PASSWORD, hash));
  }
  
  @Test
  void testVerifyHash_thenFalse() {
    assertFalse(PasswordHasher.verifyPassword(PASSWORD + "No_good", hash));
  }

}
