package no.ntnu.idatt1005.foodi.view.components.inventorylist;

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
}
