package fr.doranco.cryptage.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import fr.doranco.cryptage.AlgoAbstract;
import fr.doranco.cryptage.IAlgoCrypto;

public class AlgoAES extends AlgoAbstract
                     implements IAlgoCrypto {

  public static final String ALGO = "AES";
  private static final int KEY_SIZE_BITS = 256;


  public AlgoAES() {
//    try {
//      key = AppConfig.loadKeyFromProperties(AppConfig.PROPERTY_KEY_DES, ALGO);
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
  }



  /**
   * {@link Deprecated https://datatracker.ietf.org/doc/html/rfc7518#section-3.2}
   */
  // @Override
  // public SecretKey generateKey() {
  // SecureRandom secureRandom = new SecureRandom();
  //
  // byte[] keyBytes = new byte[16];
  //
  // secureRandom.nextBytes(keyBytes);
  //
  // return new SecretKeySpec(keyBytes, ALGO);
  // }


  /**
   * Ce key AES utilisé pour JWT, avec respecte du RFC 7518, Section 3.2
   * 
   * The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms
   * MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output
   * size).
   * 
   * @return SecretKey
   */
  @Override
  public SecretKey generateKey() {
    KeyGenerator keyGenerator = null;

    try {
      keyGenerator = KeyGenerator.getInstance(ALGO);
    } catch (NoSuchAlgorithmException e) {
      LOGGER.atError().log("Probleme generation de Clé AES : {}", e.getMessage());
      e.printStackTrace();
    }

    keyGenerator.init(KEY_SIZE_BITS, new SecureRandom());

    SecretKey secretKey = keyGenerator.generateKey();

    return new SecretKeySpec(secretKey.getEncoded(), ALGO);
  }

  @Override
  public byte[] encrypte(String messageEncrypt, SecretKey key) {
    try {
      Cipher cipher = Cipher.getInstance(ALGO);
      cipher.init(Cipher.ENCRYPT_MODE, key);

      byte[] messageCryptedByte = cipher.doFinal(messageEncrypt.getBytes());
      String messageCrypted = AlgoAbstract.getMessagFromBytes(messageCryptedByte);

      LOGGER.atInfo().log("INFO cipher : {} | {}", cipher.getAlgorithm(), cipher.getProvider().getInfo());
      LOGGER.atInfo()
            .log("MSG CRYPTED : {} | {}",
                 messageCrypted,
                 messageCrypted.getClass().getSimpleName());

      return messageCryptedByte;

    } catch (Exception e) {
      LOGGER.atError()
            .log("AES encryption failed : {}", e.getMessage());
      throw new RuntimeException("AES encryption failed.", e);
    }
  }

  @Override
  public byte[] decrypte(byte[] cipherBytes, SecretKey key) {
    try {
      Cipher cipher = Cipher.getInstance(ALGO);
      cipher.init(Cipher.DECRYPT_MODE, key);

      byte[] messageDecryptedByte = cipher.doFinal(cipherBytes);

      String messageDecrypted = AlgoAbstract.getMessagFromBytes(messageDecryptedByte);

      LOGGER.atInfo()
            .log("MSG DECRYPTED : {} | {}",
                 messageDecrypted,
                 messageDecrypted.getClass().getSimpleName());

      return messageDecryptedByte;

    } catch (Exception e) {
      LOGGER.atError()
            .log("AES decryption failed : {}", e.getMessage());
      throw new RuntimeException("AES decryption failed.", e);
    }
  }

}
