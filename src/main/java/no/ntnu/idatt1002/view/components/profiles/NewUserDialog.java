package no.ntnu.idatt1002.view.components.profiles;

import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import no.ntnu.idatt1002.view.components.dialog.StandardDialog;
import no.ntnu.idatt1002.view.utils.DialogCssUtils;

/**
 * Class for creating a dialog for adding a new user.
 *
 * @author Henrik Kvamme
 */
public class NewUserDialog extends StandardDialog implements DialogCssUtils {

  private final TextField nameField;

  /**
   * Constructor for the NewUserDialog class.
   */
  public NewUserDialog() {
    super();
    addStylesheet("components/profiles/new-user-dialog");
    addClass("new-user-dialog");

    setTitle("Add new user");
    setHeaderText("Enter the name of the new user");

    addOkButton();
    addCancelButton();

    nameField = new TextField();
    nameField.setPromptText("Mr. Example Exampleman");

    getDialogPane().setContent(nameField);

    setHandleResult();

    javafx.application.Platform.runLater(nameField::requestFocus);
  }

  private void setHandleResult() {
    setResultConverter(buttonType -> {
      if (isOkButton(buttonType)) {
        String name = nameField.getText();

        if (!isNameValid(name)) {
          showInvalidNameDialog();
          return null;
        }

        addUser(name);
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
    System.out.println("Adding user with name: " + name);
  }
}
