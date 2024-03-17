package no.ntnu.idatt1002.view.location;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;

/**
 * A router that displays different nodes depending on the current location.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class Router extends BorderPane {
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
  public Router addRoute(@NotNull String path, @NotNull Node node) throws IllegalArgumentException {
    if (routes.containsKey(path)) {
      throw new IllegalArgumentException("Route already exists");
    }
    routes.put(path, node);

    if (LocationHandler.isLocationFuzzy(path)) {
      setCenter(node);
    }

    return this;
  }

  /**
   * Changes the shown node depending on the given location.
   *
   * @param location The new location
   */
  private void updateLocation(@NotNull String location) {
    if (routes.containsKey(location)) {
      setCenter(routes.get(location));
      return;
    }
    for (String route : routes.keySet()) {
      if (LocationHandler.isLocationFuzzy(route)) {
        setCenter(routes.get(route));
        return;
      }
    }
    setCenter(null);
  }
}
