package fr.doranco.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import fr.doranco.config.AppConfig;

public abstract class RestAbstract {
  protected static final String CHARSET = ";charset=UTF-8";


  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getIt() {
    String appVersion = AppConfig.getAppConfig().getProperty("application.version");

    return ("Got it => " + this.getClass() + " | " + appVersion);
  }
}
