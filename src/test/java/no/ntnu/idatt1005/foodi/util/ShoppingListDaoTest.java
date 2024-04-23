package no.ntnu.idatt1005.foodi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.QueryBuilder;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.DAO.ShoppingListDAO;
import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.model.repository.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShoppingListDaoTest {

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
    Database.initializeEmpty();

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

    // Create new test recipes
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 2", "This is a test recipe 2", "EASY", "VEGAN", 30,
        "test.jpg", "This is a test instruction 2");
    recipeDAO.saveRecipe("Test Recipe 3", "This is a test recipe 3", "EASY", "VEGAN", 30,
        "test.jpg", "This is a test instruction 3");

    // Save the test ingredients to a recipe
    ingredientDAO.saveIngredientToRecipe(1, testIngredient1.getId(), 2.0);
    ingredientDAO.saveIngredientToRecipe(1, testIngredient2.getId(), 3.0);
    ingredientDAO.saveIngredientToRecipe(1, testIngredient3.getId(), 4.0);
    ingredientDAO.saveIngredientToRecipe(1, testIngredient4.getId(), 5.0);
    ingredientDAO.saveIngredientToRecipe(1, testIngredient5.getId(), 6.0);
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
  @DisplayName("Test addRecipe method")
  void testAddRecipe() {
    // Add the recipe to the shopping list
    shoppingListDAO.addRecipe(testUser.userId(), 1, 2);

    // Retrieve the saved recipe from the shopping list using only SQL
    Integer recipeId = new QueryBuilder("SELECT recipe_id FROM shopping_list WHERE user_id = ?")
        .addInt(testUser.userId())
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    // Check if the recipe is in the shopping list
    assertEquals(1, recipeId);
  }

  @Test
  @DisplayName("addRecipe should add the portions to the already existing"
      + "portions of recipe in the shopping list")
  void testAddExistingRecipe() {
    // Add the recipe to the shopping list
    shoppingListDAO.addRecipe(testUser.userId(), 1, 2);

    // Add the recipe to the shopping list again
    shoppingListDAO.addRecipe(testUser.userId(), 1, 3);

    // Retrieve the saved portions in recipe from the shopping list using only SQL
    Integer portions = new QueryBuilder(
        "SELECT portions FROM shopping_list WHERE user_id = ? AND recipe_id = ?")
        .addInt(testUser.userId())
        .addInt(1)
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    // Check if the portions are correct
    assertEquals(5, portions);
  }

  @Test
  @DisplayName("deleteRecipe should delete given recipe based on Id")
  void testDeleteRecipe() {
    // Add the recipe to the shopping list
    shoppingListDAO.addRecipe(testUser.userId(), 1, 2);

    // Delete the recipe from the shopping list
    shoppingListDAO.deleteRecipe(testUser.userId(), 1);

    // Retrieve the saved recipe from the shopping list using only SQL
    Integer recipeId = new QueryBuilder("SELECT recipe_id FROM shopping_list WHERE user_id = ?")
        .addInt(testUser.userId())
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    // Check if the recipe is in the shopping list
    assertNull(recipeId);
  }

  @Test
  @DisplayName("clearShoppingList should delete all shopping list entries for a user")
  void testClearShoppingList() {
    // Add several recipes to the shopping list
    shoppingListDAO.addRecipe(testUser.userId(), 1, 2);
    shoppingListDAO.addRecipe(testUser.userId(), 2, 3);
    shoppingListDAO.addRecipe(testUser.userId(), 3, 4);

    // Clear the shopping list
    shoppingListDAO.clearShoppingList(testUser.userId());

    // Retrieve the saved recipes from the shopping list using only SQL
    Integer recipeId = new QueryBuilder("SELECT recipe_id FROM shopping_list WHERE user_id = ?")
        .addInt(testUser.userId())
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    // Check if the recipe is in the shopping list
    assertNull(recipeId);
  }

  @Test
  @DisplayName("addShoppingListToInventory should add all the ingredients for"
      + "the recipes in the shopping list to the user inventory")
  void testAddShoppingListToInventory() throws SQLException {
    // Add the recipe to the shopping list
    shoppingListDAO.addRecipe(testUser.userId(), 1, 2);

    // Add the recipe to the shopping list
    shoppingListDAO.addRecipe(testUser.userId(), 2, 3);

    // Add the recipe to the shopping list
    shoppingListDAO.addRecipe(testUser.userId(), 3, 4);

    // Add the shopping list to the inventory
    shoppingListDAO.addShoppingListToInventory(testUser.userId());

    // Retrieve the saved ingredients from the inventory using only SQL
    Integer ingredientId = new QueryBuilder(
        "SELECT ingredient_id FROM inventory WHERE user_id = ?")
        .addInt(testUser.userId())
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    // Check if the ingredient is in the inventory
    assertEquals(1, ingredientId);
  }
}