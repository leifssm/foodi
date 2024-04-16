package no.ntnu.idatt1005.foodi.controller;

import javafx.scene.Node;
import no.ntnu.idatt1005.foodi.view.Root;
import no.ntnu.idatt1005.foodi.view.location.Router;

/**
 * Controller for the application. This controller manages the creation of pages and their routing
 * with their respective controllers.
 */
public class ApplicationController {

  private final Router router;

  public ApplicationController(Root root, Router router) {
    this.router = router;
  }

  private void createPage(String path, Node pageContent) {
    router.addRoute(path, pageContent);
  }
}
