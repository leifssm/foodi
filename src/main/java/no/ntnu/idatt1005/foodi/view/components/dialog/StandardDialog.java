package no.ntnu.idatt1005.foodi.view.components.dialog;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import no.ntnu.idatt1005.foodi.view.exceptions.ValidationException;
import no.ntnu.idatt1005.foodi.view.utils.DialogComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Class for creating a dialog for adding a new user.
 *
 * @author Henrik Kvamme
 */
public class StandardDialog extends Dialog<Void> implements DialogComponentUtils {

  private ButtonType okButtonType;

  /**
   * Constructor for the NewUserDialog class.
   */
  public StandardDialog() {
    super();
    addStylesheet("components/std-dialog");
    addClass("std-dialog");
  }

  /**
   * Sets the title of the dialog.
   *
   * @param title The title of the dialog
   */
  public void setHeaderTitle(String title) {
    Label header = new Label(title);
    header.getStyleClass().add("header");
    getDialogPane().setHeader(header);
  }

  /**
   * Sets the action to be performed when the OK button is clicked. If the action throws a
   * {@link ValidationException}, an {@link InvalidInputDialog} will be shown.
   *
   * @param action The action to be performed
   */
  public void setOkAction(@NotNull final ResultHandler action) {
    getOkButton().addEventFilter(ActionEvent.ACTION, event -> {
      try {
        action.run();
      } catch (ValidationException e) {
        new InvalidInputDialog(e.getMessage()).showAndWait();
        event.consume();
      } catch (Exception e) {
        new InvalidInputDialog("Unexpected error: " + e.getMessage()).showAndWait();
        Logger.getLogger(getClass().getName()).severe(e.getMessage());
      }
    });
  }

  private Node getOkButton() {
    return getDialogPane().lookupButton(okButtonType);
  }

  public void addOkButton() {
    addOkButton("OK");
  }

  /**
   * Adds an OK button to the dialog.
   */
  public void addOkButton(String text) {
    okButtonType = new ButtonType(text, ButtonBar.ButtonData.OK_DONE);
    getDialogPane().getButtonTypes().add(okButtonType);
    getOkButton().getStyleClass().add("button-ok");
  }

  /**
   * Adds a cancel button to the dialog.
   */
  public void addCancelButton() {
    ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    getDialogPane().getButtonTypes().add(cancelButtonType);
    Node cancelButton = getDialogPane().lookupButton(cancelButtonType);
    cancelButton.getStyleClass().add("button-cancel");
  }
}
