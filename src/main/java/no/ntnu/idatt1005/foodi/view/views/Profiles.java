package no.ntnu.idatt1005.foodi.view.views;

import java.util.List;
import java.util.function.Consumer;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.components.CenteredPage;
import no.ntnu.idatt1005.foodi.view.components.profiles.ProfileItems;

/**
 * Class for displaying the inventory page.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class Profiles extends CenteredPage {

  private Consumer<User> changeUser;

  /**
   * Constructor for the Profiles class.
   */
  public Profiles() {
    super();
  }

  /**
   * Method for rendering the profiles page.
   *
   * @param users list of users to display
   */
  public void render(List<User> users) {
    // Render the profiles view
    ProfileItems profileItems = new ProfileItems(users, changeUser);

    setContent(profileItems);
  }

  public void setChangeUser(Consumer<User> changeUser) {
    this.changeUser = changeUser;
  }
}