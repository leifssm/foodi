package no.ntnu.idatt1005.foodi.controller.pages;

import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.view.components.cookbook.RecipeCard;
import no.ntnu.idatt1005.foodi.view.components.cookbook.RecipeCardRow;
import no.ntnu.idatt1005.foodi.view.views.CookbookGrid;

import java.util.Arrays;
import java.util.Random;

/**
 * Controller for the cookbook grid page. This controller manages the updates to the cookbook grid
 * page.
 */
public class CookbookGridController extends PageController {

  private final CookbookGrid view;
  private final RecipeDAO recipeDAO;

  public CookbookGridController(CookbookGrid cookbookGridPage) {
    super(cookbookGridPage);
    this.view = cookbookGridPage;
    this.recipeDAO = new RecipeDAO();
    update();
  }

  @Override
  void update() {
    // Update the cookbook grid view
    System.out.println("Get data from backend and update the cookbook grid view.");
    System.out.println("Call the render() with the appropriate data for the cookbook grid page.");

    Recipe[] recipes = recipeDAO.retrieveAll().toArray(new Recipe[0]);
    System.out.println("length: " + recipes.length);

    Recipe[] twentyMinuteRecipes = Arrays.stream(recipes).filter(recipe -> recipe.getDuration() <= 20).toArray(Recipe[]::new);
    RecipeCard[] twentyMinuteRecipeCards = new RecipeCard[twentyMinuteRecipes.length];
    for (int i = 0; i < twentyMinuteRecipes.length; i++) {
      twentyMinuteRecipeCards[i] = new RecipeCard(twentyMinuteRecipes[i].getName(), twentyMinuteRecipes[i].getDuration() + " minutes", twentyMinuteRecipes[i].getImagePath());
    }
    RecipeCardRow twentyMinuteRecipeCardRow = new RecipeCardRow("Twenty minute recipes", twentyMinuteRecipeCards);

    Recipe[] easyRecipes = Arrays.stream(recipes).filter(recipe -> recipe.getDifficulty() == Recipe.Difficulty.EASY).toArray(Recipe[]::new);
    RecipeCard[] easyRecipeCards = new RecipeCard[easyRecipes.length];
    for (int i = 0; i < easyRecipes.length; i++) {
      easyRecipeCards[i] = new RecipeCard(easyRecipes[i].getName(), easyRecipes[i].getDuration() + " minutes", easyRecipes[i].getImagePath());
    }
    RecipeCardRow easyRecipeCardRow = new RecipeCardRow("Easy recipes", easyRecipeCards);

    Recipe[] vegetarianRecipes = Arrays.stream(recipes).filter(recipe -> recipe.getDietaryTag() == Recipe.DietaryTag.VEGETARIAN || recipe.getDietaryTag() == Recipe.DietaryTag.VEGAN).toArray(Recipe[]::new);
    RecipeCard[] vegetarianRecipeCards = new RecipeCard[vegetarianRecipes.length];
    for (int i = 0; i < vegetarianRecipes.length; i++) {
      vegetarianRecipeCards[i] = new RecipeCard(vegetarianRecipes[i].getName(), vegetarianRecipes[i].getDuration() + " minutes", vegetarianRecipes[i].getImagePath());
    }
    RecipeCardRow vegetarianRecipeCardRow = new RecipeCardRow("Vegetarian", vegetarianRecipeCards);

    Recipe[] popularRecipes = new Recipe[4];
    RecipeCard[] popularRecipeCards = new RecipeCard[popularRecipes.length];
    popularRecipeCards[0] = new RecipeCard(recipes[5].getName(), recipes[5].getDuration() + " minutes", recipes[5].getImagePath());
    popularRecipeCards[1] = new RecipeCard(recipes[2].getName(), recipes[2].getDuration() + " minutes", recipes[2].getImagePath());
    popularRecipeCards[2] = new RecipeCard(recipes[4].getName(), recipes[4].getDuration() + " minutes", recipes[4].getImagePath());
    popularRecipeCards[3] = new RecipeCard(recipes[1].getName(), recipes[1].getDuration() + " minutes", recipes[1].getImagePath());
    RecipeCardRow popularRecipeCardRow = new RecipeCardRow("Popular", popularRecipeCards);

    view.render(popularRecipeCardRow, twentyMinuteRecipeCardRow, easyRecipeCardRow, vegetarianRecipeCardRow);
  }
}
