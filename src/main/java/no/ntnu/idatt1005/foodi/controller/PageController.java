package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.view.Page;

/**
 * Interface for a page controller. This controller manages updates to the page it controls,
 * allowing updates to be set and triggered as needed.
 */
public interface PageController {

  /**
   * Sets the update mechanism for the page. This Runnable is intended to define how the page should
   * update itself when refreshed.
   *
   * @param update the Runnable that defines the update process for the page.
   */
  default void setUpdate(Runnable update) {
    getPage().setUpdate(update);
  }

  /**
   * Returns the page that the controller is managing.
   *
   * @return the page that the controller is managing
   */
  Page getPage();

  /**
   * Triggers the update process for the page, effectively refreshing the page.
   */
  default void refreshPage() {
    getPage().refresh();
  }
}
