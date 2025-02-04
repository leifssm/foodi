package no.ntnu.idatt1005.foodi.view.components.profiles;

import java.util.function.Function;
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
  private final Function<String, Boolean> addUser;

  /**
   * Constructor for the NewUserDialog class.
   */
  public NewUserDialog(Function<String, Boolean> addUser) {
    super();
    this.addUser = addUser;

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
    if (Boolean.FALSE.equals(addUser.apply(getName()))) {
      throw new ValidationException("Max users reached");
    }
  }

  private String getName() throws ValidationException {
    if (nameField.getText().isBlank()) {
      throw new ValidationException("Name cannot be blank");
    }
    return nameField.getText().toLowerCase();
  }
}
