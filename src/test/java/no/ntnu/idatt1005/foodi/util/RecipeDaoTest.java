package no.ntnu.idatt1005.foodi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import no.ntnu.idatt1005.foodi.model.daos.IngredientDao;
import no.ntnu.idatt1005.foodi.model.daos.RecipeDao;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Unit;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.model.repository.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * This class is responsible for testing the RecipeDatabaseAccess class.
 *
 * @author Snake727
 * @version 0.1.0
 */

class RecipeDaoTest {

  private static IngredientDao ingredientDAO;
  private static RecipeDao recipeDao;

  @BeforeEach
  public void setUp() {
    // Initialize the main database
    Database.initializeEmpty();

    // Initialize a new IngredientDao and RecipeDao object.
    ingredientDAO = new IngredientDao();
    recipeDao = new RecipeDao();
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
  @DisplayName("The retrieved name should match the one saved in the database by the"
      + " saveRecipe method.")
  void testSaveRecipe() {
    // Save a new recipe
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Compare the recipe with the one retrieved from the database
    assertEquals("Test Recipe", recipeDao.retrieveById(1).getName());
  }

  @Test
  @DisplayName("retrieveById should return the recipe with the given ID.")
  void testRetrieveById() {
    // Save a new recipe
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Compare the recipe with the one retrieved from the database
    assertEquals("Test Recipe", recipeDao.retrieveById(1).getName());
  }

  @Test
  @DisplayName("updateRecipeById should update the recipe with the given ID.")
  void testUpdateRecipeById() {
    // Save a new recipe
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals("Test Recipe", recipeDao.retrieveById(1).getName());

    // Update the recipe with new unique information
    recipeDao.updateRecipeById(1, "Updated Recipe", "This is an updated test recipe", "MEDIUM",
        "VEGETARIAN", 45, "updated.jpg", "This is an updated test instruction");

    // Check that the recipe has been updated
    assertEquals("Updated Recipe", recipeDao.retrieveById(1).getName());
  }

  @Test
  @DisplayName("deleteRecipe should delete the recipe with the given ID.")
  void testDeleteRecipe() {
    // Save a new recipe
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals("Test Recipe", recipeDao.retrieveById(1).getName());

    // Delete the recipe
    recipeDao.delete(recipeDao.retrieveById(1));

    // Check that the recipe has been deleted
    assertNull(recipeDao.retrieveById(1));
  }

  @Test
  @DisplayName("retrieveAll should return a list of all recipes in the database.")
  void testRetrieveAll() {
    // Save a new recipe
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals(1, recipeDao.retrieveAll().size());
  }

  @Test
  @DisplayName("deleteRecipeById should delete the recipe with the given ID.")
  void testDeleteRecipeById() {
    // Save a new recipe
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals("Test Recipe", recipeDao.retrieveById(1).getName());

    // Delete the recipe
    recipeDao.deleteRecipeById(1);

    // Check that the recipe has been deleted
    assertNull(recipeDao.retrieveById(1));
  }

  @Test
  @DisplayName("retrieveRecipeWithIngredientsById should return a RecipeWithIngredients object"
      + " with the given recipe ID.")
  void testRetrieveRecipeWithIngredientsById() throws SQLException {
    // Save a new recipe
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals("Test Recipe", recipeDao.retrieveById(1).getName());

    // Save a new ingredient
    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.VEGETABLE);

    // Check that ingredient has been saved
    assertEquals("Test Ingredient",
        Objects.requireNonNull(ingredientDAO.retrieveIngredientById(1)).getName());

    // Add the ingredient to the recipe
    ingredientDAO.saveIngredientToRecipe(1, 1, 100);

    // Check that the ingredient has been added to the recipe
    assertEquals(1, recipeDao.retrieveRecipeWithIngredientsById(1).getIngredients().size());
  }

