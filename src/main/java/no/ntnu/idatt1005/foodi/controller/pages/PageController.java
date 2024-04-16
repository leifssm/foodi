package no.ntnu.idatt1005.foodi.controller.pages;

import no.ntnu.idatt1005.foodi.view.components.StatefulPage;

/**
 * Interface for a page controller. This controller manages updates to the page it controls,
 * allowing updates to be set and triggered as needed.
 */
public abstract class PageController {

  private final StatefulPage page;

  /**
   * Constructor for the PageController class.
   *
   * @param page the page that the controller is managing
   */
  protected PageController(StatefulPage page) {
    this.page = page;

    setPageUpdate();
  }

  /**
   * Sets the update mechanism for the page. This Runnable is intended to define how the page should
   * update itself when refreshed.
   */
  private void setPageUpdate() {
    page.setUpdate(this::update);
  }

  /**
   * Updates the page. This method is intended to be overridden by subclasses to define how the page
   * should update itself. Pages often have a `render` method with specific parameters the
   * controller can call.
   */
  abstract void update();
}
