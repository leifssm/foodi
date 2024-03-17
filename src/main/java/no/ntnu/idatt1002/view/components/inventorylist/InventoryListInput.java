package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.control.TextField;
import no.ntnu.idatt1002.view.utils.ComponentUtils;

/**
 * A class for displaying the input field for the inventory list.
 *
 * @version 1.0
 * @author Leif Mørstad
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
}
