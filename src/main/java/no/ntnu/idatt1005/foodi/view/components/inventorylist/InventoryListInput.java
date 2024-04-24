package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import java.util.function.DoubleConsumer;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;

/**
 * A class for displaying the input field for the inventory list.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
class InventoryListInput extends TextField implements ComponentUtils {

  private final String oldValue;

  /**
   * Constructor for the InventoryListInput class.
   */
  public InventoryListInput(double amount) {
    super(Double.toString(amount));
    addStylesheet("components/inventory/inventory-list-input");
    addClass("inventory-list-input");

    this.oldValue = getText();
  }

  /**
   * Method for setting the onAmountChange event.
   *
   * @param onAmountChange The event to set
   */
  public void setOnAmountChange(DoubleConsumer onAmountChange) {
    setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.ENTER) {
        handleAmountChange(onAmountChange);
      }
    });
  }

  private void handleAmountChange(DoubleConsumer onAmountChange) {
    try {
      double newAmount = Double.parseDouble(getText());

      if (newAmount < 0) {
        setText(oldValue);
        return;
      }

      onAmountChange.accept(newAmount);
    } catch (NumberFormatException e) {
      setText(oldValue);
    }
  }
}
