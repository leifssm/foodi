package no.ntnu.idatt1005.foodi.view;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.view.components.sidebar.SideBar;
import no.ntnu.idatt1005.foodi.view.location.Router;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;
import no.ntnu.idatt1005.foodi.view.views.CookbookGrid;
import no.ntnu.idatt1005.foodi.view.views.Inventory;
import no.ntnu.idatt1005.foodi.view.views.Profiles;
import no.ntnu.idatt1005.foodi.view.views.RecipePage;

/**
 * Class that creates the main view of the application.
 */
public class Root extends BorderPane {

  private final Inventory inventoryPage;
  private final CookbookGrid cookbookGridPage;
  private final Profiles profilesPage;
  private final RecipePage recipePage;

  /**
   * Constructor for the Root class.
   */
  public Root() {
    super();
    inventoryPage = new Inventory();
    cookbookGridPage = new CookbookGrid();
    profilesPage = new Profiles();
    recipePage = new RecipePage();

    getStylesheets().add(LoadUtils.getStylesheet("root"));
    getStyleClass().add("main");
    setLeft(new SideBar());

    Router router = new Router();
    router.addRoute("inventory", inventoryPage);
    router.addRoute("cookbook-grid", cookbookGridPage);
    router.addRoute("profiles", profilesPage);
    router.addRoute("recipe-page", recipePage);
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

  public RecipePage getRecipePage() {
    return recipePage;
  }
}
