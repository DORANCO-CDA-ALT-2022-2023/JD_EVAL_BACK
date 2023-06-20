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

      byte[] messageCryptedByte = cipher.doFinal(messageEncrypt.getBytes());
      String messageCrypted = this.getMessagFromBytes(messageCryptedByte);

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

      String messageDecrypted = this.getMessagFromBytes(messageDecryptedByte);

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

  @Override
  public String getMessagFromBytes(byte[] bytes) {
    return new String(bytes);
  }

}
