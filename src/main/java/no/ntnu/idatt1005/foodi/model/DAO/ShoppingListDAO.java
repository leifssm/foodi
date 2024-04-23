package no.ntnu.idatt1005.foodi.model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithIngredients;
import org.jetbrains.annotations.NotNull;

/**
 * This class is responsible for handling the usage of database operations regarding shopping
 * lists.
 *
 * @author Snake727
 * @version 0.8.0
 */
public class ShoppingListDAO {

  /**
   * Deletes all shopping list entries for a user.
   *
   * @param userId the id of the user.
   */
  public void deleteAllForUser(int userId) {
    new QueryBuilder("DELETE FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeUpdateSafe();
  }

  /**
   * Retrieves all ingredients from every recipe in a users shopping list.
   *
   * @param userId the id of the user.
   * @return a list of all ingredients in the shopping list.
   */
  public List<AmountedIngredient> getAllIngredientsFromShoppingList(int userId) {
    List<Recipe> recipes = getRecipesInShoppingListForUser(userId);
    IngredientDAO ingredientDAO = new IngredientDAO();
    List<AmountedIngredient> allIngredients = new ArrayList<>();

    for (Recipe recipe : recipes) {
      List<AmountedIngredient> ingredients = ingredientDAO.retrieveAmountedIngredientsFromRecipe(
          recipe.getId());
      allIngredients.addAll(ingredients);
    }

    return allIngredients;
  }

  /**
   * Retrieves all the recipes in a users shopping list.
   *
   * @param userId the id of the user.
   * @return a list of recipes in the shopping list.
   */
  public List<Recipe> getRecipesInShoppingListForUser(int userId) {
    List<Recipe> recipes = new ArrayList<>();
    RecipeDAO recipeDAO = new RecipeDAO();
    IngredientDAO ingredientDAO = new IngredientDAO();
    Map<Integer, Double> shoppingList = getShoppingListForUser(userId);

    List<Recipe> allRecipes = recipeDAO.retrieveAll();

    for (Recipe recipe : allRecipes) {
      boolean allIngredientsInShoppingList = true;
      List<AmountedIngredient> ingredients = ingredientDAO.retrieveAmountedIngredientsFromRecipe(
          recipe.getId());
      for (AmountedIngredient ingredient : ingredients) {
        if (!shoppingList.containsKey(ingredient.getId())) {
          allIngredientsInShoppingList = false;
          break;
        }
      }
      if (allIngredientsInShoppingList) {
        recipes.add(recipe);
      }
    }
    return recipes;
  }

  /**
   * Retrieves the shopping list for a user.
   *
   * @param userId the id of the user.
   * @return a map of ingredient ids and their amounts.
   */
  public Map<Integer, Double> getShoppingListForUser(int userId) {
    Map<Integer, Double> currentShoppingList = new HashMap<>();

    new QueryBuilder("SELECT * FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          while (rs.next()) {
            currentShoppingList.put(rs.getInt("ingredient_id"), rs.getDouble("amount"));
          }
          return currentShoppingList;
        });

