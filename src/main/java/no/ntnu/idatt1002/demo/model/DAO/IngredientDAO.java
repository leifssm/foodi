package no.ntnu.idatt1002.demo.model.DAO;

import no.ntnu.idatt1002.demo.model.Ingredient;

import java.util.List;

/**
 * Class for handling the database operations of the ingredient entity
 *
 * @version 0.1.0
 * @author Snake727
 */

public class IngredientDAO {

  public Ingredient findById(int id) {
    // Get connection (maybe use pool?)
    // Do some SQL
    // Return som real data

    return new Ingredient(10, "Apple", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.FRUIT);
  }

  public void save(Ingredient obj) {
    // Get connection (maybe use pool?)
    // Do some SQL
  }
}
