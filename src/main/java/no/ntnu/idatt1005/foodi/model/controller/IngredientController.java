package no.ntnu.idatt1005.foodi.model.controller;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.Ingredient;

import java.sql.SQLException;

/**
 * This class is responsible for handling the usage of
 * database operations regarding stored ingredients in the frontend.
 *
 * @version 0.2.0
 * @author Snake727
 */

public class IngredientController {
  private IngredientDAO ingredientDAO;

  public IngredientController() {
    ingredientDAO = new IngredientDAO();
  }

  public void save(Ingredient ingredient) {
    try {
      ingredientDAO.save(ingredient);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void update(Ingredient ingredient) {
      ingredientDAO.update(ingredient);
  }

  public Ingredient retrieve(Ingredient ingredient) {
      return ingredientDAO.retrieve(ingredient);
  }

  public void delete(Ingredient ingredient) {
      ingredientDAO.delete(ingredient);
  }
}
