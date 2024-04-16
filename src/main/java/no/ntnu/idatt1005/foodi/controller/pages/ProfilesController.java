package no.ntnu.idatt1005.foodi.controller.pages;

import java.util.function.Consumer;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.Profiles;

/**
 * Controller for the profiles page. This controller manages the updates to the profiles page.
 */
public class ProfilesController extends PageController {

  public ProfilesController(Profiles profilesPage, Consumer<User> setCurrentUser) {
    super(profilesPage);
  }

  void update() {
    // Update the profiles view
    System.out.println("Get data from backend and update the profiles view.");
    System.out.println("Call the render() with the appropriate data for the profiles page.");
  }

}
