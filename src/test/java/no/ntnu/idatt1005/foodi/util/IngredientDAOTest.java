package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Unit;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.DB_URL;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.USER;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.PASS;

import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientDAOTest {
  private IngredientDAO ingredientDAO;
  private Ingredient testIngredient;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    DatabaseMain databaseMain = new DatabaseMain();
    databaseMain.initializeDatabaseMain();

    // Delete every element in the main database
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
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

  @Test
  void testCountIngredientItems() {
    ingredientDAO.saveIngredientObject(testIngredient);
    assertEquals(1, ingredientDAO.countIngredientItems());
  }

  @Test
  void testSaveIngredientToUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDAO.saveIngredientObject(testIngredient);
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT, 100, Date.valueOf("2022-12-31"));
    assertEquals(1, ingredientDAO.countIngredientItems());
  }
}