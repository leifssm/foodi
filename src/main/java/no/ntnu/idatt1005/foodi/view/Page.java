package no.ntnu.idatt1005.foodi.view;

import javafx.scene.Node;

/**
 * Interface for a page. The paginator can call the refresh method to update the page. The refresh
 * method runs a Runnable that is set by the controller using the setUpdate() method. This design
 * allows the controller to define how the page should be updated.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public interface Page {

  /**
   * Refreshes the page by running the update Runnable. Static pages does not need specify an
   * update.
   */
  default void refresh() {
    if (getUpdate() != null) {
      getUpdate().run();
    }
  }

  /**
   * Returns the Runnable that updates the page. This Runnable is intended to be set by the
   * controller.
   *
   * @return the Runnable that updates the page
   */
  Runnable getUpdate();

  /**
   * Sets the Runnable used to update the page. This method provides an interface for the controller
   * to define the update behavior.
   *
   * @param update the Runnable that updates the page
   */
  void setUpdate(Runnable update);

  /**
   * Returns the Node that represents the page.
   *
   * @return the Node that represents the page
   */
  Node getNode();
}
