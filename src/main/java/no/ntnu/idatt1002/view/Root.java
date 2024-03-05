package no.ntnu.idatt1002.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1002.view.components.sidebar.SideBar;
import no.ntnu.idatt1002.view.location.Router;

/**
 * Class that creates the main view of the application.
 */
public class Root extends BorderPane {
  /**
   * Constructor for the Root class.
   */
  public Root() {
    super();
    getStylesheets().add(Utils.getStylesheet("root"));
    getStyleClass().add("main");
    setLeft(new SideBar());

    Router router = new Router();
    router.addRoute("inventory", new Button("hello"));
    // add more routes here

    setCenter(router);
  }
}
