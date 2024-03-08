package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.control.TextField;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class InventoryListInput extends TextField implements CssUtils {
  public InventoryListInput() {
    super();
    addStylesheet("components/inventory/inventory-list-input");
    addClass("inventory-list-input");
  }
}
