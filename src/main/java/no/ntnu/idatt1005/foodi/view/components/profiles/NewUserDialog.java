package no.ntnu.idatt1005.foodi.view.components.profiles;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import no.ntnu.idatt1005.foodi.view.components.dialog.StandardDialog;
import no.ntnu.idatt1005.foodi.view.exceptions.ValidationException;

/**
 * Class for creating a dialog for adding a new user.
 *
 * @author Henrik Kvamme
 */
public class NewUserDialog extends StandardDialog {

  private final TextField nameField;

  /**
   * Constructor for the NewUserDialog class.
   */
  public NewUserDialog() {
    super();
    setTitle("Add new user");
    setHeaderTitle("Enter the name of the new user");

    nameField = new TextField();
    nameField.setPromptText("Mr. Example Exampleman");

    getDialogPane().setContent(nameField);

    addOkButton();
    addCancelButton();

    setOkAction(this::okAction);

    Platform.runLater(nameField::requestFocus);
  }

  private void okAction() throws ValidationException {
    addUser(getName());
  }

  private void addUser(String name) {
    // TODO: Replace with calling method on controller
    System.out.println("Adding user with name: " + name);
  }

  private String getName() throws ValidationException {
    if (nameField.getText().isBlank()) {
      throw new ValidationException("Name cannot be blank");
    }
    return nameField.getText().toLowerCase();
  }
}
