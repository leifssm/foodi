package no.ntnu.idatt1005.foodi.controller.pages;

import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.daos.RecipeDao;
import no.ntnu.idatt1005.foodi.model.daos.ShoppingListDao;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithIngredients;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import no.ntnu.idatt1005.foodi.view.views.RecipePage;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 */
public class RecipePageController extends PageController {

  private static final Logger LOGGER = Logger.getLogger(RecipePageController.class.getName());

  private final RecipePage view;
  private final RecipeDao recipeDao;
  private final ShoppingListDao shoppingListDAO;
  private final SimpleObjectProperty<User> currentUserProperty;
  private RecipeWithIngredients recipe;


  /**
   * Constructor for the InventoryController class.
   *
   * @param recipePage the recipe view
   */
  public RecipePageController(RecipePage recipePage,
      SimpleObjectProperty<User> currentUserProperty) {
    super(recipePage);

    this.view = recipePage;
    this.recipeDao = new RecipeDao();
    this.shoppingListDAO = new ShoppingListDao();
    this.currentUserProperty = currentUserProperty;

    LocationHandler.subscribe(e -> {
      if (LocationHandler.isLocationFuzzy("recipes/")) {
        update();
      }
    });
  }

  @Override
  void update() {
    String segment = LocationHandler.getLocationSegment(1);

    if (segment == null) {
      System.out.println("No segment found.");
      return;
    }

    int id = Integer.parseInt(segment);
    recipe = recipeDao.retrieveRecipeWithIngredientsById(id);

    if (recipe == null) {
      LocationHandler.setLocation("cookbook-grid");
      return;
    }

    view.render(recipe, (int portions) -> addRecipe(id, portions));
  }

  private void addRecipe(int id, int portions) {
    shoppingListDAO.addRecipe(currentUserProperty.get().userId(), id, portions);
    LOGGER.info("Added recipe " + id + " to shopping list.");
  }
}
