package fr.doranco.cryptage.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.UnsupportedEncodingException;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.doranco.cryptage.AlgoAbstract;
import fr.doranco.cryptage.IAlgoCrypto;

class AlgoSHA512Test {

  static IAlgoCrypto algoSHA512 = new AlgoSHA512();
  static SecretKey key = null;
  static final String MSG_MOCK = "KEY_FORT";

  @BeforeAll
  static void testInit() {
    
    key = algoSHA512.generateKey();
  }

  @Test
  void testGenerateKey() {
    assertNotNull(key);
  }
  
  @Test
  void testEncryptToDecrypt() throws UnsupportedEncodingException {
    byte[] cipherBytesEncoded = algoSHA512.encrypte(MSG_MOCK, key);

    assertNotNull(cipherBytesEncoded);

    byte[] cipherBytesDecoded = algoSHA512.decrypte(cipherBytesEncoded, key);

    assertNotNull(cipherBytesDecoded);
    assertEquals(AlgoAbstract.getMessagFromBytes(cipherBytesDecoded), MSG_MOCK);
  }

}
