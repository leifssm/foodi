package no.ntnu.idatt1005.foodi.controller.pages;

import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.RecipePage;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 */
public class RecipePageController extends PageController {

  private final RecipePage view;
  private final SimpleObjectProperty<User> currentUserProperty;

  /**
   * Constructor for the InventoryController class.
   *
   * @param recipePage          the inventory view
   * @param currentUserProperty the current user property
   */
  public RecipePageController(RecipePage recipePage,
      SimpleObjectProperty<User> currentUserProperty) {
    super(recipePage);

    this.view = recipePage;
    this.currentUserProperty = currentUserProperty;

    update();
  }

  @Override
  void update() {
    // Update the inventory view
    System.out.println("Get data from backend with userId: " + currentUserProperty.get().userId()
        + " and update the inventory view.");
    System.out.println("Call the render() with the appropriate data for the recipe page.");

  }


}
