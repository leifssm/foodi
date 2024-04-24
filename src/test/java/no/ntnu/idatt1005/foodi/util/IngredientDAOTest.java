package no.ntnu.idatt1005.foodi.util;

import static no.ntnu.idatt1005.foodi.model.repository.Database.DB_URL;
import static no.ntnu.idatt1005.foodi.model.repository.Database.PASS;
import static no.ntnu.idatt1005.foodi.model.repository.Database.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category;
import no.ntnu.idatt1005.foodi.model.repository.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IngredientDAOTest {

  private IngredientDAO ingredientDAO;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    Database.initializeEmpty();

    // Initialize a new IngredientDAO object
    ingredientDAO = new IngredientDAO();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER,
        Database.PASS);
        Statement stmt = conn.createStatement()) {
      stmt.execute(
          "DROP ALL OBJECTS DELETE FILES"); // This will delete all tables and files associated with the database
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
  void testCountIngredientItemsWithNoIngredients() throws SQLException {
    assertEquals(0, ingredientDAO.countIngredientItems());
  }

  @Test
  void testCountIngredientItemsInUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    assertEquals(1, ingredientDAO.countIngredientItemsInUserInventory(1));
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
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
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
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.updateIngredientInUserInventory(1, 200,
        Date.valueOf("2022-12-31").toLocalDate());

    // Retrieve the ingredient using the retrieveExpiringIngredientsFromInventory method because it needs an amount and a date
    ExpiringIngredient retrievedIngredient = ingredientDAO.retrieveExpiringIngredientsFromInventory(
        1).get(0);
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
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.updateIngredientExpirationDate(1, 1, Date.valueOf("2023-12-31").toLocalDate());

    // Retrieve the ingredient using the retrieveExpiringIngredientsFromInventory method because it needs an amount and a date
    ExpiringIngredient retrievedIngredient = ingredientDAO.retrieveExpiringIngredientsFromInventory(
        1).get(0);
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
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    var ingredients = ingredientDAO.retrieveExpiringIngredientsFromInventory(1);
    assertNotNull(ingredients);
    ExpiringIngredient ingredient = ingredients.get(0);
    ingredientDAO.deleteIngredientFromUserInventory(1, ingredient.getInventoryId());
    assertEquals(0, ingredientDAO.countIngredientItemsInUserInventory(1));
  }

  @Test
  void testRetrieveAmountedIngredientsFromInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));

    assertEquals(5, ingredientDAO.retrieveAmountedIngredientsFromInventory(1).size());
  }

  // Create a recipe with id 1 and insert five different ingredients
  // Then retrieve the amounted ingredients from the recipe and check if the size is 5
  @Test
  void testRetrieveAmountedIngredientsFromRecipe() throws SQLException {
    // Create a recipe with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(
          "INSERT INTO PUBLIC.recipe (id, name, description, difficulty, dietary_tag, duration) VALUES (1, 'Test Recipe', 'Test Description', 'EASY', 'VEGAN', 30)");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    // Create five different ingredients
    ingredientDAO.saveIngredient("Test Ingredient 1", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredient("Test Ingredient 2", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredient("Test Ingredient 3", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredient("Test Ingredient 4", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredient("Test Ingredient 5", Ingredient.Unit.GRAM, Category.MEAT);

    // Insert the five different ingredients into the recipe
    ingredientDAO.saveIngredientToRecipe(1, 1, 100);
    ingredientDAO.saveIngredientToRecipe(1, 2, 100);
    ingredientDAO.saveIngredientToRecipe(1, 3, 100);
    ingredientDAO.saveIngredientToRecipe(1, 4, 100);
    ingredientDAO.saveIngredientToRecipe(1, 5, 100);

    assertEquals(5, ingredientDAO.retrieveAmountedIngredientsFromRecipe(1).size());
  }

  @Test
  void testToggleFreezeIngredient() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDAO.toggleFreezeIngredient(1, 1, true);

    ExpiringIngredient retrievedIngredient = Objects.requireNonNull(
        ingredientDAO.retrieveExpiringIngredientsFromInventory(
            1)).get(0);
    assertEquals(true, retrievedIngredient.getIsFrozen());
  }
}