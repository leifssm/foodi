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

  /**
   * Method for attaching the controller to the view.
   */
  private void attachToView() {
    view.setChangeUser(this::changeUser);
  }


  /**
   * Method for changing the current user.
   *
   * @param user the user to change to
   */
  private void changeUser(User user) {
    currentUserProperty.set(user);
  }

  /**
   * Method for adding a user using DAO.
   *
   * @param name the name of the user to add
   */
  private void addUser(String name) {
    // TODO: Add user to the backend using DAO, the DAO should return a User DTO object
    System.out.println("Adding user: " + name);
    User user = new User(2, name); // Replace with the user object from the DAO
    changeUser(user);

    // Update to rerender the view with the added user
    update();
  }

  @Override
  void update() {
    view.render(getAllUsers());
  }

  /**
   * Method for fetching all users using DAO.
   *
   * @return a list of all users
   */
  private List<User> getAllUsers() {
    // TODO: Fetch profile names from the backend
    return List.of(new User(0, "Henrik 0"), new User(1, "Henrik 1"));
  }
}
