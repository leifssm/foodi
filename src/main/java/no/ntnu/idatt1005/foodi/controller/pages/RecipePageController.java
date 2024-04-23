package no.ntnu.idatt1005.foodi.controller.pages;

import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithIngredients;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import no.ntnu.idatt1005.foodi.view.views.RecipePage;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 */
public class RecipePageController extends PageController {

  private final RecipePage view;
  private RecipeWithIngredients recipe;
  private final RecipeDAO recipeDAO;

  /**
   * Constructor for the InventoryController class.
   *
   * @param recipePage          the recipe view
   */
  public RecipePageController(RecipePage recipePage) {
    super(recipePage);

    this.view = recipePage;
    this.recipeDAO = new RecipeDAO();

    LocationHandler.subscribe(e ->  {
      if (LocationHandler.isLocationFuzzy("recipes/")) {
        update();
      }
    });
  }

  @Override
  void update() {
    recipe = getRecipeFromUrl();
    System.out.println("Call the render() with the appropriate data for the recipe page.");
    if (recipe == null) {
      LocationHandler.setLocation("cookbook-grid");
      return;
    }
    view.render(recipe);
  }

  private RecipeWithIngredients getRecipeFromUrl() {
      System.out.println("Gets the recipe from the URL.");
      String segment = LocationHandler.getLocationSegment(1);
      if (segment == null) {
        System.out.println("No segment found.");
        return null;
      }
      int id = Integer.parseInt(segment);
      return recipeDAO.retrieveRecipeWithIngredientsById(id);
    }
}
