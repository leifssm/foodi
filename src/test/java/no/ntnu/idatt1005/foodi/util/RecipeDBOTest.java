package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class is responsible for testing the RecipeDatabaseAccess class.
 *
 * @version 0.1.0
 * @author Snake727
 */

public class RecipeDBOTest {
  private static IngredientDAO ingredientDAO;
  private static RecipeDAO recipeDAO;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    DatabaseMain databaseMain = new DatabaseMain();
    databaseMain.initializeDatabaseMain();

    // Initialize a new IngredientDAO object
    ingredientDAO = new IngredientDAO();
    recipeDAO = new RecipeDAO();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection conn = DriverManager.getConnection(DatabaseMain.DB_URL, DatabaseMain.USER, DatabaseMain.PASS);
         Statement stmt = conn.createStatement()) {
      stmt.execute("DROP ALL OBJECTS DELETE FILES"); // This will delete all tables and files associated with the database
    }
  }

  @Test
  void testSaveRecipe() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg", "This is a test instruction");

    // Compare the recipe with the one retrieved from the database
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");
  }

  @Test
  void testRetrieveById() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg", "This is a test instruction");

    // Compare the recipe with the one retrieved from the database
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");
  }

  @Test
  void testUpdateRecipeById() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg", "This is a test instruction");

    // Check that recipe has been saved
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");

    // Update the recipe with new unique information
    recipeDAO.updateRecipeById(1, "Updated Recipe", "This is an updated test recipe", "MEDIUM", "VEGETARIAN", 45, "updated.jpg", "This is an updated test instruction");

    // Check that the recipe has been updated
    assertEquals(recipeDAO.retrieveById(1).getName(), "Updated Recipe");
  }

  @Test
  void testDeleteRecipe() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg", "This is a test instruction");

    // Check that recipe has been saved
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");

    // Delete the recipe
    recipeDAO.delete(recipeDAO.retrieveById(1));

    // Check that the recipe has been deleted
    assertNull(recipeDAO.retrieveById(1));
  }
}
