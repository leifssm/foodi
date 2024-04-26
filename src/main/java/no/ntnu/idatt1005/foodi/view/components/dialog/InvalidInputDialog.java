package no.ntnu.idatt1005.foodi.view.components.dialog;

/**
 * Class for creating a dialog for invalid input. Extends the StandardDialog class.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class InvalidInputDialog extends StandardDialog {

  /**
   * Constructor for the InvalidInputDialog class.
   */
  public InvalidInputDialog(String message) {
    super();
    setTitle("Invalid input");
    setContentText(message);
    addOkButton();
  }
}
