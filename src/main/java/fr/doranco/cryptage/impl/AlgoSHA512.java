package fr.doranco.cryptage.impl;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import fr.doranco.cryptage.AlgoAbstract;
import fr.doranco.cryptage.IAlgoCrypto;

public class AlgoSHA512 extends AlgoAbstract
                        implements IAlgoCrypto {

  public static final String ALGO = "HmacSHA512";
  private static final int KEY_SIZE_BITS = 512;

  @Override
  public SecretKey generateKey() {

    byte[] keyBytes = new byte[64]; // 64 bytes = 512 bits
    SecureRandom secureRandom = new SecureRandom();
    secureRandom.nextBytes(keyBytes);

    // Create an HMAC key specification
    SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGO);

    // Create the HMAC object
    Mac mac = null;
    try {
      mac = Mac.getInstance("HmacSHA512");

      mac.init(keySpec);
    } catch (NoSuchAlgorithmException e) {
      LOGGER.atError().log("Probleme generation de Clé HmacSHA512 : {}", e.getMessage());
    } catch (InvalidKeyException e) {
      LOGGER.atError().log("Probleme generation de Clé HmacSHA512 : {}", e.getMessage());
    }

    // Get the generated key
    return new SecretKeySpec(mac.doFinal(), ALGO);
  }

  @Override
  public byte[] encrypte(String messageEncrypt, SecretKey key) {
    Cipher cipher = null;
    try {
      cipher = Cipher.getInstance(ALGO);

      cipher.init(Cipher.ENCRYPT_MODE, key);

      return cipher.doFinal(messageEncrypt.getBytes());
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      LOGGER.atError().log("Probleme encryptage avec HmacSHA512 : {}", e.getMessage());
    } catch (InvalidKeyException e) {
      LOGGER.atError().log("Probleme encryptage avec HmacSHA512 : {}", e.getMessage());
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      LOGGER.atError().log("Probleme encryptage avec HmacSHA512 : {}", e.getMessage());
    }

    return null;
  }

  @Override
  public byte[] decrypte(byte[] cipherBytes, SecretKey key) {
    Cipher cipher;
    try {
      cipher = Cipher.getInstance(ALGO);

      cipher.init(Cipher.DECRYPT_MODE, key);

      return cipher.doFinal(cipherBytes);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalBlockSizeException | BadPaddingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

}
