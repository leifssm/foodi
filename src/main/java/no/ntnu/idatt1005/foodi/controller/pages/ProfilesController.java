package no.ntnu.idatt1005.foodi.controller.pages;

import java.util.List;
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

    attachToView();
  }

  private void attachToView() {
    view.setChangeUser(this::changeUser);
  }

  void changeUser(User user) {
    currentUserProperty.set(user);
  }

  void update() {
    // Update the profiles view
    System.out.println("Get data from backend and update the profiles view.");
    System.out.println("Call the render() with the appropriate data for the profiles page.");
    List<User> profileNames = List.of(new User(0, "Henrik 0"), new User(1, "Henrik 1"));
    view.render(profileNames);
  }
}
