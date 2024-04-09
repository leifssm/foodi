package no.ntnu.idatt1005.foodi.view.components.profiles;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import no.ntnu.idatt1005.foodi.view.dialog.StandardDialog;

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
    setHeaderText("Enter the name of the new user");

    addOkButton();
    addCancelButton();

    nameField = new TextField();
    nameField.setPromptText("Mr. Example Exampleman");

    getDialogPane().setContent(nameField);

    setHandleResult();

    Platform.runLater(nameField::requestFocus);
  }

  private void setHandleResult() {
    setResultConverter(buttonType -> {
      if (isOkButton(buttonType)) {
        String name = nameField.getText();

        if (!isNameValid(name)) {
          showInvalidNameDialog();
          return null;
        }

        addUser(name.toLowerCase());
      }

      nameField.clear();
      return null;
    });
  }

  private boolean isOkButton(ButtonType buttonType1) {
    return buttonType1.getButtonData() == ButtonType.OK.getButtonData();
  }

  private boolean isNameValid(String name) {
    return !name.isBlank();
  }

  private void showInvalidNameDialog() {
    var dialog = new StandardDialog();
    dialog.setTitle("Invalid name");
    dialog.setContentText(
        "The name " + nameField.getText() + " is invalid. Please enter a valid name.");

    dialog.addOkButton();
    dialog.showAndWait();
  }

  private void addUser(String name) {
    // TODO: Replace with controller
    System.out.println("Adding user with name: " + name);
  }
}
