package eu.sanjin.kurelic.cbc.business.exception;

import java.util.Objects;

public class InvalidUserException extends Exception {

  private String message;

  public InvalidUserException() {

  }

  public InvalidUserException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    if (!Objects.isNull(message)) {
      return message;
    }
    return super.getMessage();
  }
}
