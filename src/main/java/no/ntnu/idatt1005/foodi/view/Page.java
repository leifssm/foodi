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

  void update();

  /**
   * Sets the Runnable used to update the page. This method provides an interface for the controller
   * to define the update behavior.
   *
   * @param controllerUpdate the Runnable that updates the page
   */
  void setUpdate(Runnable controllerUpdate);

  /**
   * Returns the Node that represents the page.
   *
   * @return the Node that represents the page
   */
  Node getNode();
}
