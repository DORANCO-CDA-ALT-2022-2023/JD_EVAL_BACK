package fr.doranco.rest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.doranco.config.AppConfig;

public abstract class RestAbstract {
  protected static final String CHARSET = ";charset=UTF-8";

  protected ObjectMapper mapper = new ObjectMapper();

  private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  protected Validator validator = factory.getValidator();

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getIt() {
    String appVersion = AppConfig.getAppConfig().getProperty("application.version");

    return ("Got it => " + this.getClass() + " | " + appVersion);
  }

  
  
}
