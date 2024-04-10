package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.objects.Recipe;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
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

public class RecipeDBOTest {
  private static RecipeDAO recipeDAO;
  private static Recipe testRecipe;
  private static Recipe testRecipe2;

  @BeforeAll
  public static void setUp() throws SQLException {
    // Create a new RecipeDatabaseAccess object
    recipeDAO = new RecipeDAO();

    // Initialize the database if not already initialized
    DatabaseMain databaseMain = new DatabaseMain();
    databaseMain.initializeDatabaseMain();

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

    // Create new Recipe objects
    testRecipe = new Recipe(1, "Pasta", "Pasta with tomato sauce", Recipe.Difficulty.EASY, Recipe.DietaryTag.VEGETARIAN, 30);
    testRecipe2 = new Recipe(2, "Pizza", "Pizza with tomato sauce and cheese", Recipe.Difficulty.EASY, Recipe.DietaryTag.VEGETARIAN, 60);
  }

  @Test
  public void testSave() throws SQLException {
    recipeDAO.save(testRecipe);
    Recipe savedRecipe = recipeDAO.retrieve(testRecipe);
    assertEquals(testRecipe.toString(), savedRecipe.toString());
    recipeDAO.delete(testRecipe);
  }

  @Test
  public void testRetrieve() {
    try {
      recipeDAO.save(testRecipe2);
      Recipe savedRecipe = recipeDAO.retrieve(testRecipe2);
      assertEquals(testRecipe2.toString(), savedRecipe.toString());
      recipeDAO.delete(testRecipe2);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
