package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.objects.Recipe;

/**
 * This class is responsible for handling the usage of database operations regarding stored recipes
 * in the frontend.
 *
 * @author Snake727
 * @version 0.2.0
 */
public class RecipeController {

  private final RecipeDAO recipeDAO;

  public RecipeController() {
    recipeDAO = new RecipeDAO();
  }

  public void save(Recipe recipe) {
    recipeDAO.saveRecipeObject(recipe);
  }

  public void update(Recipe recipe) {
    recipeDAO.update(recipe);
  }

  public Recipe retrieve(Recipe recipe) {
    return recipeDAO.retrieveRecipeObject(recipe);
  }

  public void delete(Recipe recipe) {
    recipeDAO.delete(recipe);
  }
}
