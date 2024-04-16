package no.ntnu.idatt1005.foodi.controller.pages;

import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.Profiles;

/**
 * Controller for the profiles page. This controller manages the updates to the profiles page.
 */
public class ProfilesController extends PageController {

  private final Profiles view;
  private final SimpleObjectProperty<User> currentUserProperty;

  /**
   * Constructor for the ProfilesController class.
   *
   * @param profilesPage        the profiles view
   * @param currentUserProperty the current user property
   */
  public ProfilesController(Profiles profilesPage, SimpleObjectProperty<User> currentUserProperty) {
    super(profilesPage);
    this.currentUserProperty = currentUserProperty;

    this.view = profilesPage;
  }

  void update() {
    // Update the profiles view
    System.out.println("Get data from backend and update the profiles view.");
    System.out.println("Call the render() with the appropriate data for the profiles page.");
    changeUser(new User(1, "DTO"));
  }

  void changeUser(User user) {
    currentUserProperty.set(user);
  }
}
