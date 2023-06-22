package fr.doranco.cryptage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.doranco.config.AppConfig;

public abstract class AlgoAbstract {

  protected static SecretKey key;

  /* Properties for App */
  protected static final Logger LOGGER = LogManager.getLogger(AlgoAbstract.class);

  // public void setSecretKey() {
  // try {
  // key = AppConfig.loadKeyFromProperties();
  // LOGGER.atInfo().log("LOAD KEY FROM PROPERTIES : {}", key.getAlgorithm());
  // } catch (IOException e) {
  // LOGGER.atError().log("IOException : {}", e.getMessage());
  // }
  // }

  public final static SecretKey getSecretKey() {
    try {
      key = AppConfig.loadKeyFromProperties();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return key;
  }

  public static String getMessagFromBytes(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes, "UTF-8");
  }

  private void messageCrypted(String message) {

  }

  private void messageDecrypted(String message) {

  }
}
