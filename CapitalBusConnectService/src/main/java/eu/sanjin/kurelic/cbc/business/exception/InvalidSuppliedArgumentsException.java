package eu.sanjin.kurelic.cbc.business.exception;

import java.util.Objects;

public class InvalidSuppliedArgumentsException extends Exception {

  private String message;

  public InvalidSuppliedArgumentsException() {
  }

  public InvalidSuppliedArgumentsException(String message) {
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
