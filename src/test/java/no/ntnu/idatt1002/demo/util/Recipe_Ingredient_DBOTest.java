package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.DAO.Recipe_Ingredient_DBAccess;
import no.ntnu.idatt1002.demo.model.Recipe_Ingredient;
import no.ntnu.idatt1002.demo.model.Recipe;
import no.ntnu.idatt1002.demo.model.DAO.RecipeDatabaseAccess;
import no.ntnu.idatt1002.demo.model.Ingredient;
import no.ntnu.idatt1002.demo.model.DAO.IngredientDatabaseAccess;
import no.ntnu.idatt1002.demo.model.repository.Database;
import no.ntnu.idatt1002.demo.model.DAO.RecipeDatabaseAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is responsible for testing the RecipeDatabaseAccess class.
 *
 * @version 0.1.0
 * @author Snake727
 */

public class Recipe_Ingredient_DBOTest {
  private static Recipe_Ingredient_DBAccess recipe_ingredient_dbAccess;
  private static RecipeDatabaseAccess recipeDatabaseAccess;
  private static IngredientDatabaseAccess ingredientDatabaseAccess;
  private static Recipe testRecipe;
  private static Recipe testRecipe2;
  private static Recipe_Ingredient testRecipe_Ingredient;
  private static Recipe_Ingredient testRecipe_Ingredient2;
  private static Ingredient testIngredient;
  private static Ingredient testIngredient2;

  @BeforeAll
  public static void setUp() throws SQLException {
    // Create the necessary database access objects
    recipe_ingredient_dbAccess = new Recipe_Ingredient_DBAccess();
    recipeDatabaseAccess = new RecipeDatabaseAccess();
    ingredientDatabaseAccess = new IngredientDatabaseAccess();

    // Initialize the database if not already initialized
    Database database = new Database();
    database.initializeDatabase();

    // Create new Recipe objects
    testRecipe = new Recipe(1, "Pasta", "Pasta with tomato sauce", Recipe.Difficulty.EASY, 30);
    testRecipe2 = new Recipe(2, "Pizza", "Pizza with tomato sauce and cheese", Recipe.Difficulty.EASY, 45);

    // Create new Recipe_Ingredient objects
    testRecipe_Ingredient = new Recipe_Ingredient(1, 1, 2.0);
    testRecipe_Ingredient2 = new Recipe_Ingredient(2, 2, 3.0);

    // Create new Ingredient objects
    testIngredient = new Ingredient(1, "Pasta", Ingredient.IngredientUnit.GRAM, Ingredient.IngredientCategory.GRAIN);
    testIngredient2 = new Ingredient(2, "Tomato sauce", Ingredient.IngredientUnit.MILLILITER, Ingredient.IngredientCategory.SAUCE);
  }

  @Test
  /**
   * This is a test that tries to save a Recipe_Ingredient object to the database.
   * The reason we have Ingredient and Recipe objects here, is because in order to save a Recipe_Ingredient object to the database,
   * we need to have the Recipe and Ingredient objects already saved in the database with the same ID as the Recipe_Ingredient object.
   */
  public void testSave() throws SQLException {
    // Save the Recipe and Ingredient objects to the database with IDs 1
    recipeDatabaseAccess.save(testRecipe);
    ingredientDatabaseAccess.save(testIngredient);

    // Save the Recipe and Ingredient objects to the database with IDs 2
    recipeDatabaseAccess.save(testRecipe2);
    ingredientDatabaseAccess.save(testIngredient2);

    // Test the first Recipe_Ingredient object
    recipe_ingredient_dbAccess.save(testRecipe_Ingredient);
    Recipe_Ingredient savedRecipe_Ingredient = recipe_ingredient_dbAccess.retrieve(testRecipe_Ingredient);
    assertEquals(testRecipe_Ingredient.toString(), savedRecipe_Ingredient.toString());

    // Test the second Recipe_Ingredient object
    recipe_ingredient_dbAccess.save(testRecipe_Ingredient2);
    Recipe_Ingredient savedRecipe_Ingredient2 = recipe_ingredient_dbAccess.retrieve(testRecipe_Ingredient2);
    assertEquals(testRecipe_Ingredient2.toString(), savedRecipe_Ingredient2.toString());

    // Delete the test data from the database
    recipe_ingredient_dbAccess.delete(testRecipe_Ingredient);
    recipe_ingredient_dbAccess.delete(testRecipe_Ingredient2);
    recipeDatabaseAccess.delete(testRecipe);
    recipeDatabaseAccess.delete(testRecipe2);
    ingredientDatabaseAccess.delete(testIngredient);
    ingredientDatabaseAccess.delete(testIngredient2);
  }
}
