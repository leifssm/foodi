package no.ntnu.idatt1005.foodi.view;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.view.components.sidebar.SideBar;
import no.ntnu.idatt1005.foodi.view.location.Router;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;
import no.ntnu.idatt1005.foodi.view.views.CookbookGrid;
import no.ntnu.idatt1005.foodi.view.views.Inventory;
import no.ntnu.idatt1005.foodi.view.views.Profiles;
import no.ntnu.idatt1005.foodi.view.views.ShoppingList;

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

    Router router = new Router()
        .addRoute("inventory", new Inventory())
        .addRoute("shopping-list", new ShoppingList())
        .addRoute("cookbook-grid", new CookbookGrid())
        .addRoute("profiles", new Profiles());
    // add more routes here

    setCenter(router);
  }
}
