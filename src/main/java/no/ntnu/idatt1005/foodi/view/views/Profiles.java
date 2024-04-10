package no.ntnu.idatt1005.foodi.view.views;

import no.ntnu.idatt1005.foodi.view.components.CenteredPage;
import no.ntnu.idatt1005.foodi.view.components.profiles.ProfileItems;

import java.util.List;

/**
 * Class for displaying the inventory page.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class Profiles extends CenteredPage {

  /**
   * Constructor for the Profiles class.
   */
  public Profiles() {
    super();

    List<String> profileNames = List.of("leif", "henrik", "kevin", "markus");
    ProfileItems profileItems = new ProfileItems(profileNames);

    setContent(profileItems);
  }
}