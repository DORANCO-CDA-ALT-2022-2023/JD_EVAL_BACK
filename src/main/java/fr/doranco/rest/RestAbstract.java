package fr.doranco.rest;

import javax.annotation.security.RolesAllowed;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.doranco.config.AppConfig;
import fr.doranco.services.ServiceAbsctract;

public abstract class RestAbstract {
  protected static final String CHARSET = ";charset=UTF-8";

  protected static final Logger LOGGER = LogManager.getLogger(ServiceAbsctract.class);

  protected ObjectMapper mapper = new ObjectMapper();

  private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  protected Validator validator = factory.getValidator();


  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String getIt() {
    String appVersion = AppConfig.getAppConfig().getProperty("application.version");

    return ("Got it => " + this.getClass() + " | " + appVersion);
  }

  @GET
  @Path("test")
  @Produces(MediaType.TEXT_PLAIN)
  @RolesAllowed({"Admin", "Magasinier"})
  public String test() {
    String appVersion = AppConfig.getAppConfig().getProperty("application.version");

    return ("Test => " + this.getClass() + " | " + appVersion);
  }


}
