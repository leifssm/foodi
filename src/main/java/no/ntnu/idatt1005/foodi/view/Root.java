package no.ntnu.idatt1005.foodi.view;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.view.components.sidebar.SideBar;
import no.ntnu.idatt1005.foodi.view.location.Router;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;
import no.ntnu.idatt1005.foodi.view.views.About;
import no.ntnu.idatt1005.foodi.view.views.CookbookGrid;
import no.ntnu.idatt1005.foodi.view.views.Inventory;
import no.ntnu.idatt1005.foodi.view.views.Profiles;
import no.ntnu.idatt1005.foodi.view.views.RecipePage;
import no.ntnu.idatt1005.foodi.view.views.ShoppingList;

/**
 * Class that creates the main view of the application.
 */
public class Root extends BorderPane {

  private final Inventory inventoryPage;
  private final ShoppingList shoppingListPage;
  private final CookbookGrid cookbookGridPage;
  private final Profiles profilesPage;
  private final RecipePage recipePage;
  private final About aboutPage;

  /**
   * Constructor for the Root class.
   */
  public Root() {
    super();
    inventoryPage = new Inventory();
    shoppingListPage = new ShoppingList();
    cookbookGridPage = new CookbookGrid();
    profilesPage = new Profiles();
    recipePage = new RecipePage();
    aboutPage = new About();

    getStylesheets().add(LoadUtils.getStylesheet("root"));
    getStyleClass().add("main");

    Router router = new Router()
        .addRoute("profiles", profilesPage)
        .addRoute("inventory", inventoryPage)
        .addRoute("shopping-list", shoppingListPage)
        .addRoute("cookbook-grid", cookbookGridPage)
        .addRoute("recipes", recipePage)
        .addRoute("about", aboutPage);
    // add more routes here

    setCenter(router);
  }

  public void setSidebar(SideBar sidebar) {
    setLeft(sidebar);
  }

  public Inventory getInventoryPage() {
    return inventoryPage;
  }

  public ShoppingList getShoppingListPage() {
    return shoppingListPage;
  }

  public About getAboutPage() {
    return aboutPage;
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