    return currentShoppingList;
  }

  /**
   * Retrieves the recipe ids for a given ingredient id.
   *
   * @param ingredientId the id of the ingredient.
   * @return a list of recipe ids.
   */
  private List<Integer> getRecipeIdsByIngredientId(int ingredientId) {
    List<Integer> recipeIds = new ArrayList<>();

    new QueryBuilder("SELECT recipe_id FROM recipe_ingredient WHERE ingredient_id = ?")
        .addInt(ingredientId)
        .executeQuerySafe(rs -> {
          while (rs.next()) {
            recipeIds.add(rs.getInt("recipe_id"));
          }
          return recipeIds;
        });

    return recipeIds;
  }

  /**
   * Adds a recipe to the shopping list.
   *
   * @param userId   the id of the user you wish to add a recipe to the shopping list for.
   * @param recipeId the id of the recipe you wish to add to the shopping list.
   */

  public void addRecipeToShoppingList(int userId, int recipeId) throws SQLException {
    IngredientDAO ingredientDAO = new IngredientDAO();
    List<AmountedIngredient> ingredients = ingredientDAO.retrieveAmountedIngredientsFromRecipe(
        recipeId);
    Map<Integer, Double> shoppingList = getShoppingListForUser(userId);

    for (AmountedIngredient ingredient : ingredients) {
      shoppingList.put(ingredient.getId(),
          shoppingList.getOrDefault(ingredient.getId(), 0.0) + ingredient.getAmount());
    }

    save(shoppingList, userId, 1);
  }

  /**
   * Saves a shopping list to the database.
   *
   * @param shoppingList the shopping list to be saved.
   * @param userId       the id of the user.
   * @param listId       the id of the shopping list.
   * @throws SQLException if an error occurs while saving the shopping list.
   */

  public void save(@NotNull Map<Integer, Double> shoppingList, int userId, int listId)
      throws SQLException {
    // Delete existing entries for the user to avoid duplicates
    new QueryBuilder("DELETE FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeUpdateSafe();

    int itemNr = 1;
    for (Map.Entry<Integer, Double> entry : shoppingList.entrySet()) {
      new QueryBuilder(
          "INSERT INTO shopping_list "
              + "(SHOPPINGLIST_ID, ITEM_ID, INGREDIENT_ID, AMOUNT, USER_ID) VALUES (?, ?, ?, ?, ?)")
          .addInt(listId)
          .addInt(itemNr++)
          .addInt(entry.getKey())
          .addDouble(entry.getValue())
          .addInt(userId)
          .executeUpdateSafe();
    }
  }

  /**
   * Deletes a recipe from the shopping list.
   *
   * @param userId   the id of the user you wish to remove a recipe from the shopping list for.
   * @param recipeId the id of the recipe you wish to remove from the shopping list.
   */
  public void deleteRecipeFromShoppingList(int userId, int recipeId) throws SQLException {
    IngredientDAO ingredientDAO = new IngredientDAO();
    List<AmountedIngredient> ingredients = ingredientDAO.retrieveAmountedIngredientsFromRecipe(
        recipeId);
    Map<Integer, Double> shoppingList = getShoppingListForUser(userId);

    for (AmountedIngredient ingredient : ingredients) {
      double newAmount =
          shoppingList.getOrDefault(ingredient.getId(), 0.0) - ingredient.getAmount();
      if (newAmount <= 0) {
        shoppingList.remove(ingredient.getId());
      } else {
        shoppingList.put(ingredient.getId(), newAmount);
      }
    }

    save(shoppingList, userId, 1);
  }

  /**
   * Returns a list of RecipeWithIngredient objects containing all the recipes with their
   * ingredients in the shopping list.
   *
   * @param userId the id of the user you wish to retrieve the shopping list for.
   */

  public List<RecipeWithIngredients> getRecipesWithIngredientsInShoppingList(int userId) {
    List<Recipe> recipes = getRecipesInShoppingListForUser(userId);
    List<RecipeWithIngredients> recipesWithIngredients = new ArrayList<>();
    IngredientDAO ingredientDAO = new IngredientDAO();
    Map<Integer, Double> shoppingList = getShoppingListForUser(userId);

    for (Recipe recipe : recipes) {
      List<AmountedIngredient> ingredients = ingredientDAO.retrieveAmountedIngredientsFromRecipe(
          recipe.getId());
      boolean allIngredientsInShoppingList = ingredients.stream()
          .allMatch(ingredient -> shoppingList.containsKey(ingredient.getId()));

      if (allIngredientsInShoppingList) {
        recipesWithIngredients.add(new RecipeWithIngredients(
            recipe.getId(),
            recipe.getName(),
            recipe.getDescription(),
            recipe.getDifficulty(),
            recipe.getDietaryTag(),
            recipe.getDuration(),
            ingredients,
            recipe.getImagePath(),
            recipe.getInstruction()
        ));
      }
    }
    return recipesWithIngredients;
  }
}