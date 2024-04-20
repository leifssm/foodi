package no.ntnu.idatt1005.foodi.controller.pages;

import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.Profiles;

/**
 * Controller for the profiles page. This controller manages the updates to the profiles page.
 */
public class ProfilesController extends PageController {

  private static final int MAX_USERS = 4;
  private final Profiles view;
  private final SimpleObjectProperty<User> currentUserProperty;
  private final UserDAO userDAO;

  /**
   * Constructor for the ProfilesController class.
   *
   * @param profilesPage        the profiles view
   * @param currentUserProperty the current user property
   */
  public ProfilesController(Profiles profilesPage, SimpleObjectProperty<User> currentUserProperty) {
    super(profilesPage);
    this.userDAO = new UserDAO();
    this.currentUserProperty = currentUserProperty;

    this.view = profilesPage;

    attachToView();
  }

  /**
   * Method for attaching the controller to the view.
   */
  private void attachToView() {
    view.setChangeUser(this::changeUser);
    view.setAddUser(this::addUser);
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
   * Method for adding a new user.
   *
   * @param name the name of the new user
   * @return true if the user was added, false if the max users has been reached
   */
  private Boolean addUser(String name) {
    if (userDAO.retrieveAllUsers().size() >= MAX_USERS) {
      return false;
    }

    userDAO.saveUser(name);
    update();

    return true;
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
    return userDAO.retrieveAllUsers();
  }
}
