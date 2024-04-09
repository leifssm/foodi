package no.ntnu.idatt1002.view.views;

import java.util.List;
import no.ntnu.idatt1002.view.components.CenteredPage;
import no.ntnu.idatt1002.view.components.profiles.ProfileItems;

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

    List<String> profileNames = List.of("Leif", "Henrik", "Kevin", "Markus");
    ProfileItems profileItems = new ProfileItems(profileNames);

    setContent(profileItems);
  }
}