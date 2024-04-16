package no.ntnu.idatt1005.foodi.view.components;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.controller.pages.PageController;
import no.ntnu.idatt1005.foodi.view.location.Router;

/**
 * A stateful page that can be updated by the controller.
 *
 * <p> This class is a custom component for creating a stateful page that can be updated by
 * {@link PageController} controller and {@link Router}. </p>
 *
 * <p> The class extends {@link BorderPane}.</p>
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public abstract class StatefulPage extends BorderPane {

  private Runnable controllerUpdate;

  protected StatefulPage() {
    super();
  }

  protected void update() {
    if (controllerUpdate == null) {
      return;
    }

    controllerUpdate.run();
  }

  public void setUpdate(Runnable controllerUpdate) {
    this.controllerUpdate = controllerUpdate;
  }
}
