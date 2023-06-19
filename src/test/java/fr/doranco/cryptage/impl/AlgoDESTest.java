package fr.doranco.cryptage.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.doranco.cryptage.IAlgoCrypto;

class AlgoDESTest {

  static IAlgoCrypto algoDES = new AlgoDES();
  static SecretKey key = null;
  static final String MSG_MOCK = "FR7630001007941234567890185";

  @BeforeAll
  static void testInit() {
    key = algoDES.generateKey();
  }

  @Test
  void testGenerateKey() {
    assertNotNull(key);
  }
  
  @Test
  void testEncryptToDecrypt() {
    byte[] cipherBytesEncoded = algoDES.encrypte(MSG_MOCK, key);

    assertNotNull(cipherBytesEncoded);

    byte[] cipherBytesDecoded = algoDES.decrypte(cipherBytesEncoded, key);

    assertNotNull(cipherBytesDecoded);
    assertEquals(algoDES.getMessagFromBytes(cipherBytesDecoded), MSG_MOCK);
  }

}
