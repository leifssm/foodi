package no.ntnu.idatt1002.view.location;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class Router extends Pane {
  HashMap<String, Node> routes = new HashMap<>();

  public Router() {
    super();
    LocationHandler.subscribe(this::updateLocation);
  }

  public void addRoute(String path, Node node) throws IllegalArgumentException {
    if (routes.containsKey(path)) {
      throw new IllegalArgumentException("Route already exists");
    }
    routes.put(path, node);
  }

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
