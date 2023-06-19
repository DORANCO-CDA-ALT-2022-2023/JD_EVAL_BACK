package fr.doranco.cryptage.impl;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import fr.doranco.cryptage.AlgoAbstract;
import fr.doranco.cryptage.IAlgoCrypto;

public class AlgoAES extends AlgoAbstract
                     implements IAlgoCrypto {

  public AlgoAES() {}

  public static final String ALGO = "AES";

  @Override
  public SecretKey generateKey() {
    SecureRandom secureRandom = new SecureRandom();
    
    byte[] keyBytes = new byte[16];
    
    secureRandom.nextBytes(keyBytes);
    
    return new SecretKeySpec(keyBytes, ALGO);
  }

  @Override
  public byte[] encrypte(String messageEncrypt, SecretKey key) {
    try {
      Cipher cipher = Cipher.getInstance(ALGO);
      cipher.init(Cipher.ENCRYPT_MODE, key);

      return cipher.doFinal(messageEncrypt.getBytes());

    } catch (Exception e) {
      throw new RuntimeException("AES encryption failed.", e);
    }
  }

  @Override
  public byte[] decrypte(byte[] cipherBytes, SecretKey key) {
    try {
      Cipher cipher = Cipher.getInstance(ALGO);
      cipher.init(Cipher.DECRYPT_MODE, key);
      
      return cipher.doFinal(cipherBytes);
      
    } catch (Exception e) {
      throw new RuntimeException("AES decryption failed.", e);
    }
  }

  @Override
  public String getMessagFromBytes(byte[] bytes) {
    return new String(bytes);
  }

}
