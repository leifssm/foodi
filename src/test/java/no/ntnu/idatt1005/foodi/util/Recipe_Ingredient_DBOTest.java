package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.DAO.RecipeIngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.*;
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
  private static RecipeIngredient testRecipe_Ingredient;
  private static RecipeIngredient testRecipe_Ingredient2;
  private static Ingredient testIngredient;
  private static Ingredient testIngredient2;

  @BeforeAll
  public static void setUp() throws SQLException {

    String deleteInventorySql = "DELETE FROM inventory";
    String deleteShoppingListSql = "DELETE FROM shopping_list";
    String deleteUserSql = "DELETE FROM MAIN.PUBLIC.\"user\"";
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
    DatabaseMain databaseMain = new DatabaseMain();
    databaseMain.initializeDatabaseMain();


    // Create new Recipe objects
    testRecipe = new Recipe(1, "Pasta", "Pasta with tomato sauce", Recipe.Difficulty.EASY,Recipe.DietaryTag.NONE, 30);
    testRecipe2 = new Recipe(2, "Pizza", "Pizza with tomato sauce and cheese", Recipe.Difficulty.EASY,Recipe.DietaryTag.NONE, 45);

    // Create new Ingredient objects
    testIngredient = new Ingredient(1, "Pasta", Ingredient.IngredientUnit.GRAM, Ingredient.IngredientCategory.GRAIN);
    testIngredient2 = new Ingredient(2, "Tomato sauce", Ingredient.IngredientUnit.MILLILITER, Ingredient.IngredientCategory.SAUCE);

    // Create new Recipe_Ingredient objects
    testRecipe_Ingredient = new RecipeIngredient(testRecipe, testIngredient, 2.0);
    testRecipe_Ingredient2 = new RecipeIngredient(testRecipe2, testIngredient2, 3.0);
  }

  @Test
  /**
   * This is a test that tries to save a Recipe_Ingredient object to the database.
   * The reason we have Ingredient and Recipe objects here, is because in order to save a Recipe_Ingredient object to the database,
   * we need to have the Recipe and Ingredient objects already saved in the database with the same ID as the Recipe_Ingredient object.
   */
  void testSave() throws SQLException {
    // Save the Recipe and Ingredient objects to the database
    recipeDAO.save(testRecipe);
    ingredientDAO.save(testIngredient);

    // Save the RecipeIngredient object to the database
    recipe_ingredient_DAO.save(testRecipe_Ingredient);

    // Retrieve the saved RecipeIngredient object from the database
    RecipeIngredient savedRecipeIngredient = recipe_ingredient_DAO.retrieve(testRecipe_Ingredient);

    // Check if the saved RecipeIngredient object is the same as the original one
    assertEquals(testRecipe_Ingredient.toString(), savedRecipeIngredient.toString());

    // Delete the test data from the database
    recipe_ingredient_DAO.delete(testRecipe_Ingredient);
    recipeDAO.delete(testRecipe);
    ingredientDAO.delete(testIngredient);
  }

  @Test
  void testUpdate() throws SQLException {
    // Save the Recipe and Ingredient objects to the database
    recipeDAO.save(testRecipe);
    ingredientDAO.save(testIngredient);

    // Save the RecipeIngredient object to the database
    recipe_ingredient_DAO.save(testRecipe_Ingredient);

    // Update the RecipeIngredient object in the database
    testRecipe_Ingredient.setAmount(3.0);
    recipe_ingredient_DAO.update(testRecipe_Ingredient);

    // Retrieve the updated RecipeIngredient object from the database
    RecipeIngredient updatedRecipeIngredient = recipe_ingredient_DAO.retrieve(testRecipe_Ingredient);

    // Check if the updated RecipeIngredient object is the same as the original one
    assertEquals(testRecipe_Ingredient.toString(), updatedRecipeIngredient.toString());

    // Delete the test data from the database
    recipe_ingredient_DAO.delete(testRecipe_Ingredient);
    recipeDAO.delete(testRecipe);
    ingredientDAO.delete(testIngredient);
  }

  @Test
  void testDelete() throws SQLException {
    // Save the Recipe and Ingredient objects to the database
    recipeDAO.save(testRecipe);
    ingredientDAO.save(testIngredient);

    // Save the RecipeIngredient object to the database
    recipe_ingredient_DAO.save(testRecipe_Ingredient);

    // Delete the RecipeIngredient object from the database
    recipe_ingredient_DAO.delete(testRecipe_Ingredient);

    // Check if the RecipeIngredient object was deleted from the database
    assertNull(recipe_ingredient_DAO.retrieve(testRecipe_Ingredient));

    // Delete the test data from the database
    recipeDAO.delete(testRecipe);
    ingredientDAO.delete(testIngredient);
  }

  @Test
  void testRetrieve() throws SQLException {
    // Save the Recipe and Ingredient objects to the database
    recipeDAO.save(testRecipe);
    ingredientDAO.save(testIngredient);

    // Save the RecipeIngredient object to the database
    recipe_ingredient_DAO.save(testRecipe_Ingredient);

    // Retrieve the RecipeIngredient object from the database
    RecipeIngredient retrievedRecipeIngredient = recipe_ingredient_DAO.retrieve(testRecipe_Ingredient);

    // Check if the retrieved RecipeIngredient object is the same as the original one
    assertEquals(testRecipe_Ingredient.toString(), retrievedRecipeIngredient.toString());

    // Delete the test data from the database
    recipe_ingredient_DAO.delete(testRecipe_Ingredient);
    recipeDAO.delete(testRecipe);
    ingredientDAO.delete(testIngredient);
  }

}
