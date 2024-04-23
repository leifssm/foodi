package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import java.util.function.DoubleConsumer;
import javafx.scene.control.TextField;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;

/**
 * A class for displaying the input field for the inventory list.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
class InventoryListInput extends TextField implements ComponentUtils {

  /**
   * Constructor for the InventoryListInput class.
   */
  public InventoryListInput() {
    super();
    addStylesheet("components/inventory/inventory-list-input");
    addClass("inventory-list-input");
  }

  public void setOnAmountChange(DoubleConsumer onAmountChange) {
    textProperty().addListener(
        (observable, oldValue, newValue) -> handleAmountChange(oldValue, newValue, onAmountChange));
  }

  private void handleAmountChange(String oldValue, String newValue,
      DoubleConsumer onAmountChange) {
    try {
      double newAmount = Double.parseDouble(newValue);
      if (newAmount < 0) {
        setText(oldValue);
      } else {
        onAmountChange.accept(newAmount);
      }
    } catch (NumberFormatException e) {
      setText(oldValue);
    }
  }
}
