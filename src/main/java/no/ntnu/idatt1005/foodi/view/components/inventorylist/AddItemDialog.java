package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Consumer;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.view.components.dialog.NamedInputField;
import no.ntnu.idatt1005.foodi.view.components.dialog.StandardDialog;
import no.ntnu.idatt1005.foodi.view.exceptions.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class for creating a dialog for adding a new item to the inventory list. Extends the
 * StandardDialog class.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class AddItemDialog extends StandardDialog {

  private static final String DATE_FORMAT = "dd.MM.yyyy";
  private final TextField ingredientField;
  private final TextField amountField;
  private final ComboBox<Ingredient.Unit> unitField;
  private final ComboBox<Ingredient.Category> categoryField;
  private final DateTimeFormatter dateFormatParser = DateTimeFormatter.ofPattern(DATE_FORMAT);
  private final TextField expirationDateField;
  private final Consumer<ExpiringIngredient> onItemAdded;

  /**
   * Constructor for the AddItemDialog class.
   */
  public AddItemDialog(Consumer<ExpiringIngredient> onItemAdded) {
    super();
    this.onItemAdded = onItemAdded;

    ingredientField = new TextField();
    amountField = new TextField();
    unitField = new ComboBox<>();
    categoryField = new ComboBox<>();
    expirationDateField = new TextField();

    setTitle("Add New Item");
    setHeaderTitle("Add New Item");

    setMainContent();

    addOkButton("Add Item");
    addCancelButton();

    setOkAction(this::okAction);

    showAndWait();
  }

  private void setMainContent() {
    ingredientField.setPromptText("Potato");

    amountField.setPromptText("5");

    unitField.getItems().addAll(Ingredient.Unit.values());

    categoryField.getItems().addAll(Ingredient.Category.values());

    expirationDateField.setPromptText(DATE_FORMAT);

    VBox inputFields = new VBox();
    inputFields.getChildren().addAll(
        new NamedInputField(ingredientField, "Ingredient name"),
        new HBox(8,
            new NamedInputField(amountField, "Amount"),
            new NamedInputField(unitField, "Unit")
        ),
        new NamedInputField(categoryField, "Category"),
        new NamedInputField(expirationDateField, "Expiration Date")
    );

    inputFields.setSpacing(12);

    getDialogPane().setContent(inputFields);
  }

  private void okAction() throws ValidationException {
    String ingredient = getIngredient();
    double amount = getAmount();
    Ingredient.Unit unit = getIngredientUnit();
    Ingredient.Category category = getIngredientCategory();
    LocalDate expirationDate = getExpirationDate();
    boolean isFrozen = getIsFrozen();

    onItemAdded.accept(
        new ExpiringIngredient(
            -1,
            ingredient,
            unit,
            category,
            amount,
            expirationDate,
            isFrozen
        )
    );
  }

  private @NotNull String getIngredient() throws ValidationException {
    String ingredient = ingredientField.getText();
    if (ingredient.isBlank()) {
      throw new ValidationException("Ingredient name cannot be empty.");
    }

    return ingredientField.getText().toLowerCase();
  }

  private double getAmount() throws ValidationException {
    double amount = 0;
    try {
      amount = Double.parseDouble(amountField.getText());
    } catch (NumberFormatException e) {
      throw new ValidationException("Amount must be a number.");
    }

    return amount;
  }

  private @NotNull Ingredient.Unit getIngredientUnit() throws ValidationException {
    if (unitField.getValue() == null) {
      throw new ValidationException("Unit must be selected.");
    }

    return unitField.getValue();
  }

  private @NotNull Ingredient.Category getIngredientCategory() throws ValidationException {
    if (categoryField.getValue() == null) {
      throw new ValidationException("Category must be selected.");
    }

    return categoryField.getValue();
  }

  /**
   * Method for getting the expiration date from the expiration date field. If the date is not in a
   * valid format, it returns null as in "no date".
   *
   * @return The expiration date as a Date object or null.
   */
  private @Nullable LocalDate getExpirationDate() throws ValidationException {
    if (expirationDateField.getText().isBlank()) {
      return null;
    }

    try {
      return LocalDate.parse(expirationDateField.getText(), dateFormatParser);
    } catch (DateTimeParseException e) {
      throw new ValidationException(
          "Expiration date must be in the format dd.MM.yyyy or blank for no expiration date.");
    }
  }

  private boolean getIsFrozen() {
    return false;
  }
}