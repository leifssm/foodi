package no.ntnu.idatt1005.foodi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Unit;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.model.repository.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class is responsible for testing the RecipeDatabaseAccess class.
 *
 * @author Snake727
 * @version 0.1.0
 */

public class RecipeDBOTest {

  private static IngredientDAO ingredientDAO;
  private static RecipeDAO recipeDAO;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    Database.initializeEmpty();

    // Initialize a new IngredientDAO object
    ingredientDAO = new IngredientDAO();
    recipeDAO = new RecipeDAO();
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

  @Test
  void testSaveRecipe() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Compare the recipe with the one retrieved from the database
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");
  }

  @Test
  void testRetrieveById() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Compare the recipe with the one retrieved from the database
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");
  }

  @Test
  void testUpdateRecipeById() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");

    // Update the recipe with new unique information
    recipeDAO.updateRecipeById(1, "Updated Recipe", "This is an updated test recipe", "MEDIUM",
        "VEGETARIAN", 45, "updated.jpg", "This is an updated test instruction");

    // Check that the recipe has been updated
    assertEquals(recipeDAO.retrieveById(1).getName(), "Updated Recipe");
  }

  @Test
  void testDeleteRecipe() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");

    // Delete the recipe
    recipeDAO.delete(recipeDAO.retrieveById(1));

    // Check that the recipe has been deleted
    assertNull(recipeDAO.retrieveById(1));
  }

  @Test
  void testRetrieveAll() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals(recipeDAO.retrieveAll().size(), 1);
  }

  @Test
  void testDeleteRecipeById() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals(recipeDAO.retrieveById(1).getName(), "Test Recipe");

    // Delete the recipe
    recipeDAO.deleteRecipeById(1);

    // Check that the recipe has been deleted
    assertNull(recipeDAO.retrieveById(1));
  }

  @Test
  void testRetrieveRecipeWithIngredientsById() throws SQLException {
    // Save a new recipe
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals("Test Recipe", recipeDAO.retrieveById(1).getName());

    // Save a new ingredient
    ingredientDAO.saveIngredient("Test Ingredient", Ingredient.Unit.GRAM, Category.VEGETABLE);

    // Check that ingredient has been saved
    assertEquals("Test Ingredient",
        Objects.requireNonNull(ingredientDAO.retrieveIngredientById(1)).getName());

    // Add the ingredient to the recipe
    ingredientDAO.saveIngredientToRecipe(1, 1, 100);

    // Check that the ingredient has been added to the recipe
    assertEquals(1, recipeDAO.retrieveRecipeWithIngredientsById(1).getIngredients().size());
  }

  @Test
  void testRetrieveAllRecipesWithIngredients() throws SQLException {
    // Save five new recipes
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 2", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 3", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 4", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 5", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals("Test Recipe", recipeDAO.retrieveById(1).getName());

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
    assertEquals(2, recipeDAO.retrieveRecipeWithIngredientsById(1).getIngredients().size());
    assertEquals(5, recipeDAO.retrieveRecipeWithIngredientsById(2).getIngredients().size());
    assertEquals(4, recipeDAO.retrieveRecipeWithIngredientsById(3).getIngredients().size());
    assertEquals(1, recipeDAO.retrieveRecipeWithIngredientsById(4).getIngredients().size());
    assertEquals(7, recipeDAO.retrieveRecipeWithIngredientsById(5).getIngredients().size());

    // Check if the retrieveAllRecipesWithIngredients method correctly returns the recipes that only
    // contain the ingredients that were given as arguments.
    List<Recipe> recipes = List.of(recipeDAO.retrieveAllRecipesWithIngredients(2, 7, 9, 10, 1));
    List<Recipe> recipes2 = List.of(recipeDAO.retrieveAllRecipesWithIngredients(2));
    List<Recipe> recipes3 = List.of(recipeDAO.retrieveAllRecipesWithIngredients(1, 6));
    List<Recipe> recipes4 = List.of(
        recipeDAO.retrieveAllRecipesWithIngredients(5, 7, 4, 6, 1, 2, 3));

    List<List<Recipe>> allRecipes = List.of(recipes, recipes2, recipes3, recipes4);
    for (List<Recipe> recipeList : allRecipes) {
      for (Recipe recipe : recipes) {
        // Retrieve the ingredients of the recipe
        List<AmountedIngredient> ingredients = recipeDAO.retrieveRecipeWithIngredientsById(
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

  @Test
  void testRetrieveAllRecipesWithIngredientsWithNoIngredient() throws SQLException {
    // Save five new recipes
    recipeDAO.saveRecipe("Test Recipe", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 2", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 3", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 4", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");
    recipeDAO.saveRecipe("Test Recipe 5", "This is a test recipe", "EASY", "VEGAN", 30, "test.jpg",
        "This is a test instruction");

    // Check that recipe has been saved
    assertEquals("Test Recipe", recipeDAO.retrieveById(1).getName());

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
    assertEquals(2, recipeDAO.retrieveRecipeWithIngredientsById(1).getIngredients().size());
    assertEquals(5, recipeDAO.retrieveRecipeWithIngredientsById(2).getIngredients().size());
    assertEquals(4, recipeDAO.retrieveRecipeWithIngredientsById(3).getIngredients().size());
    assertEquals(1, recipeDAO.retrieveRecipeWithIngredientsById(4).getIngredients().size());
    assertEquals(7, recipeDAO.retrieveRecipeWithIngredientsById(5).getIngredients().size());

    // Check if the retrieveAllRecipesWithIngredients method correctly returns the recipes that only
    // contain the ingredients that were given as arguments.
    List<Recipe> recipes = List.of(recipeDAO.retrieveAllRecipesWithIngredients(2, 7, 9, 10, 1));
    List<Recipe> recipes2 = List.of(recipeDAO.retrieveAllRecipesWithIngredients(2));
    List<Recipe> recipes3 = List.of(recipeDAO.retrieveAllRecipesWithIngredients(1, 6));
    List<Recipe> recipes4 = List.of(
        recipeDAO.retrieveAllRecipesWithIngredients(5, 7, 4, 6, 1, 2, 3));

    List<List<Recipe>> allRecipes = List.of(recipes, recipes2, recipes3, recipes4);
    for (List<Recipe> recipeList : allRecipes) {
      for (Recipe recipe : recipes) {
        // Retrieve the ingredients of the recipe
        List<AmountedIngredient> ingredients = recipeDAO.retrieveRecipeWithIngredientsById(
            recipe.getId()).getIngredients();

        // Convert the ingredient list to a set of ingredient IDs for easier comparison
        Set<Integer> ingredientIds = ingredients.stream()
            .map(Ingredient::getId)
            .collect(Collectors.toSet());

        // Check if the ingredient IDs match the ones we used to retrieve the recipes
        assertFalse(ingredientIds.contains(8));
      }
    }
  }
}
