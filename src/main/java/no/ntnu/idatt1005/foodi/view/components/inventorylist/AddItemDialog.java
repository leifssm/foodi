package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import java.util.Date;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.controller.ItemController;
import no.ntnu.idatt1005.foodi.model.objects.IngredientCategory;
import no.ntnu.idatt1005.foodi.model.objects.IngredientUnit;
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

  private final TextField ingredientField;
  private final TextField amountField;
  private final ComboBox<IngredientUnit> unitField;
  private final ComboBox<IngredientCategory> categoryField;
  private final DatePicker expirationDateField;

    private final InventoryList inventoryList;

  /**
   * Constructor for the AddItemDialog class.
   */
  public AddItemDialog(InventoryList inventoryList) {
    super();
    this.inventoryList = inventoryList;

    ingredientField = new TextField();
    amountField = new TextField();
    unitField = new ComboBox<>();
    categoryField = new ComboBox<>();
    expirationDateField = new DatePicker();

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

    unitField.getItems().addAll(IngredientUnit.values());

    categoryField.getItems().addAll(IngredientCategory.values());

    expirationDateField.setPromptText("dd/mm/yyyy");

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
    IngredientUnit unit = getIngredientUnit();
    IngredientCategory category = getIngredientCategory();
    Date expirationDate = getExpirationDate();

    String amountString = String.valueOf(amount);
    String unitString = unit.toString();

    ItemController itemController = new ItemController();
    itemController.saveItem(ingredient, category, unit, (int) amount, expirationDate);

    InventoryItem newItem = new InventoryItem(ingredient, expirationDate, category.toString(), amountString, unitString);
    inventoryList.addItemToInventory(newItem);

  }

  private @NotNull String getIngredient() throws ValidationException {
    String ingredient = ingredientField.getText();
    if (ingredient.isBlank()) {
      throw new ValidationException("Ingredient name cannot be empty.");
    }

    return ingredientField.getText().toLowerCase();
  }

  private @NotNull double getAmount() throws ValidationException {
    double amount = 0;
    try {
      amount = Double.parseDouble(amountField.getText());
    } catch (NumberFormatException e) {
      throw new ValidationException("Amount must be a number.");
    }

    return amount;
  }

  private @NotNull IngredientUnit getIngredientUnit() throws ValidationException {
    if (unitField.getValue() == null) {
      throw new ValidationException("Unit must be selected.");
    }

    return unitField.getValue();
  }

  private @NotNull IngredientCategory getIngredientCategory() throws ValidationException {
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
  private @Nullable Date getExpirationDate() {
    try {
      return java.sql.Date.valueOf(expirationDateField.getValue());
    } catch (Exception e) {
      return null;
    }
  }
}