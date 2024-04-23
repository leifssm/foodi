package no.ntnu.idatt1005.foodi.model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithIngredients;

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
  public void clearShoppingList(int userId) {
    new QueryBuilder("DELETE FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeUpdateSafe();
  }


  /**
   * Adds a recipe to the shopping list by inserting the parameters into the shopping list table.
   * This method also checks if the recipe is already in the shopping list, and if so, updates the
   * portions.
   *
   * @param userId   the id of the user to add a recipe to the shopping list for.
   * @param recipeId the id of the recipe to add to the shopping list.
   * @param portions the amount of portions for the recipe.
   */

  public void addRecipe(int userId, int recipeId, int portions) {
    Integer existingPortions = new QueryBuilder(
        "SELECT portions FROM shopping_list WHERE user_id = ? AND recipe_id = ?")
        .addInt(userId)
        .addInt(recipeId)
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    if (existingPortions != null) {
      new QueryBuilder(
          "UPDATE shopping_list SET portions = portions + ? WHERE user_id = ? AND recipe_id = ?")
          .addInt(portions)
          .addInt(userId)
          .addInt(recipeId)
          .executeUpdateSafe();
    } else {
      new QueryBuilder("INSERT INTO shopping_list (user_id, recipe_id, portions) VALUES (?, ?, ?)")
          .addInt(userId)
          .addInt(recipeId)
          .addInt(portions)
          .executeUpdateSafe();
    }
  }

  /**
   * Deletes a recipe from the shopping list.
   *
   * @param userId   the id of the user to remove a recipe from the shopping list for.
   * @param recipeId the id of the recipe to remove from the shopping list.
   */
  public void deleteRecipe(int userId, int recipeId) {
    new QueryBuilder("DELETE FROM shopping_list WHERE user_id = ? AND recipe_id = ?")
        .addInt(userId)
        .addInt(recipeId)
        .executeUpdateSafe();
  }

  /**
   * Returns a list of RecipeWithIngredient objects containing all the recipes with their
   * ingredients in the shopping list. This method also scales each ingredient in every recipe by
   * the amount of portions.
   *
   * @param userId the id of the user to retrieve the shopping list for.
   * @return a list of RecipeWithIngredient objects.
   */

  public List<RecipeWithIngredients> getRecipesWithIngredients(int userId) {
    return new QueryBuilder("SELECT * FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          List<RecipeWithIngredients> recipes = new ArrayList<>();
          while (rs.next()) {
            int recipeId = rs.getInt("recipe_id");
            int portions = rs.getInt("portions");
            Recipe recipe = new RecipeDAO().retrieveById(recipeId);
            List<AmountedIngredient> ingredients = new IngredientDAO().retrieveAmountedIngredientsFromRecipe(
                recipeId);
            for (AmountedIngredient ingredient : ingredients) {
              ingredient.setAmount(ingredient.getAmount() * portions);
            }
            RecipeWithIngredients recipeWithIngredients = new RecipeWithIngredients(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getDifficulty(),
                recipe.getDietaryTag(),
                recipe.getDuration(),
                ingredients,
                recipe.getImagePath(),
                recipe.getInstruction()
            );
            recipes.add(recipeWithIngredients);
          }
          return recipes;
        });
  }

  /**
   * Adds all the ingredients from the recipes from the shopping list to the inventory of given
   * user.
   *
   * @param userId the id of the user to add the shopping list to the inventory for.
   * @throws SQLException if an error occurs while executing the query.
   */
  public void addShoppingListToInventory(int userId) throws SQLException {
    new QueryBuilder(
        "INSERT INTO inventory (user_id, ingredient_id, amount) "
            + "SELECT ?, ingredient_id, SUM(amount * "
            + "(SELECT portions FROM shopping_list WHERE user_id = ? AND recipe_id = ri.recipe_id)) "
            + "FROM recipe_ingredient ri "
            + "WHERE recipe_id IN (SELECT recipe_id FROM shopping_list WHERE user_id = ?) "
            + "GROUP BY ingredient_id")
        .addInt(userId)
        .addInt(userId)
        .addInt(userId)
        .executeUpdateSafe();
  }

  /**
   * Returns a list of AmountedIngredient objects containing all the ingredients in the shopping
   * list. The list returns a summed amount of each ingredient.
   *
   * @param userId the id of the user to retrieve the list from.
   * @return a list of AmountedIngredient objects containing all the ingredients in the shopping
   * list.
   */
  public List<AmountedIngredient> getTotalIngredients(int userId) {
    Map<Integer, AmountedIngredient> ingredientMap = new HashMap<>();
    List<Recipe> recipes = getRecipes(userId);
    for (Recipe recipe : recipes) {
      int portions = getPortions(userId, recipe.getId());
      List<AmountedIngredient> ingredients = getIngredients(recipe.getId());
      for (AmountedIngredient ingredient : ingredients) {
        double scaledAmount = ingredient.getAmount() * portions;
        if (ingredientMap.containsKey(ingredient.getId())) {
          AmountedIngredient existingIngredient = ingredientMap.get(ingredient.getId());
          double totalAmount = existingIngredient.getAmount() + scaledAmount;
          existingIngredient.setAmount(totalAmount);
        } else {
          ingredient.setAmount(scaledAmount);
          ingredientMap.put(ingredient.getId(), ingredient);
        }
      }
    }
    return new ArrayList<>(ingredientMap.values());
  }

  /**
   * Returns a list of Recipe objects containing all the recipes in the shopping list.
   *
   * @param userId the id of the user to retrieve the list for.
   * @return a list of Recipe objects containing all the recipes in the shopping list.
   */
  private List<Recipe> getRecipes(int userId) {
    return new QueryBuilder(
        "SELECT * FROM recipe WHERE id IN (SELECT recipe_id FROM shopping_list WHERE user_id = ?)")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          List<Recipe> recipes = new ArrayList<>();
          while (rs.next()) {
            recipes.add(new Recipe(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                Recipe.Difficulty.valueOf(rs.getString("difficulty").toUpperCase()),
                Recipe.DietaryTag.valueOf(rs.getString("dietary_tag").toUpperCase()),
                rs.getInt("duration"),
                rs.getString("imagePath"),
                rs.getString("instruction")
            ));
          }
          return recipes;
        });
  }

  /**
   * Returns amount of portions for a given recipe in the shopping list.
   *
   * @param userId   the id of the user to retrieve the portions for.
   * @param recipeId the id of the recipe to retrieve the portions for.
   * @return the amount of portions in a recipe in the shopping list.
   */
  public int getPortions(int userId, int recipeId) {
    Integer result = new QueryBuilder(
        "SELECT portions FROM shopping_list WHERE user_id = ? AND recipe_id = ?")
        .addInt(userId)
        .addInt(recipeId)
        .executeQuerySafe(rs -> rs.next() ? rs.getInt(1) : null);

    return result != null ? result : 0;
  }

  /**
   * Returns a list of AmountedIngredient objects containing all the ingredients in a recipe.
   *
   * @param recipeId the id of the recipe to retrieve the list of ingredients for.
   * @return a list of summed amounts for each ingredient in a recipe.
   */
  private List<AmountedIngredient> getIngredients(int recipeId) {
    return new QueryBuilder("SELECT * FROM recipe_ingredient WHERE recipe_id = ?")
        .addInt(recipeId)
        .executeQuerySafe(rs -> {
          List<AmountedIngredient> ingredients = new ArrayList<>();
          while (rs.next()) {
            Ingredient ingredient = retrieveIngredientById(rs.getInt("ingredient_id"));
            if (ingredient != null) {
              AmountedIngredient amountedIngredient = new AmountedIngredient(
                  ingredient.getId(),
                  ingredient.getName(),
                  ingredient.getUnit(),
                  ingredient.getCategory(),
                  rs.getDouble("amount")
              );
              ingredients.add(amountedIngredient);
            }
          }
          return ingredients;
        });
  }

  /**
   * Retrieves an ingredient by its id. If the ingredient does not exist, null is returned.
   *
   * @param ingredientId the id of the ingredient to retrieve.
   * @return an ingredient object if the ingredient exists, otherwise null.
   */
  private Ingredient retrieveIngredientById(int ingredientId) {
    return new QueryBuilder("SELECT * FROM ingredient WHERE id = ?")
        .addInt(ingredientId)
        .executeQuerySafe(rs -> rs.next() ? new Ingredient(
            rs.getInt("id"),
            rs.getString("name"),
            Ingredient.Unit.valueOf(rs.getString("unit").toUpperCase()),
            Ingredient.Category.valueOf(rs.getString("category").toUpperCase())
        ) : null);
  }
}