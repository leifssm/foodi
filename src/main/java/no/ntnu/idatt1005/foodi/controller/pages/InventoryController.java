package no.ntnu.idatt1005.foodi.controller.pages;

import java.util.function.Supplier;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.Inventory;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 */
public class InventoryController extends PageController {

  private final Supplier<User> getCurrentUser;

  /**
   * Constructor for the InventoryController class.
   *
   * @param inventoryPage the inventory view
   */
  public InventoryController(Inventory inventoryPage, Supplier<User> getCurrentUser) {
    super(inventoryPage);
    this.getCurrentUser = getCurrentUser;

    update();
  }

  @Override
  void update() {
    // Update the inventory view
    System.out.println("Get data from backend with userId: " + getCurrentUser.get().userId()
        + " and update the inventory view.");
    System.out.println("Call the render() with the appropriate data for the inventory page.");

    getPage().render();
  }


}
