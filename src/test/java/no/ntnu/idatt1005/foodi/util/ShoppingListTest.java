package no.ntnu.idatt1005.foodi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.DAO.ShoppingListDAO;
import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingListTest {

  private static ShoppingListDAO shoppingListDAO;
  private static UserDAO userDAO;
  private static IngredientDAO ingredientDAO;
  private static RecipeDAO recipeDAO;
  private static User testUser;
  private static Ingredient testIngredient1;
  private static Ingredient testIngredient2;
  private static Ingredient testIngredient3;
  private static Ingredient testIngredient4;
  private static Ingredient testIngredient5;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    DatabaseMain databaseMain = new DatabaseMain();
    databaseMain.initializeDatabaseMain();

    // Initialize new DAO objects
    ingredientDAO = new IngredientDAO();
    userDAO = new UserDAO();
    shoppingListDAO = new ShoppingListDAO();
    recipeDAO = new RecipeDAO();

    // Create a test user
    testUser = new User(1, "Test User");
    userDAO.saveUser(testUser.name());

    // Create new test ingredients
    testIngredient1 = new Ingredient(1, "Test Ingredient 1", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);
    testIngredient2 = new Ingredient(2, "Test Ingredient 2", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);
    testIngredient3 = new Ingredient(3, "Test Ingredient 3", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);
    testIngredient4 = new Ingredient(4, "Test Ingredient 4", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);
    testIngredient5 = new Ingredient(5, "Test Ingredient 5", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);

    // Save the test ingredients to the database
    ingredientDAO.saveIngredientObject(testIngredient1);
    ingredientDAO.saveIngredientObject(testIngredient2);
    ingredientDAO.saveIngredientObject(testIngredient3);
    ingredientDAO.saveIngredientObject(testIngredient4);
    ingredientDAO.saveIngredientObject(testIngredient5);

    // Create a new test recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Save the test ingredients to a recipe
    ingredientDAO.saveIngredientToRecipe(1, testIngredient1.getId(), 2.0);
    ingredientDAO.saveIngredientToRecipe(1, testIngredient2.getId(), 3.0);
    ingredientDAO.saveIngredientToRecipe(1, testIngredient3.getId(), 4.0);
    ingredientDAO.saveIngredientToRecipe(1, testIngredient4.getId(), 5.0);
    ingredientDAO.saveIngredientToRecipe(1, testIngredient5.getId(), 6.0);
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection conn = DriverManager.getConnection(DatabaseMain.DB_URL, DatabaseMain.USER,
        DatabaseMain.PASS);
        Statement stmt = conn.createStatement()) {
      stmt.execute(
          "DROP ALL OBJECTS DELETE FILES"); // This will delete all tables and files associated with the database
    }
  }

  // Test the save method in the ShoppingListDAO class
  // This is done by generating a new shopping list based on the test recipe
  // and saving it to the database. The method then retrieves the shopping list
  // from the database and compares it to the original shopping list.
  @Test
  @Order(1)
  public void testSaveShoppingList() throws SQLException {
    Map<Integer, Double> shoppingList = new HashMap<>();
    shoppingList.put(testIngredient1.getId(), 2.0);
    shoppingList.put(testIngredient2.getId(), 3.0);
    shoppingList.put(testIngredient3.getId(), 4.0);
    shoppingList.put(testIngredient4.getId(), 5.0);
    shoppingList.put(testIngredient5.getId(), 6.0);

    shoppingListDAO.save(shoppingList, testUser.userId(), 1);

    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(
        testUser.userId());

    assertEquals(shoppingList, retrievedShoppingList,
        "The saved shopping list should match the retrieved shopping list.");
  }

  @Test
  @Order(2)
  public void testDeleteShoppingList() {
    shoppingListDAO.deleteAllForUser(testUser.userId());

    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(
        testUser.userId());

    assertTrue(retrievedShoppingList.isEmpty(),
        "The shopping list should be empty after deletion.");
  }

  @Test
  @Order(3)
  public void testUpdateShoppingList() throws SQLException {
    Map<Integer, Double> shoppingList = new HashMap<>();
    shoppingList.put(testIngredient1.getId(), 2.0);
    shoppingList.put(testIngredient2.getId(), 3.0);

    shoppingListDAO.save(shoppingList, testUser.userId(), 1);

    Map<Integer, Double> updatedShoppingList = new HashMap<>();
    updatedShoppingList.put(testIngredient1.getId(), 4.0);
    updatedShoppingList.put(testIngredient2.getId(), 5.0);

    shoppingListDAO.save(updatedShoppingList, testUser.userId(), 1);

    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(
        testUser.userId());

    assertEquals(updatedShoppingList, retrievedShoppingList,
        "The updated shopping list should match the retrieved shopping list.");
  }

  @Test
  @Order(4)
  public void testGetShoppingList() throws SQLException {
    Map<Integer, Double> shoppingList = new HashMap<>();
    shoppingList.put(testIngredient1.getId(), 2.0);
    shoppingList.put(testIngredient2.getId(), 3.0);

    shoppingListDAO.save(shoppingList, testUser.userId(), 1);

    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(
        testUser.userId());

    assertEquals(shoppingList, retrievedShoppingList,
        "The saved shopping list should match the retrieved shopping list.");
  }

  @Test
  @Order(5)
  public void testGetEmptyShoppingList() {
    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(
        testUser.userId());

    assertTrue(retrievedShoppingList.isEmpty(),
        "The shopping list should be empty if no shopping list has been saved.");
  }

  @Test
  @Order(6)
  public void testGetShoppingListForNonExistingUser() {
    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(1);

    assertTrue(retrievedShoppingList.isEmpty(),
        "The shopping list should be empty if the user does not exist.");
  }

  @Test
  @Order(7)
  public void testSaveEmptyShoppingList() throws SQLException {
    Map<Integer, Double> shoppingList = new HashMap<>();

    shoppingListDAO.save(shoppingList, testUser.userId(), 1);

    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(
        testUser.userId());

    assertTrue(retrievedShoppingList.isEmpty(),
        "The shopping list should be empty if no ingredients are added.");
  }

  // This method should be tested by adding five recipes to the shopping list
  // Then assert that the amount of recipes in the shopping list is equal to five
  @Test
  @Order(8)
  @DisplayName("Test that the getRecipesInShoppingListForUser method"
      + "returns the correct amount of recipes in the shopping list.")
  public void testGetRecipesInShoppingListForUser() throws SQLException {
    Map<Integer, Double> shoppingList = new HashMap<>();
    shoppingList.put(testIngredient1.getId(), 2.0);
    shoppingList.put(testIngredient2.getId(), 3.0);
    shoppingList.put(testIngredient3.getId(), 4.0);
    shoppingList.put(testIngredient4.getId(), 5.0);
    shoppingList.put(testIngredient5.getId(), 6.0);

    shoppingListDAO.save(shoppingList, testUser.userId(), 1);

    assertEquals(5, shoppingListDAO.getRecipesInShoppingListForUser(testUser.userId()).size(),
        "The amount of recipes in the shopping list should be equal to the amount of ingredients in the shopping list.");
  }

  @Test
  @Order(9)
  @DisplayName("Test that the getAllIngredientsFromShoppingList method"
      + "returns the correct amount of ingredients based on the recipes"
      + "in the shopping list.")
  public void testGetAllIngredientsFromShoppingList() throws SQLException {
    Map<Integer, Double> shoppingList = new HashMap<>();
    shoppingList.put(testIngredient1.getId(), 2.0);
    shoppingList.put(testIngredient2.getId(), 3.0);
    shoppingList.put(testIngredient3.getId(), 4.0);
    shoppingList.put(testIngredient4.getId(), 5.0);
    shoppingList.put(testIngredient5.getId(), 6.0);

    shoppingListDAO.save(shoppingList, testUser.userId(), 1);

    assertEquals(25, shoppingListDAO.getAllIngredientsFromShoppingList(testUser.userId()).size(),
        "The amount of ingredients in the shopping list should be equal "
            + "to the amount of ingredients in the shopping list in total.");
  }
}