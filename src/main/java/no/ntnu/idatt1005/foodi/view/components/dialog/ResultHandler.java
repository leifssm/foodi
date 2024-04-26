package no.ntnu.idatt1005.foodi.view.components.dialog;

import no.ntnu.idatt1005.foodi.view.exceptions.ValidationException;

/**
 * Functional interface for handling the result of a dialog. Used to handle exceptions thrown by the
 * dialog.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
@FunctionalInterface
public interface ResultHandler {

  void run() throws ValidationException;
}
