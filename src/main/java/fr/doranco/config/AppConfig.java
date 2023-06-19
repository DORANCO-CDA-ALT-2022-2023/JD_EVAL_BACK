package fr.doranco.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppConfig implements ServletContextListener {

  private static Properties properties;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("INIT CONFIG !");

    // properties = new Properties();
    //
    // try {
    // FileInputStream file = new FileInputStream("config.properties");
    // properties.load(file);
    // } catch (IOException e) {
    // e.printStackTrace();
    // return;
    // }
    //
    // String version = properties.getProperty("application.version");
    // System.out.println("SNAPSHOT : [" + version + "]");


    properties = new Properties();

    try (InputStream inputStream = AppConfig.class.getResourceAsStream("/config.properties")) {
      properties.load(inputStream);
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }

    String version = properties.getProperty("application.version");
    System.out.println("SNAPSHOT : [" + version + "]");
  }

  public static Properties getAppConfig() {
    return AppConfig.properties;
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }


}
