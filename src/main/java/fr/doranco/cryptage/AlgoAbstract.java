package fr.doranco.cryptage;

import java.io.IOException;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.doranco.config.AppConfig;

public abstract class AlgoAbstract {

  protected static SecretKey key;

  /* Properties for App */
  private static final Logger LOGGER = LogManager.getLogger(AlgoAbstract.class);

  public void setSecretKey() {
    try {
      key = AppConfig.loadKeyFromProperties();
      LOGGER.atInfo().log("LOAD KEY FROM PROPERTIES : {}", key.getAlgorithm());
    } catch (IOException e) {
      LOGGER.atError().log("IOException : {}", e.getMessage());
    }
  }

  public final static SecretKey getSecretKey() {
    return key;
  }

}
