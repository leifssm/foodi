package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Unit;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.DB_URL;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.USER;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.PASS;

import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.AfterEach;
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

    // Initialize a new IngredientDAO object
    ingredientDAO = new IngredientDAO();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection conn = DriverManager.getConnection(DatabaseMain.DB_URL, DatabaseMain.USER, DatabaseMain.PASS);
         Statement stmt = conn.createStatement()) {
      stmt.execute("DROP ALL OBJECTS DELETE FILES"); // This will delete all tables and files associated with the database
    }
  }

//  @Test
//  void testSaveIngredientObject() {
//    ingredientDAO.saveIngredientObject(testIngredient);
//    Ingredient retrievedIngredient = ingredientDAO.retrieveIngredient(testIngredient);
//    assertEquals(testIngredient.toString(), retrievedIngredient.toString());
//    ingredientDAO.deleteIngredient(testIngredient);
//  }

  @Test
  void testCountIngredientItems() throws SQLException {
    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
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

    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT, 100, Date.valueOf("2022-12-31"));
    assertEquals(1, ingredientDAO.countIngredientItems());
  }

  @Test
  void testRetrieveIngredientById() throws SQLException {
    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    Ingredient retrievedIngredient = ingredientDAO.retrieveIngredientById(1);
    assertEquals("Test Ingredient", retrievedIngredient.getName());
  }

  @Test
  void testUpdateIngredientInUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.updateIngredientInUserInventory(1, 1, 200, Date.valueOf("2022-12-31").toLocalDate());

    // Retrieve the ingredient using the retrieveExpiringIngredientsFromInventory method because it needs an amount and a date
    ExpiringIngredient retrievedIngredient = ingredientDAO.retrieveExpiringIngredientsFromInventory(1).get(0);
    assertEquals(200, retrievedIngredient.getAmount());
  }

  @Test
  void testUpdateIngredientExpirationDate() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.updateIngredientExpirationDate(1, 1, Date.valueOf("2023-12-31").toLocalDate());

    // Retrieve the ingredient using the retrieveExpiringIngredientsFromInventory method because it needs an amount and a date
    ExpiringIngredient retrievedIngredient = ingredientDAO.retrieveExpiringIngredientsFromInventory(1).get(0);
    assertEquals(Date.valueOf("2023-12-31").toLocalDate(), retrievedIngredient.getExpirationDate());
  }

  @Test
  void testDeleteIngredientFromUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.deleteIngredientFromUserInventory(1, 1);
    assertEquals(0, ingredientDAO.countIngredientItems());
  }
}