package fr.doranco.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.doranco.cryptage.IAlgoCrypto;
import fr.doranco.cryptage.impl.AlgoDES;

@WebListener
public class AppConfig implements ServletContextListener {
  /* Properties for App */
  private static Properties CONF_PROPERTIES;
  private static Properties SEC_PROPERTIES;

  private static final String FILE_PATH_SEC = "key_sec.properties";
  private static final String FILE_PATH_CONFIG = "/config.properties";

  private static final String RESOURCE_PATH = AppConfig.class.getClassLoader().getResource("").getPath();
  private static final String ABSOLUTE_FILE_PATH_SEC = RESOURCE_PATH + FILE_PATH_SEC;


  public static final String PROPERTY_APPLICATION_VERSION = "application.version";
  public static final String PROPERTY_KEY_DES = "des.key";

  /* Properties for App */
  private static final Logger LOGGER = LogManager.getLogger(AppConfig.class);
  
  private IAlgoCrypto DES = new AlgoDES();
  


  @Override
  public void contextInitialized(ServletContextEvent sce) {
    LOGGER.info("INIT CONFIG !");

    CONF_PROPERTIES = new Properties();
    SEC_PROPERTIES = new Properties();

    try (InputStream inputStream = AppConfig.class.getResourceAsStream(FILE_PATH_CONFIG)) {
      CONF_PROPERTIES.load(inputStream);
    } catch (IOException e) {
      LOGGER.atError().log("IOException : {}", e.getMessage());
    }

    String version = CONF_PROPERTIES.getProperty(PROPERTY_APPLICATION_VERSION);

    LOGGER.atInfo().log("SNAPSHOT {}", version);
    

    try {
      SecretKey key = DES.generateKey(); 
      
      LOGGER.atInfo().log("GET SEC KEY '{}' :: OK :: {}", AlgoDES.ALGO, key.toString());
      saveKeyToProperties(key);
    } catch (IOException e) {
      LOGGER.atError().log("IOException {}", e.getMessage());
    }
  }

  public static void saveKeyToProperties(SecretKey key) throws IOException {
    File file = new File(ABSOLUTE_FILE_PATH_SEC);
    // create file with properties;
    if (!file.exists()) {
      
      Properties properties = new Properties();
      properties.setProperty(PROPERTY_KEY_DES, Base64.getEncoder().encodeToString(key.getEncoded()));

      try (OutputStream outputStream = new FileOutputStream(ABSOLUTE_FILE_PATH_SEC)) {
        properties.store(outputStream, "DES Key");
      }

      LOGGER.atInfo().log("File created and saved in : {}", ABSOLUTE_FILE_PATH_SEC);

    } else {
      LOGGER.atWarn().log("File already exists in : {}", file.getAbsolutePath());
    }
    

  }

  public static SecretKey loadKeyFromProperties() throws IOException {
    Properties properties = new Properties();

    try (InputStream inputStream = new FileInputStream(ABSOLUTE_FILE_PATH_SEC)) {
      properties.load(inputStream);
    }

    String encodedKey = properties.getProperty(PROPERTY_KEY_DES);
    byte[] keyBytes = Base64.getDecoder().decode(encodedKey);

    SecretKey key = new SecretKeySpec(keyBytes, AlgoDES.ALGO);
    
    
    
    return key;
  }

 

  public static Properties getAppConfig() {
    return AppConfig.CONF_PROPERTIES;
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }



}
