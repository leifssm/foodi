package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.Recipe;
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

public class RecipeDBOTest {
  private static RecipeDatabaseAccess recipeDatabaseAccess;
  private static Recipe testRecipe;
  private static Recipe testRecipe2;

  @BeforeAll
  public static void setUp() throws SQLException {
    // Create a new RecipeDatabaseAccess object
    recipeDatabaseAccess = new RecipeDatabaseAccess();

    // Initialize the database if not already initialized
    Database database = new Database();
    database.initializeDatabase();

    // Create new Recipe objects
    testRecipe = new Recipe(1, "Pasta", "Pasta with tomato sauce", Recipe.Difficulty.EASY, 30);
    testRecipe2 = new Recipe(2, "Pizza", "Pizza with tomato sauce and cheese", Recipe.Difficulty.EASY, 45);
  }

  @Test
  public void testSave() throws SQLException {
    recipeDatabaseAccess.save(testRecipe);
    Recipe savedRecipe = recipeDatabaseAccess.retrieve(testRecipe);
    assertEquals(testRecipe.toString(), savedRecipe.toString());
    recipeDatabaseAccess.delete(testRecipe);
  }

  @Test
  public void testRetrieve() {
    try {
      recipeDatabaseAccess.save(testRecipe2);
      Recipe savedRecipe = recipeDatabaseAccess.retrieve(testRecipe2);
      assertEquals(testRecipe2.toString(), savedRecipe.toString());
      recipeDatabaseAccess.delete(testRecipe2);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
