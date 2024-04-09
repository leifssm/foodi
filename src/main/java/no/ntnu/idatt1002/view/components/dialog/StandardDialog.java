package no.ntnu.idatt1002.view.components.dialog;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import no.ntnu.idatt1002.view.utils.DialogCssUtils;

/**
 * Class for creating a dialog for adding a new user.
 *
 * @author Henrik Kvamme
 */
public class StandardDialog extends Dialog<Void> implements DialogCssUtils {

  /**
   * Constructor for the NewUserDialog class.
   */
  public StandardDialog() {
    super();
    addStylesheet("components/std-dialog");
    addClass("std-dialog");
  }

  /**
   * Adds an OK button to the dialog.
   */
  public void addOkButton() {
    ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
    getDialogPane().getButtonTypes().add(okButtonType);
    Node okButton = getDialogPane().lookupButton(okButtonType);
    okButton.getStyleClass().add("button-ok");
    expandButton(okButton);
  }

  private void expandButton(Node button) {
    // Does not currently work :((
    HBox.setHgrow(button, Priority.ALWAYS);
    button.maxWidth(Double.MAX_VALUE);
  }

  /**
   * Adds a cancel button to the dialog.
   */
  public void addCancelButton() {
    ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    getDialogPane().getButtonTypes().add(cancelButtonType);
    Node cancelButton = getDialogPane().lookupButton(cancelButtonType);
    cancelButton.getStyleClass().add("button-cancel");
    expandButton(cancelButton);
  }
}
