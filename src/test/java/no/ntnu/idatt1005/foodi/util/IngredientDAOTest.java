package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Unit;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category;
import no.ntnu.idatt1005.foodi.model.repository.Test.DatabaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientDAOTest {
  private IngredientDAO ingredientDAO;
  private Ingredient testIngredient;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the database
    DatabaseTest databaseTest = new DatabaseTest();
    databaseTest.initializeDatabase();

    // Delete every element in the database
    try (Connection connection = DriverManager.getConnection(DatabaseTest.DB_URL, DatabaseTest.USER, DatabaseTest.PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("DELETE FROM inventory");
      statement.executeUpdate("DELETE FROM shopping_list");
      statement.executeUpdate("DELETE FROM PUBLIC.\"user\"");
      statement.executeUpdate("DELETE FROM recipe_ingredient");
      statement.executeUpdate("DELETE FROM ingredient");
      statement.executeUpdate("DELETE FROM recipe");
    }

    // Initialize a new IngredientDAO object and an Ingredient object
    ingredientDAO = new IngredientDAO();
    testIngredient = new Ingredient(1, "Test Ingredient", Unit.GRAM, Category.MEAT);
  }

  @Test
  void testSaveIngredientObject() {
    ingredientDAO.saveIngredientObject(testIngredient);
    Ingredient retrievedIngredient = ingredientDAO.retrieveIngredient(testIngredient);
    assertEquals(testIngredient.toString(), retrievedIngredient.toString());
  }
}