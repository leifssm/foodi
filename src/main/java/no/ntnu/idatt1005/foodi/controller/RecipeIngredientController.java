package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.model.DAO.RecipeIngredientDAO;

import java.sql.SQLException;

/**
 * This class is responsible for handling the usage of
 * database operations regarding stored RecipeIngredients in the frontend.
 *
 * @version 0.2.0
 * @author Snake727
 */
public class RecipeIngredientController {
  private RecipeIngredientDAO recipeIngredientDAO;

  public RecipeIngredientController() {
    recipeIngredientDAO = new RecipeIngredientDAO();
  }

  public void save(RecipeIngredient recipeIngredient) {
    try {
      recipeIngredientDAO.save(recipeIngredient);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update(RecipeIngredient recipeIngredient) {
      recipeIngredientDAO.update(recipeIngredient);
  }

  public RecipeIngredient retrieve(RecipeIngredient recipeIngredient) {
      return recipeIngredientDAO.retrieve(recipeIngredient);
  }

  public void delete(RecipeIngredient recipeIngredient) {
      recipeIngredientDAO.delete(recipeIngredient);
  }
}
