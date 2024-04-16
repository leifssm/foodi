package no.ntnu.idatt1005.foodi.controller.pages;

import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.objects.dtos.GroupedExpiringIngredients;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.Inventory;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 */
public class InventoryController extends PageController {

  private final SimpleObjectProperty<User> currentUserProperty;
  private final Inventory view;

  /**
   * Constructor for the InventoryController class.
   *
   * @param inventoryPage the inventory view
   */
  public InventoryController(Inventory inventoryPage,
      SimpleObjectProperty<User> currentUserProperty) {
    super(inventoryPage);
    this.view = inventoryPage;

    this.currentUserProperty = currentUserProperty;

    update();
  }

  @Override
  void update() {
    // Update the inventory view
    System.out.println("Get data from backend with userId: " + currentUserProperty.get().userId()
        + " and update the inventory view.");
    System.out.println("Call the render() with the appropriate data for the inventory page.");

    // view.render(getInventoryData());
  }

  private List<GroupedExpiringIngredients> getInventoryData() {
    // Get the inventory data from the backend
    return null;
  }


}
