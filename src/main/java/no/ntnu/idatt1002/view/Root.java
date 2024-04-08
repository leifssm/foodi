package no.ntnu.idatt1002.view;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1002.view.components.sidebar.SideBar;
import no.ntnu.idatt1002.view.location.Router;
import no.ntnu.idatt1002.view.utils.LoadUtils;
import no.ntnu.idatt1002.view.views.CookbookGrid;
import no.ntnu.idatt1002.view.views.Inventory;
import no.ntnu.idatt1002.view.views.Profiles;

/**
 * Class that creates the main view of the application.
 */
public class Root extends BorderPane {
  /**
   * Constructor for the Root class.
   */
  public Root() {
    super();
    getStylesheets().add(LoadUtils.getStylesheet("root"));
    getStyleClass().add("main");
    setLeft(new SideBar());

    Router router = new Router();
    router.addRoute("inventory", new Inventory());
    router.addRoute("cookbook-grid", new CookbookGrid());
    router.addRoute("profiles", new Profiles());
    // add more routes here

    setCenter(router);
  }
}
