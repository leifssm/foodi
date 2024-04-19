package no.ntnu.idatt1005.foodi.controller;

import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.controller.pages.CookbookGridController;
import no.ntnu.idatt1005.foodi.controller.pages.InventoryController;
import no.ntnu.idatt1005.foodi.controller.pages.ProfilesController;
import no.ntnu.idatt1005.foodi.controller.pages.RecipePageController;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.Root;
import no.ntnu.idatt1005.foodi.view.components.sidebar.SideBar;

/**
 * Controller for the application. This controller manages the creation of pages and their routing
 * with their respective controllers.
 */
public class ApplicationController {

  private final Root root;
  private final SimpleObjectProperty<User> currentUserProperty;

  /**
   * Constructor for the ApplicationController class.
   *
   * @param root the root view of the application
   */
  public ApplicationController(Root root) {
    this.root = root;
    this.currentUserProperty = new SimpleObjectProperty<>(
        new User(1, "Default"));

    root.setSidebar(new SideBar(this.currentUserProperty));

    createControllers();
  }

  private void createControllers() {
    new InventoryController(root.getInventoryPage(), currentUserProperty);
    new CookbookGridController(root.getCookbookGridPage());
    new ProfilesController(root.getProfilesPage(), currentUserProperty);
    new RecipePageController(root.getRecipePage());
  }
}
