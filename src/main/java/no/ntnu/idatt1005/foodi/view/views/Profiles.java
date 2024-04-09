package no.ntnu.idatt1005.foodi.view.views;

import no.ntnu.idatt1005.foodi.view.components.CenteredPage;
import no.ntnu.idatt1005.foodi.view.components.profiles.ProfileItems;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;

import java.util.List;

/**
 * Class for displaying the inventory page.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class Profiles extends CenteredPage implements CssUtils {

  /**
   * Constructor for the Profiles class.
   */
  public Profiles() {
    super();
    addStylesheet("components/profiles/profiles");
    addClass("profiles");

    List<String> profileNames = List.of("Leif", "Henrik", "Kevin", "Markus");
    ProfileItems profileItems = new ProfileItems(profileNames);

    setContent(profileItems);
  }
}