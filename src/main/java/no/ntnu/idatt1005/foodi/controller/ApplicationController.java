package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.controller.pages.CookbookGridController;
import no.ntnu.idatt1005.foodi.controller.pages.InventoryController;
import no.ntnu.idatt1005.foodi.controller.pages.ProfilesController;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.Root;
import org.jetbrains.annotations.NotNull;

/**
 * Controller for the application. This controller manages the creation of pages and their routing
 * with their respective controllers.
 */
public class ApplicationController {

  private final Root root;
  private User currentUser;

  /**
   * Constructor for the ApplicationController class.
   *
   * @param root the root view of the application
   */
  public ApplicationController(Root root) {
    this.root = root;

    setCurrentUser(new User(1, "REPLACE WITH DATA FROM DTO"));
    createControllers();
  }

  private void createControllers() {
    new InventoryController(root.getInventoryPage(), this::getCurrentUser);
    new CookbookGridController(root.getCookbookGridPage());
    new ProfilesController(root.getProfilesPage(), this::setCurrentUser);
  }

  public User getCurrentUser() {
    return currentUser;
  }

  public void setCurrentUser(@NotNull User user) {
    this.currentUser = user;
  }
}
