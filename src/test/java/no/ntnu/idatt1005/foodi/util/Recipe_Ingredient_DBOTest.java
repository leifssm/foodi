package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.DAO.RecipeIngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.Recipe_Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.Recipe;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.objects.Ingredient;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.repository.Database;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static no.ntnu.idatt1005.foodi.model.repository.Database.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is responsible for testing the RecipeDatabaseAccess class.
 *
 * @version 0.1.0
 * @author Snake727
 */

public class Recipe_Ingredient_DBOTest {
  private static RecipeIngredientDAO recipe_ingredient_DAO;
  private static RecipeDAO recipeDAO;
  private static IngredientDAO ingredientDAO;
  private static Recipe testRecipe;
  private static Recipe testRecipe2;
  private static Recipe_Ingredient testRecipe_Ingredient;
  private static Recipe_Ingredient testRecipe_Ingredient2;
  private static Ingredient testIngredient;
  private static Ingredient testIngredient2;

  @BeforeAll
  public static void setUp() throws SQLException {

    String deleteInventorySql = "DELETE FROM inventory";
    String deleteShoppingListSql = "DELETE FROM shopping_list";
    String deleteUserSql = "DELETE FROM TEST.PUBLIC.\"user\"";
    String deleteRecipe_IngredientSql = "DELETE FROM recipe_ingredient";
    String deleteIngredientSql = "DELETE FROM ingredient";
    String deleteRecipeSql = "DELETE FROM recipe";
    ;


    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
      Statement statement = connection.createStatement();
      statement.executeUpdate(deleteInventorySql);
      statement.executeUpdate(deleteShoppingListSql);
      statement.executeUpdate(deleteUserSql);
      statement.executeUpdate(deleteRecipe_IngredientSql);
      statement.executeUpdate(deleteIngredientSql);
      statement.executeUpdate(deleteRecipeSql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }


    // Create the necessary database access objects
    recipe_ingredient_DAO = new RecipeIngredientDAO();
    recipeDAO = new RecipeDAO();
    ingredientDAO = new IngredientDAO();

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
    recipeDAO.save(testRecipe);
    ingredientDAO.save(testIngredient);

    // Save the Recipe and Ingredient objects to the database with IDs 2
    recipeDAO.save(testRecipe2);
    ingredientDAO.save(testIngredient2);

    // Test the first Recipe_Ingredient object
    recipe_ingredient_DAO.save(testRecipe_Ingredient);
    Recipe_Ingredient savedRecipe_Ingredient = recipe_ingredient_DAO.retrieve(testRecipe_Ingredient);
    assertEquals(testRecipe_Ingredient.toString(), savedRecipe_Ingredient.toString());

    // Test the second Recipe_Ingredient object
    recipe_ingredient_DAO.save(testRecipe_Ingredient2);
    Recipe_Ingredient savedRecipe_Ingredient2 = recipe_ingredient_DAO.retrieve(testRecipe_Ingredient2);
    assertEquals(testRecipe_Ingredient2.toString(), savedRecipe_Ingredient2.toString());

    // Delete the test data from the database
    recipe_ingredient_DAO.delete(testRecipe_Ingredient);
    recipe_ingredient_DAO.delete(testRecipe_Ingredient2);
    recipeDAO.delete(testRecipe);
    recipeDAO.delete(testRecipe2);
    ingredientDAO.delete(testIngredient);
    ingredientDAO.delete(testIngredient2);
  }
}