  @Test
  @DisplayName("retrieveAllRecipesWithIngredients "
      + "should return a list of all RecipeWithIngredients objects")
  void testRetrieveAllRecipesWithIngredients() throws SQLException {
    // Save five new recipes
    recipeDao.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDao.saveRecipe("Test Recipe 2", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDao.saveRecipe("Test Recipe 3", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDao.saveRecipe("Test Recipe 4", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDao.saveRecipe("Test Recipe 5", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals("Test Recipe", recipeDao.retrieveById(1).getName());

    // Save ten different ingredients with unique combinations
    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.VEGETABLE);
    ingredientDAO.saveIngredient("Test Ingredient 2", Unit.GALLON, Category.MEAT);
    ingredientDAO.saveIngredient("Test Ingredient 3", Unit.KILOGRAM, Category.DAIRY);
    ingredientDAO.saveIngredient("Test Ingredient 4", Unit.LITER, Category.VEGETABLE);
    ingredientDAO.saveIngredient("Test Ingredient 5", Unit.MILLILITER, Category.MEAT);
    ingredientDAO.saveIngredient("Test Ingredient 6", Unit.OUNCE, Category.DAIRY);
    ingredientDAO.saveIngredient("Test Ingredient 7", Unit.PIECE, Category.VEGETABLE);
    ingredientDAO.saveIngredient("Test Ingredient 8", Unit.PINT, Category.MEAT);
    ingredientDAO.saveIngredient("Test Ingredient 9", Unit.POUNDS, Category.DAIRY);
    ingredientDAO.saveIngredient("Test Ingredient 10", Unit.QUART, Category.VEGETABLE);

    // Check that ingredient has been saved
    assertEquals("Test Ingredient",
        Objects.requireNonNull(ingredientDAO.retrieveIngredientById(1)).getName());

    // Add the ingredients to the recipes
    // Recipe 1
    ingredientDAO.saveIngredientToRecipe(1, 1, 100);
    ingredientDAO.saveIngredientToRecipe(1, 9, 200);

    // Recipe 2
    ingredientDAO.saveIngredientToRecipe(2, 2, 100);
    ingredientDAO.saveIngredientToRecipe(2, 7, 200);
    ingredientDAO.saveIngredientToRecipe(2, 3, 300);
    ingredientDAO.saveIngredientToRecipe(2, 10, 400);
    ingredientDAO.saveIngredientToRecipe(2, 6, 500);

    // Recipe 3
    ingredientDAO.saveIngredientToRecipe(3, 3, 100);
    ingredientDAO.saveIngredientToRecipe(3, 4, 200);
    ingredientDAO.saveIngredientToRecipe(3, 10, 300);
    ingredientDAO.saveIngredientToRecipe(3, 2, 400);

    // Recipe 4
    ingredientDAO.saveIngredientToRecipe(4, 4, 100);

    // Recipe 5
    ingredientDAO.saveIngredientToRecipe(5, 5, 100);
    ingredientDAO.saveIngredientToRecipe(5, 7, 200);
    ingredientDAO.saveIngredientToRecipe(5, 4, 300);
    ingredientDAO.saveIngredientToRecipe(5, 6, 400);
    ingredientDAO.saveIngredientToRecipe(5, 1, 500);
    ingredientDAO.saveIngredientToRecipe(5, 2, 600);
    ingredientDAO.saveIngredientToRecipe(5, 3, 700);

    // Check that the ingredients have been added to the recipes
    assertEquals(2, recipeDao.retrieveRecipeWithIngredientsById(1).getIngredients().size());
    assertEquals(5, recipeDao.retrieveRecipeWithIngredientsById(2).getIngredients().size());
    assertEquals(4, recipeDao.retrieveRecipeWithIngredientsById(3).getIngredients().size());
    assertEquals(1, recipeDao.retrieveRecipeWithIngredientsById(4).getIngredients().size());
    assertEquals(7, recipeDao.retrieveRecipeWithIngredientsById(5).getIngredients().size());

    // Check if the retrieveAllRecipesWithIngredients method correctly returns the recipes that only
    // contain the ingredients that were given as arguments.
    List<Recipe> recipes = List.of(recipeDao.retrieveAllRecipesWithIngredients(2, 7, 9, 10, 1));
    List<Recipe> recipes2 = List.of(recipeDao.retrieveAllRecipesWithIngredients(2));
    List<Recipe> recipes3 = List.of(recipeDao.retrieveAllRecipesWithIngredients(1, 6));
    List<Recipe> recipes4 = List.of(
        recipeDao.retrieveAllRecipesWithIngredients(5, 7, 4, 6, 1, 2, 3));

    List<List<Recipe>> allRecipes = List.of(recipes, recipes2, recipes3, recipes4);
    for (List<Recipe> ignored : allRecipes) {
      for (Recipe recipe : recipes) {
        // Retrieve the ingredients of the recipe
        List<AmountedIngredient> ingredients = recipeDao.retrieveRecipeWithIngredientsById(
            recipe.getId()).getIngredients();

        // Convert the ingredient list to a set of ingredient IDs for easier comparison
        Set<Integer> ingredientIds = ingredients.stream()
            .map(Ingredient::getId)
            .collect(Collectors.toSet());

        // Check if the ingredient IDs match the ones we used to retrieve the recipes
        assertTrue(ingredientIds.containsAll(Arrays.asList(2, 7, 9, 10, 1)));
      }
    }
  }
}
