package no.ntnu.idatt1005.foodi.controller.pages;

import no.ntnu.idatt1005.foodi.view.views.Inventory;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 */
public class InventoryController extends PageController {

  /**
   * Constructor for the InventoryController class.
   *
   * @param inventoryView the inventory view
   */
  public InventoryController(Inventory inventoryView) {
    super(inventoryView);
  }

  @Override
  void update() {
    // Update the inventory view
    System.out.println("InventoryController: update");

  }
}
