package fr.doranco.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorException extends Exception {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int code;

  private String message;

  public ErrorException(int code, String message) {
    super();
    this.code = code;
    this.message = message;
  }

}
