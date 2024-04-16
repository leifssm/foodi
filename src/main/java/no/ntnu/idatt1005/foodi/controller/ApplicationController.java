package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.controller.pages.InventoryController;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.Root;

/**
 * Controller for the application. This controller manages the creation of pages and their routing
 * with their respective controllers.
 */
public class ApplicationController {

  private final Root root;
  private User user;

  /**
   * Constructor for the ApplicationController class.
   *
   * @param root the root view of the application
   */
  public ApplicationController(Root root) {
    this.root = root;

    createControllers();
  }

  private void createControllers() {
    new InventoryController(root.getInventoryPage());
    //new CookbookGridController(root.getCookbookGridPage());
    //new ProfilesController(root.getProfilesPage());
  }
}
