package fr.doranco.cryptage.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import fr.doranco.cryptage.AlgoAbstract;
import fr.doranco.cryptage.IAlgoCrypto;

public class AlgoDES extends AlgoAbstract
                     implements IAlgoCrypto {

  // private static final Logger LOGGER = LogManager.getLogger(AlgoDES.class);

  public AlgoDES() {}

  public static final String ALGO = "DES";


  @Override
  public SecretKey generateKey() {
    KeyGenerator keyGen = null;

    try {

      keyGen = KeyGenerator.getInstance(AlgoDES.ALGO);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    keyGen.init(56);

    // this.key = keyGen.generateKey();
    key = keyGen.generateKey();

    return key;
  }

  @Override
  public byte[] encrypte(String messageEncrypt, SecretKey key) {

    String messageCrypted = null;
    Cipher cipher = null;
    byte[] cipherBytes = null;

    try {
      cipher = Cipher.getInstance(AlgoDES.ALGO);

      cipher.init(Cipher.ENCRYPT_MODE, key);

      byte[] messageBytes = messageEncrypt.getBytes("UTF-8");
      cipherBytes = cipher.doFinal(messageBytes);

      messageCrypted = this.getMessagFromBytes(cipherBytes);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    LOGGER.atInfo().log("INFO cipher : {} | {}", cipher.getAlgorithm(), cipher.getProvider().getInfo());
    LOGGER.atInfo()
          .log("MSG CRYPTED : {} | {}",
               messageCrypted,
               messageCrypted.getClass().getSimpleName());


    return cipherBytes;
  }

  @Override
  public byte[] decrypte(byte[] cipherBytes, SecretKey key) {

    Cipher cipher = null;

    byte[] cipherBytesDecoded = null;

    try {
      cipher = Cipher.getInstance(AlgoDES.ALGO);

      cipher.init(Cipher.DECRYPT_MODE, key);

      cipherBytesDecoded = cipher.doFinal(cipherBytes);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    String messageDecripted = this.getMessagFromBytes(cipherBytesDecoded);

    LOGGER.atInfo()
          .log("MSG DECRYPTED : {} | {}",
               messageDecripted,
               messageDecripted.getClass().getSimpleName());

    return cipherBytesDecoded;
  }

  @Override
  public String getMessagFromBytes(byte[] bytes) {
    String message = null;
    try {
      message = new String(bytes, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return message;
  }


}
