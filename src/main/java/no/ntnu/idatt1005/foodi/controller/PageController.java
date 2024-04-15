package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.view.Page;

public interface PageController {

  /**
   * Refreshes the page by running the update Runnable. The update runnable could call a render
   * method on the page.
   */
  default void setUpdate(Runnable update) {
    getPage().setUpdate(update);
  }

  Page getPage();
}
