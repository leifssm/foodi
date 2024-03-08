package no.ntnu.idatt1002.view.views;

import no.ntnu.idatt1002.view.components.TitledPage;
import no.ntnu.idatt1002.view.components.inventorylist.InventoryList;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class Inventory extends TitledPage implements CssUtils {
  public Inventory() {
    super("Inventory", "Manage your inventory");
    addStylesheet("components/inventory/inventory");
    addClass("inventory");

    setContent(new InventoryList());
  }
}