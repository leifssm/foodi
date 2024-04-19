package no.ntnu.idatt1005.foodi.model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
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
   * Retrieves the shopping list for a user with partially removed ingredients.
   *
   * @param userId the id of the user.
   * @return a list of recipes with partially removed ingredients.
   */
  public List<Recipe> getRecipesInShoppingListForUser(int userId) {
    RecipeDAO recipeDAO = new RecipeDAO();
    Map<Integer, Double> shoppingList = getShoppingListForUser(userId);
    List<Recipe> recipes = new ArrayList<>();

    for (Integer ingredientId : shoppingList.keySet()) {
      List<Integer> recipeIds = getRecipeIdsByIngredientId(ingredientId);
      for (Integer recipeId : recipeIds) {
        Recipe recipe = recipeDAO.retrieveById(recipeId);
        if (recipe != null) {
          recipes.add(recipe);
        }
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
    return new QueryBuilder("SELECT * FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          Map<Integer, Double> currentShoppingList = new HashMap<>();
          
          while (rs.next()) {
            currentShoppingList.put(rs.getInt("ingredient_id"), rs.getDouble("amount"));
          }
          return currentShoppingList;
        });
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
}