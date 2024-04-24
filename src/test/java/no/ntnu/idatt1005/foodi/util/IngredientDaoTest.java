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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IngredientDaoTest {

  private IngredientDAO ingredientDao;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    Database.initializeEmpty();

    // Initialize a new IngredientDAO object
    ingredientDao = new IngredientDAO();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER,
        Database.PASS);
        Statement stmt = conn.createStatement()) {
      stmt.execute(
          // This will delete all tables and files associated with the database
          "DROP ALL OBJECTS DELETE FILES");
    }
  }

  @Test
  @DisplayName("countIngredientItems should return 1")
  void testCountIngredientItems() throws SQLException {
    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    assertEquals(1, ingredientDao.countIngredientItems());
  }

  @Test
  @DisplayName("countIngredientItems should return 0")
  void testCountIngredientItemsWithNoIngredients() throws SQLException {
    assertEquals(0, ingredientDao.countIngredientItems());
  }

  @Test
  @DisplayName("countIngredientItemsInUserInventory should return 1")
  void testCountIngredientItemsInUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    assertEquals(1, ingredientDao.countIngredientItemsInUserInventory(1));
  }

  @Test
  @DisplayName("saveIngredientToUserInventory should save an ingredient to the user's inventory")
  void testSaveIngredientToUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));

    // Assert that the items exists in the user's inventory.
    assertEquals(1, ingredientDao.countIngredientItems());

    // Assert that the item name is correct.
    assertEquals("Test Ingredient", ingredientDao.retrieveExpiringIngredientsFromInventory(1).get(0)
        .getName());
  }

  @Test
  @DisplayName("retrieveIngredientById should return the correct ingredient")
  void testRetrieveIngredientById() throws SQLException {
    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    Ingredient retrievedIngredient = ingredientDao.retrieveIngredientById(1);
    assertEquals("Test Ingredient", retrievedIngredient.getName());
  }

  @Test
  @DisplayName("updateIngredientInUserInventory should update the "
      + "amount of an ingredient in the user's inventory")
  void testUpdateIngredientInUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDao.updateIngredientInUserInventory(1, 200,
        Date.valueOf("2022-12-31").toLocalDate());

    // Retrieve the ingredient using the
    // retrieveExpiringIngredientsFromInventory method because it needs an amount and a date
    ExpiringIngredient retrievedIngredient = ingredientDao.retrieveExpiringIngredientsFromInventory(
        1).get(0);
    assertEquals(200, retrievedIngredient.getAmount());
  }

  @Test
  @DisplayName("updateItemAmountInUserInventory() should update the amount of an ingredient in the user's inventory")
  void testUpdateItemAmountInUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDao.updateItemAmountInUserInventory(1, 1, 200);

    // Retrieve the ingredient using the retrieveExpiringIngredientsFromInventory
    // method because it needs an amount and a date
    ExpiringIngredient retrievedIngredient = ingredientDao.retrieveExpiringIngredientsFromInventory(
        1).get(0);
    assertEquals(200, retrievedIngredient.getAmount());
  }

  @Test
  @DisplayName("updateIngredientExpirationDate should update the"
      + " expiration date of an ingredient in the user's inventory")
  void testUpdateIngredientExpirationDate() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDao.updateIngredientExpirationDate(1, 1, Date.valueOf("2023-12-31").toLocalDate());

    // Retrieve the ingredient using the retrieveExpiringIngredientsFromInventory
    // method because it needs an amount and a date
    ExpiringIngredient retrievedIngredient = ingredientDao.retrieveExpiringIngredientsFromInventory(
        1).get(0);
    assertEquals(Date.valueOf("2023-12-31").toLocalDate(), retrievedIngredient.getExpirationDate());
  }

  @Test
  @DisplayName("deleteIngredientFromUserInventory should "
      + "delete an ingredient from the user's inventory")
  void testDeleteIngredientFromUserInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    var ingredients = ingredientDao.retrieveExpiringIngredientsFromInventory(1);
    assertNotNull(ingredients);
    ExpiringIngredient ingredient = ingredients.get(0);
    ingredientDao.deleteIngredientFromUserInventory(1, ingredient.getInventoryId());
    assertEquals(0, ingredientDao.countIngredientItemsInUserInventory(1));
  }

  @Test
  @DisplayName("retrieveExpiringIngredientsFromInventory should return a list of"
      + "expiringIngredient objects")
  void testRetrieveAmountedIngredientsFromInventory() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));

    assertEquals(5, ingredientDao.retrieveAmountedIngredientsFromInventory(1).size());
  }

  @Test
  @DisplayName("retrieveAmountedIngredientsFromRecipe should return a list of"
      + "amountedIngredient objects")
  void testRetrieveAmountedIngredientsFromRecipe() throws SQLException {
    // Create a recipe with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(
          "INSERT INTO PUBLIC.recipe (id, name, description, difficulty, dietary_tag, duration) "
              + "VALUES (1, 'Test Recipe', 'Test Description', 'EASY', 'VEGAN', 30)");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    // Create five different ingredients
    ingredientDao.saveIngredient("Test Ingredient 1", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredient("Test Ingredient 2", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredient("Test Ingredient 3", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredient("Test Ingredient 4", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredient("Test Ingredient 5", Ingredient.Unit.GRAM, Category.MEAT);

    // Insert the five different ingredients into the recipe
    ingredientDao.saveIngredientToRecipe(1, 1, 100);
    ingredientDao.saveIngredientToRecipe(1, 2, 100);
    ingredientDao.saveIngredientToRecipe(1, 3, 100);
    ingredientDao.saveIngredientToRecipe(1, 4, 100);
    ingredientDao.saveIngredientToRecipe(1, 5, 100);

    assertEquals(5, ingredientDao.retrieveAmountedIngredientsFromRecipe(1).size());
  }

  @Test
  @DisplayName("toggleFreezeIngredient should toggle the freeze status of an ingredient")
  void testToggleFreezeIngredient() throws SQLException {
    // Create a user with id 1
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (1, 'Test User')");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    ingredientDao.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.MEAT);
    ingredientDao.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM,
        Category.MEAT, 100, Date.valueOf("2022-12-31"));
    ingredientDao.toggleFreezeIngredient(1, 1, true);

    ExpiringIngredient retrievedIngredient = Objects.requireNonNull(
        ingredientDao.retrieveExpiringIngredientsFromInventory(
            1)).get(0);
    assertEquals(true, retrievedIngredient.getIsFrozen());
  }
}