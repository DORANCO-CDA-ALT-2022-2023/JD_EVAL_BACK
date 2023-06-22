package fr.doranco.services;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class ServiceAbsctract {
  protected static final Logger LOGGER = LogManager.getLogger(ServiceAbsctract.class);

  private ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  protected Validator validator = factory.getValidator();

  protected ObjectMapper mapper = new ObjectMapper();


  /**
   * @deprecated
   *
   *             Methode pour capter violation sur DTO
   * 
   * @param <T>
   * @param dto
   * @param violations
   * @param successSupplier
   * @param errorSupplier
   * @return
   */
  protected <T> T
            handleViolations(Object dto,
                             Set<ConstraintViolation<Object>> violations,
                             Supplier<T> successSupplier,
                             Function<String, T> errorSupplier) {
    if (!violations.isEmpty()) {
      ObjectNode errorJson = this.mapper.createObjectNode();

      for (ConstraintViolation<Object> violation : violations) {
        String fieldName = violation.getPropertyPath().toString();
        String errorMessageString = violation.getMessage();

        errorJson.put(fieldName, errorMessageString);
      }

      String errorResponse = null;
      try {
        errorResponse = this.mapper.writeValueAsString(errorJson);
      } catch (JsonProcessingException e) {
        return errorSupplier.apply("Error pendant generation de reponse");
      }

      return errorSupplier.apply(errorResponse);
    }

    return successSupplier.get();
  }


  /**
   * @param dto
   * @param violations
   * @return
   */
  protected String getMessagesViolation(Object dto,
                                        Set<ConstraintViolation<Object>> violations) {
    ObjectNode errorJson = this.mapper.createObjectNode();

    for (ConstraintViolation<Object> violation : violations) {
      String fieldName = violation.getPropertyPath().toString();
      String errorMessageString = violation.getMessage();

      errorJson.put(fieldName, errorMessageString);
    }

    String errorResponse = null;
    try {
      errorResponse = this.mapper.writeValueAsString(errorJson);
      return errorResponse;
    } catch (JsonProcessingException e) {
      return "Erreur pendant generation de message";
    }
  }
}
