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

  private Runnable controllerUpdate;

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
  public void update() {
    if (controllerUpdate == null) {
      return;
    }

    controllerUpdate.run();
  }

  @Override
  public void setUpdate(Runnable controllerUpdate) {
    this.controllerUpdate = controllerUpdate;
  }

  @Override
  public Node getNode() {
    return this;
  }
}