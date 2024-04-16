package no.ntnu.idatt1005.foodi.view;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.view.components.sidebar.SideBar;
import no.ntnu.idatt1005.foodi.view.location.Router;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;
import no.ntnu.idatt1005.foodi.view.views.CookbookGrid;
import no.ntnu.idatt1005.foodi.view.views.Inventory;
import no.ntnu.idatt1005.foodi.view.views.Profiles;

/**
 * Class that creates the main view of the application.
 */
public class Root extends BorderPane {

  private final Inventory inventoryPage;
  private final CookbookGrid cookbookGridPage;
  private final Profiles profilesPage;

  /**
   * Constructor for the Root class.
   */
  public Root(Router router) {
    super();
    inventoryPage = new Inventory();
    cookbookGridPage = new CookbookGrid();
    profilesPage = new Profiles();

    getStylesheets().add(LoadUtils.getStylesheet("root"));
    getStyleClass().add("main");
    setLeft(new SideBar());

    router.addRoute("inventory", inventoryPage);
    router.addRoute("cookbook-grid", cookbookGridPage);
    router.addRoute("profiles", profilesPage);
    // add more routes here

    setCenter(router);
  }

  public Inventory getInventoryPage() {
    return inventoryPage;
  }

  public CookbookGrid getCookbookGridPage() {
    return cookbookGridPage;
  }

  public Profiles getProfilesPage() {
    return profilesPage;
  }
}
