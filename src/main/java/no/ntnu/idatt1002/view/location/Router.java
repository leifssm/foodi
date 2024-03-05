package no.ntnu.idatt1002.view.location;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * A router that displays different nodes depending on the current location.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class Router extends Pane {
  /**
   * A map of routes and their corresponding nodes.
   */
  HashMap<String, Node> routes = new HashMap<>();

  /**
   * Creates an empty router.
   */
  public Router() {
    super();
    LocationHandler.subscribe(this::updateLocation);
  }

  /**
   * Adds a route to the router, which is shown when the given route is active.
   *
   * @param path The path of the route
   * @param node The node to display when the route is active
   * @throws IllegalArgumentException If the route already exists
   */
  public void addRoute(String path, Node node) throws IllegalArgumentException {
    if (routes.containsKey(path)) {
      throw new IllegalArgumentException("Route already exists");
    }
    routes.put(path, node);
  }

  /**
   * Changes the shown node depending on the given location.
   *
   * @param location The new location
   */
  private void updateLocation(String location) {
    if (routes.containsKey(location)) {
      getChildren().setAll(routes.get(location));
      return;
    }
    for (String route : routes.keySet()) {
      if (LocationHandler.isLocationFuzzy(route)) {
        getChildren().setAll(routes.get(route));
        return;
      }
    }
    getChildren().clear();
  }
}
