package fr.doranco.cryptage.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.doranco.cryptage.IAlgoCrypto;

class AlgoAESTest {

  static IAlgoCrypto algoAES = new AlgoAES();
  static SecretKey key = null;
  static final String MSG_MOCK = "FR7630006000011234567890189";

  @BeforeAll
  static void testInit() {
    key = algoAES.generateKey();
  }

  @Test
  void testGenerateKey() {
    assertNotNull(key);
  }
  
  @Test
  void testEncryptToDecrypt() {
    byte[] cipherBytesEncoded = algoAES.encrypte(MSG_MOCK, key);

    assertNotNull(cipherBytesEncoded);

    byte[] cipherBytesDecoded = algoAES.decrypte(cipherBytesEncoded, key);

    assertNotNull(cipherBytesDecoded);
    assertEquals(algoAES.getMessagFromBytes(cipherBytesDecoded), MSG_MOCK);
  }

}
