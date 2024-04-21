package no.ntnu.idatt1005.foodi.controller.pages;

import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.components.cookbook.RecipeCard;
import no.ntnu.idatt1005.foodi.view.components.cookbook.RecipeCardRow;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import no.ntnu.idatt1005.foodi.view.views.CookbookGrid;

import java.util.Arrays;
import java.util.List;

/**
 * Controller for the cookbook grid page. This controller manages the updates to the cookbook grid
 * page.
 */
public class CookbookGridController extends PageController {

  private final CookbookGrid view;
  private final RecipeDAO recipeDAO;


  /**
   * Constructor for the CookbookGridController class.
   *
   * @param cookbookGridPage the cookbook grid view
   */
  public CookbookGridController(CookbookGrid cookbookGridPage) {
    super(cookbookGridPage);
    this.view = cookbookGridPage;
    this.recipeDAO = new RecipeDAO();
    LocationHandler.subscribe(e -> {
      update();
    });
    update();
  }

  @Override
  void update() {
    Recipe[] recipes = fetchAllRecipes();

    RecipeCardRow twentyMinuteRecipeCardRow = createRecipeCardRow("Twenty minute recipes",
        filterRecipesByDuration(recipes, 20));

    RecipeCardRow easyRecipeCardRow = createRecipeCardRow("Easy recipes",
        filterRecipesByDifficulty(recipes, Recipe.Difficulty.EASY));

    RecipeCardRow vegetarianRecipeCardRow = createRecipeCardRow("Vegetarian",
        filterRecipesByDietaryTags(recipes, Recipe.DietaryTag.VEGETARIAN, Recipe.DietaryTag.VEGAN));

    RecipeCardRow popularRecipeCardRow = createRecipeCardRowForPopularRecipes(recipes);


    List<RecipeCardRow> cardRows = Arrays.asList(popularRecipeCardRow, twentyMinuteRecipeCardRow, easyRecipeCardRow, vegetarianRecipeCardRow);

    view.render(cardRows.toArray(new RecipeCardRow[0]));
  }

  private Recipe[] fetchAllRecipes() {
    return recipeDAO.retrieveAll().toArray(new Recipe[0]);
  }

  private RecipeCardRow createRecipeCardRow(String title, Recipe[] filteredRecipes) {
    RecipeCard[] cards = Arrays.stream(filteredRecipes)
        .map(recipe -> new RecipeCard(recipe.getName(), recipe.getDuration() + " minutes", recipe.getImagePath()))
        .toArray(RecipeCard[]::new);
    return new RecipeCardRow(title, cards);
  }

  private Recipe[] filterRecipesByDuration(Recipe[] recipes, int maxDuration) {
    return Arrays.stream(recipes)
        .filter(recipe -> recipe.getDuration() <= maxDuration)
        .toArray(Recipe[]::new);
  }

  private Recipe[] filterRecipesByDifficulty(Recipe[] recipes, Recipe.Difficulty difficulty) {
    return Arrays.stream(recipes)
        .filter(recipe -> recipe.getDifficulty() == difficulty)
        .toArray(Recipe[]::new);
  }

  private Recipe[] filterRecipesByDietaryTags(Recipe[] recipes, Recipe.DietaryTag... tags) {
    return Arrays.stream(recipes)
        .filter(recipe -> Arrays.asList(tags).contains(recipe.getDietaryTag()))
        .toArray(Recipe[]::new);
  }

  private RecipeCardRow createRecipeCardRowForPopularRecipes(Recipe[] recipes) {
    // Assuming 'popularRecipes' are predefined; here you may need a more dynamic approach
    RecipeCard[] cards = {
        new RecipeCard(recipes[5].getName(), recipes[5].getDuration() + " minutes", recipes[5].getImagePath()),
        new RecipeCard(recipes[2].getName(), recipes[2].getDuration() + " minutes", recipes[2].getImagePath()),
        new RecipeCard(recipes[4].getName(), recipes[4].getDuration() + " minutes", recipes[4].getImagePath()),
        new RecipeCard(recipes[1].getName(), recipes[1].getDuration() + " minutes", recipes[1].getImagePath())
    };
    return new RecipeCardRow("Popular", cards);
  }
}
