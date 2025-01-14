package no.ntnu.idatt1005.foodi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import no.ntnu.idatt1005.foodi.model.daos.IngredientDao;
import no.ntnu.idatt1005.foodi.model.daos.QueryBuilder;
import no.ntnu.idatt1005.foodi.model.daos.RecipeDao;
import no.ntnu.idatt1005.foodi.model.daos.ShoppingListDao;
import no.ntnu.idatt1005.foodi.model.daos.UserDao;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithPartiallyRemovedIngredients;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.model.repository.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShoppingListDaoTest {

  private static ShoppingListDao shoppingListDao;
  private static UserDao userDao;
  private static IngredientDao ingredientDao;
  private static RecipeDao recipeDao;
  private static User testUser;

  @BeforeEach
  public void setUp() throws SQLException {
    // Delete everything in the database
    try (Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER,
        Database.PASS);
        Statement stmt = conn.createStatement()) {
      stmt.execute(
          "DROP ALL OBJECTS DELETE FILES");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Initialize the main database
    Database.initializeEmpty();

    // Initialize new daos objects
    ingredientDao = new IngredientDao();
    userDao = new UserDao();
    shoppingListDao = new ShoppingListDao();
    recipeDao = new RecipeDao();

    // Create a test user
    testUser = new User(1, "Test User");
    userDao.saveUser(testUser.name());

    // Save new test ingredients to the database
    ingredientDao.saveIngredient("Test Ingredient 1", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);
    ingredientDao.saveIngredient("Test Ingredient 2", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);
    ingredientDao.saveIngredient("Test Ingredient 3", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);
    ingredientDao.saveIngredient("Test Ingredient 4", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);
    ingredientDao.saveIngredient("Test Ingredient 5", Ingredient.Unit.GRAM,
        Ingredient.Category.VEGETABLE);

    // Create new test recipes
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30,
        "test.jpg", "This is a test instruction");
    recipeDao.saveRecipe("Test Recipe 2", "This is a test recipe 2", "EASY", "VEGAN", 30,
        "test.jpg", "This is a test instruction 2");
    recipeDao.saveRecipe("Test Recipe 3", "This is a test recipe 3", "EASY", "VEGAN", 30,
        "test.jpg", "This is a test instruction 3");

    // Save the test ingredients to recipes
    ingredientDao.saveIngredientToRecipe(1, 1, 2.0);
    ingredientDao.saveIngredientToRecipe(1, 2, 3.0);
    ingredientDao.saveIngredientToRecipe(1, 3, 4.0);
    ingredientDao.saveIngredientToRecipe(1, 4, 5.0);
    ingredientDao.saveIngredientToRecipe(1, 5, 6.0);

    ingredientDao.saveIngredientToRecipe(2, 1, 2.0);
    ingredientDao.saveIngredientToRecipe(2, 2, 3.0);
    ingredientDao.saveIngredientToRecipe(2, 3, 4.0);

    ingredientDao.saveIngredientToRecipe(3, 1, 2.0);
    ingredientDao.saveIngredientToRecipe(3, 2, 3.0);
    ingredientDao.saveIngredientToRecipe(3, 3, 4.0);
    ingredientDao.saveIngredientToRecipe(3, 4, 5.0);
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
    shoppingListDao.addRecipe(testUser.userId(), 1, 2);

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
    shoppingListDao.addRecipe(testUser.userId(), 1, 2);

    // Add the recipe to the shopping list again
    shoppingListDao.addRecipe(testUser.userId(), 1, 3);

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
    shoppingListDao.addRecipe(testUser.userId(), 1, 2);

    // Delete the recipe from the shopping list
    shoppingListDao.deleteRecipe(testUser.userId(), 1);

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
    shoppingListDao.addRecipe(testUser.userId(), 1, 2);
    shoppingListDao.addRecipe(testUser.userId(), 2, 3);
    shoppingListDao.addRecipe(testUser.userId(), 3, 4);

    // Clear the shopping list
    shoppingListDao.clearShoppingList(testUser.userId());

    // Retrieve the saved recipes from the shopping list using only SQL
    Integer recipeId = new QueryBuilder("SELECT recipe_id FROM shopping_list WHERE user_id = ?")
        .addInt(testUser.userId())
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    // Check if the recipe is in the shopping list
    assertNull(recipeId);
  }

  @Test
  @DisplayName("addShoppingListToInventory should add all the ingredients for "
      + "the recipes in the shopping list to the user inventory")
  void testAddShoppingListToInventory() {
    // Add the recipe to the shopping list
    shoppingListDao.addRecipe(testUser.userId(), 1, 2);

    // Add the recipe to the shopping list
    shoppingListDao.addRecipe(testUser.userId(), 2, 3);

    // Add the recipe to the shopping list
    shoppingListDao.addRecipe(testUser.userId(), 3, 4);

    // Add the shopping list to the inventory
    shoppingListDao.addShoppingListToInventory(testUser.userId());

    // Retrieve the saved ingredients from the inventory using only SQL
    Integer ingredientId = new QueryBuilder(
        "SELECT ingredient_id FROM inventory WHERE user_id = ? AND ingredient_id = ?")
        .addInt(testUser.userId())
        .addInt(1)
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    // Check if the ingredient is in the inventory
    assertEquals(1, ingredientId);

    // Check if the ingredient amount is correct
    Double amount = new QueryBuilder(
        "SELECT amount FROM inventory WHERE user_id = ? AND ingredient_id = ?")
        .addInt(testUser.userId())
        .addInt(1)
        .executeQuerySafe(rs -> rs.next() ? rs.getDouble(1) : null);

    assertEquals(18.0, amount);
  }

  @Test
  @DisplayName("getTotalIngredients should return "
      + "the total amount of ingredients in the shopping list")
  void testGetTotalIngredients() {
    // Add the recipe to the shopping list
    shoppingListDao.addRecipe(testUser.userId(), 1, 4);

    // Add the recipe to the shopping list
    shoppingListDao.addRecipe(testUser.userId(), 2, 4);

    // Add the recipe to the shopping list
    shoppingListDao.addRecipe(testUser.userId(), 3, 4);

    // Retrieve the total amount of ingredients in the shopping list
    List<AmountedIngredient> totalIngredients = shoppingListDao.getTotalIngredients(
        testUser.userId());

    System.out.println(totalIngredients);
    // Check if the total amount of ingredients is correct
    assertEquals(5, totalIngredients.size());

    // Check if the amount in the first ingredient is correct
    assertEquals(24.0, totalIngredients.get(0).getAmount());
  }

  @Test
  @DisplayName("getRecipeWithIngredients method should return a list of "
      + "RecipeWithIngredients objects.")
  void testGetRecipeWithIngredients() {
    // Add recipes to the shopping list
    shoppingListDao.addRecipe(testUser.userId(), 1, 2);
    shoppingListDao.addRecipe(testUser.userId(), 2, 3);
    shoppingListDao.addRecipe(testUser.userId(), 3, 4);

    // Retrieve the saved recipe from the shopping list using only SQL
    List<RecipeWithPartiallyRemovedIngredients> recipes = shoppingListDao.getRecipesWithIngredients(
        testUser.userId()
    );

    // Check if the recipe is in the shopping list
    assertEquals(3, recipes.size());

    // Check if the recipe name is correct
    assertEquals("Test Recipe", recipes.get(0).getName());
    assertEquals("Test Recipe 2", recipes.get(1).getName());
    assertEquals("Test Recipe 3", recipes.get(2).getName());

    // Check if the recipe ingredients are correct
    assertEquals(5, recipes.get(0).getIngredients().size());
    assertEquals(3, recipes.get(1).getIngredients().size());
    assertEquals(4, recipes.get(2).getIngredients().size());

    // Check if the recipe ingredient amounts are correct
    assertEquals(4.0, recipes.get(0).getIngredients().get(0).getAmount());
    assertEquals(6.0, recipes.get(0).getIngredients().get(1).getAmount());
    assertEquals(8.0, recipes.get(0).getIngredients().get(2).getAmount());
  }
}