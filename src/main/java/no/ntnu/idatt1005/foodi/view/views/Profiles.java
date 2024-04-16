package no.ntnu.idatt1005.foodi.view.views;

import java.util.List;
import javafx.scene.Node;
import no.ntnu.idatt1005.foodi.view.Page;
import no.ntnu.idatt1005.foodi.view.components.CenteredPage;
import no.ntnu.idatt1005.foodi.view.components.profiles.ProfileItems;

/**
 * Class for displaying the inventory page.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class Profiles extends CenteredPage implements Page {

  private Runnable update;

  /**
   * Constructor for the Profiles class.
   */
  public Profiles() {
    super();

    List<String> profileNames = List.of("leif", "henrik", "kevin", "markus");
    ProfileItems profileItems = new ProfileItems(profileNames);

    setContent(profileItems);
  }

  @Override
  public Runnable getUpdate() {
    return update;
  }

  @Override
  public void setUpdate(Runnable update) {
    this.update = update;
  }

  @Override
  public Node getNode() {
    return this;
  }
}