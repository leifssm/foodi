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
      getRecipeFromUrl();
      update();
    });
  }

  @Override
  void update() {
    System.out.println("Call the render() with the appropriate data for the recipe page.");
    getRecipeFromUrl();
    view.render(recipeDAO.retrieveRecipeWithIngredientsById(1));
  }

  private void getRecipeFromUrl() {
    if (LocationHandler.isLocationFuzzy("recipe/")) {
      String segment = LocationHandler.getLocationSegment(1);
      if (segment == null) {
        return;
      }
      int id = Integer.parseInt(segment);
      recipe = recipeDAO.retrieveRecipeWithIngredientsById(id);
    }
  }
}
