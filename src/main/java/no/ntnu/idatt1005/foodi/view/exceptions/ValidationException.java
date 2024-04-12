package no.ntnu.idatt1005.foodi.view.exceptions;

/**
 * Class for creating a validation exception. Used for throwing exceptions when validation fails.
 * Extends the Exception class.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class ValidationException extends Exception {

  public ValidationException(String message) {
    super(message);
  }
}
