package no.ntnu.idatt1002.view.views;

import javafx.scene.control.Button;
import no.ntnu.idatt1002.view.components.TitledPage;
import no.ntnu.idatt1002.view.components.inventorylist.InventoryList;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class Inventory extends TitledPage implements CssUtils {
  public Inventory() {
    super("Inventory", "Manage your inventory");
    addClass("inventory");
    addStylesheet("inventory");

    setContent(new InventoryList());
  }
}