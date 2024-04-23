package no.ntnu.idatt1005.foodi.model.DAO;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class is responsible for handling the interaction between the Ingredient class and the
 * Database.
 *
 * @author Snake727
 * @version 0.9.0
 */

public class IngredientDAO {

  /**
   * Counts the number of ingredient items in the database.
   *
   * @return the number of ingredient items in the database.
   */
  public int countIngredientItems() {
    Integer result = new QueryBuilder("SELECT COUNT(*) FROM ingredient")
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            return rs.getInt(1);
          }
          return null;
        });

    return result != null ? result : 0;
  }

  /**
   * Counts the number of ingredient items in a user's inventory.
   *
   * @param userId The id of the user to count the ingredients from.
   * @return the number of ingredient items in the user's inventory.
   */
  public int countIngredientItemsInUserInventory(int userId) {
    Integer result = new QueryBuilder("SELECT COUNT(*) FROM inventory WHERE user_id = ?")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            return rs.getInt(1);
          }
          return null;
        });

    return result != null ? result : 0;
  }

  /**
   * Saves an ingredient to a recipe. This is done by inserting the recipe id, ingredient id and
   * amount into the recipe_ingredient table.
   *
   * @param recipeId     The id of the recipe to save the ingredient to.
   * @param ingredientId The id of the ingredient to save.
   * @param amount       The amount of the ingredient to save.
   */
  public void saveIngredientToRecipe(int recipeId, int ingredientId, double amount) {
    new QueryBuilder(
        "INSERT INTO recipe_ingredient (recipe_id, ingredient_id, amount) VALUES (?, ?, ?)")
        .addInt(recipeId)
        .addInt(ingredientId)
        .addDouble(amount)
        .executeUpdateSafe();
  }

  /**
   * Saves an ingredient object to the database.
   *
   * @param obj The ingredient object to save.
   */
  public void saveIngredientObject(@NotNull Ingredient obj) {
    new QueryBuilder("INSERT INTO ingredient (name, unit, category) VALUES (?, ?, ?)")
        .addString(obj.getName())
        .addString(obj.getUnit().getDatabaseKey())
        .addString(obj.getCategory().getDatabaseKey())
        .executeUpdateSafe();
  }

  /**
   * Saves an ingredient to a user's inventory.
   *
   * @param userId         The id of the user to save the ingredient to.
   * @param ingredientName The name of the ingredient to save.
   * @param unit           The unit of the ingredient to save.
   * @param category       The category of the ingredient to save.
   * @param amount         The amount of the ingredient to save.
   * @param expirationDate The expiration date of the ingredient.
   */
  public void saveIngredientToUserInventory(
      int userId,
      String ingredientName,
      Ingredient.Unit unit,
      Ingredient.Category category,
      double amount,
      @Nullable Date expirationDate
  ) {
    ingredientName = ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1);
    int ingredientId = findIngredientId(ingredientName, unit, category);
    if (ingredientId == -1) {
      saveIngredient(ingredientName, unit, category);
      ingredientId = findIngredientId(ingredientName, unit, category);
    }

    new QueryBuilder(
        "INSERT INTO inventory "
            + "(ingredient_id, user_id, amount, expiration_date) VALUES (?, ?, ?, ?)")
        .addInt(ingredientId)
        .addInt(userId)
        .addDouble(amount)
        .addDate(expirationDate)
        .executeUpdateSafe();
  }

  /**
   * Finds the id of an ingredient in the database.
   *
   * @param ingredientName The name of the ingredient to find.
   * @param unit           The unit of the ingredient to find.
   * @param category       The category of the ingredient to find.
   * @return The id of the ingredient. Returns -1 if nothing was found.
   */
  private static Integer findIngredientId(@NotNull String ingredientName,
      @NotNull Ingredient.Unit unit, @NotNull Ingredient.Category category) {
    Integer result = new QueryBuilder(
        "SELECT id FROM ingredient WHERE name = ? AND unit = ? AND category = ?")
        .addString(ingredientName)
        .addString(unit.toString())
        .addString(category.toString())
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            return rs.getInt(1);
          }
          return -1;
        });
    return result != null ? result : -1;
  }

  /**
   * Saves an ingredient object to the database.
   *
   * @param ingredientName The name of the ingredient to save.
   * @param unit           The unit of the ingredient to save.
   * @param category       The category of the ingredient to save.
   */
  public void saveIngredient(
      @NotNull String ingredientName,
      @NotNull Ingredient.Unit unit,
      @NotNull Ingredient.Category category
  ) {
    ingredientName = ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1);
    // If no such ingredient exists, proceed with the insertion
    new QueryBuilder("INSERT INTO ingredient (name, unit, category) VALUES (?, ?, ?)")
        .addString(ingredientName)
        .addString(unit.toString())
        .addString(category.toString())
        .executeUpdateSafe();
  }

  /**
   * Merges an ingredient object into the database. If the ingredient already exists, it will be
   * updated. If not, it will be inserted.
   *
   * @param id             The id of the ingredient to merge.
   * @param ingredientName The name of the ingredient to merge.
   * @param unit           The unit of the ingredient to merge.
   * @param category       The category of the ingredient to merge.
   */
  public void mergeIngredient(int id, @NotNull String ingredientName, @NotNull Ingredient.Unit unit,
      @NotNull Ingredient.Category category) {
    // If no such ingredient exists, proceed with the insertion
    new QueryBuilder("MERGE INTO ingredient (id, name, unit, category) VALUES (?, ?, ?, ?)")
        .addInt(id)
        .addString(ingredientName)
        .addString(unit.toString())
        .addString(category.toString())
        .executeUpdateSafe();
  }

  /**
   * Updates an ingredient object in the database.
   *
   * @param obj The ingredient object to update.
   */
  private void updateIngredient(@NotNull Ingredient obj) {
    new QueryBuilder("UPDATE ingredient SET name = ?, unit = ?, category = ? WHERE id = ?")
        .addString(obj.getName())
        .addString(obj.getUnit().getDatabaseKey())
        .addString(obj.getCategory().getDatabaseKey())
        .addInt(obj.getId())
        .executeUpdateSafe();
  }

  /**
   * Updates an ingredient in a user's inventory.
   *
   * @param userId         The id of the user to update the ingredient in.
   * @param ingredientId   The id of the ingredient to update.
   * @param amount         The new amount of the ingredient.
   * @param expirationDate The new expiration date of the ingredient.
   */
  public void updateIngredientInUserInventory(int userId, int ingredientId, double amount,
      @NotNull LocalDate expirationDate) {
    new QueryBuilder(
        "UPDATE inventory SET "
            + "amount = ?, expiration_date = ? WHERE user_id = ? AND ingredient_id = ?")
        .addDouble(amount)
        .addDate(Date.valueOf(expirationDate))
        .addInt(userId)
        .addInt(ingredientId)
        .executeUpdateSafe();
  }

  /**
   * Updates the expiration date of an ingredient in a user's inventory.
   *
   * @param userId         The id of the user to update the ingredient in.
   * @param ingredientId   The id of the ingredient to update.
   * @param expirationDate The new expiration date of the ingredient.
   */
  public void updateIngredientExpirationDate(int userId, int ingredientId,
      @NotNull LocalDate expirationDate) {
    new QueryBuilder(
        "UPDATE inventory SET expiration_date = ? WHERE user_id = ? AND ingredient_id = ?")
        .addDate(Date.valueOf(expirationDate))
        .addInt(userId)
        .addInt(ingredientId)
        .executeUpdateSafe();
  }

  /**
   * Deletes an ingredient object from the database and also deletes related entries in the
   * inventory table.
   *
   * @param obj The ingredient object to delete.
   */
  public void deleteIngredientObject(@NotNull Ingredient obj) {
    // First, delete the related entries in the inventory table
    new QueryBuilder("DELETE FROM inventory WHERE ingredient_id = ?")
        .addInt(obj.getId())
        .executeUpdateSafe();

    // Then, delete the ingredient from the ingredient table
    new QueryBuilder("DELETE FROM ingredient WHERE id = ?")
        .addInt(obj.getId())
        .executeUpdateSafe();
  }

  /**
   * Deletes an ingredient from a user's inventory.
   *
   * @param userId The id of the user to delete the ingredient from.
   * @param id     The id of the inventory ingredient to delete.
   */
  public void deleteIngredientFromUserInventory(int userId, int id) {
    new QueryBuilder("DELETE FROM inventory WHERE user_id = ? AND id = ?")
        .addInt(userId)
        .addInt(id)
        .executeUpdateSafe();
  }

  /**
   * Retrieves an ingredient object from the database.
   *
   * @param obj The ingredient object to retrieve.
   * @return The Ingredient object. Returns null if nothing was found.
   */
  public @Nullable Ingredient retrieveIngredientObject(@NotNull Ingredient obj) {
    return new QueryBuilder("SELECT * FROM ingredient WHERE id = ?")
        .addInt(obj.getId())
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            String name = rs.getString("name");
            Ingredient.Unit unit = Ingredient.Unit.fromKey(rs.getString("unit"));
            Ingredient.Category category = Ingredient.Category.fromKey(rs.getString("category"));

            return new Ingredient(obj.getId(), name, unit, category);
          }
          return null;
        });
  }

  /**
   * Retrieves all ingredients and their amounts in a recipe. This is done by checking the recipe
   * table for a matching recipe id.
   *
   * @param recipeId The id of the recipe to retrieve ingredients from.
   * @return The Ingredient object. Returns null if nothing was found.
   */
  public @Nullable List<AmountedIngredient> retrieveAmountedIngredientsFromRecipe(int recipeId) {
    return new QueryBuilder(
        "SELECT i.*, ri.amount FROM recipe_ingredient ri "
            + "JOIN ingredient i ON ri.ingredient_id = i.id WHERE ri.recipe_id = ?")
        .addInt(recipeId)
        .executeQuerySafe(rs -> {
          List<AmountedIngredient> ingredients = new ArrayList<>();
          while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            Ingredient.Unit unit = Ingredient.Unit.fromKey(rs.getString("unit"));
            Ingredient.Category category = Ingredient.Category.fromKey(rs.getString("category"));
            double amount = rs.getDouble("amount");

            ingredients.add(new AmountedIngredient(id, name, unit, category, amount));
          }
          return ingredients;
        });
  }

  /**
   * Retrieve a list of all the ingredients in a users inventory. The list contains
   * AmountedIngredient objects with the amount of each ingredient.
   *
   * @param userId The id of the user to retrieve the inventory from.
   * @return A list of AmountedIngredient objects.
   */
  public @Nullable List<AmountedIngredient> retrieveAmountedIngredientsFromInventory(int userId) {
    return new QueryBuilder("SELECT * FROM inventory WHERE user_id = ?")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          List<AmountedIngredient> ingredients = new ArrayList<>();
          while (rs.next()) {
            int ingredientId = rs.getInt("ingredient_id");
            double amount = rs.getDouble("amount");

            Ingredient ingredient = retrieveIngredientById(ingredientId);
            assert ingredient != null;
            ingredients.add(new AmountedIngredient(ingredient.getId(), ingredient.getName(),
                ingredient.getUnit(), ingredient.getCategory(), amount));
          }
          return ingredients;
        });
  }

  /**
   * Retrieve an ingredient from the database by its ID.
   *
   * @param id the id of the ingredient to retrieve.
   * @return The Ingredient object. Returns null if nothing was found.
   */
  public @Nullable Ingredient retrieveIngredientById(int id) {
    return new QueryBuilder("SELECT * FROM ingredient WHERE id = ?")
        .addInt(id)
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            String name = rs.getString("name");
            Ingredient.Unit unit = Ingredient.Unit.fromKey(rs.getString("unit"));
            Ingredient.Category category = Ingredient.Category.fromKey(rs.getString("category"));

            return new Ingredient(id, name, unit, category);
          }
          return null;
        });
  }

  /**
   * Retrieve a list of all the ingredients in a users inventory. The list contains
   * ExpiringIngredient objects with the amount and expiration date of each ingredient.
   *
   * @param userId The id of the user to retrieve the inventory from.
   * @return A list of ExpiringIngredient objects.
   */

  public @Nullable List<ExpiringIngredient> retrieveExpiringIngredientsFromInventory(int userId) {
    return new QueryBuilder("SELECT * FROM inventory WHERE user_id = ?")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          List<ExpiringIngredient> ingredients = new ArrayList<>();
          while (rs.next()) {
            int id = rs.getInt("id");
            int ingredientId = rs.getInt("ingredient_id");
            double amount = rs.getDouble("amount");
            Date expirationDateSql = rs.getDate("expiration_date");
            LocalDate expirationDate = expirationDateSql.toLocalDate();
            boolean isFrozen = rs.getBoolean("is_frozen");

            Ingredient ingredient = retrieveIngredientById(ingredientId);
            assert ingredient != null;
            ExpiringIngredient expiringIngredient = new ExpiringIngredient(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getUnit(),
                ingredient.getCategory(),
                amount,
                expirationDate,
                isFrozen
            );
            expiringIngredient.setInventoryId(id);
            ingredients.add(expiringIngredient);
          }
          return ingredients;
        });
  }

  /**
   * Retrieve the total amount of an ingredient in a user's inventory.
   *
   * @param userId The id of the user to retrieve the inventory from.
   * @return The total amount of the ingredient in the user's inventory.
   */
  public double getTotalAmountOfIngredientsInInventory(int userId) {
    Double result = new QueryBuilder("SELECT SUM(amount) FROM inventory WHERE user_id = ?")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            return rs.getDouble(1);
          }
          return null;
        });

    return result != null ? result : 0;
  }

  public void toggleFreezeIngredient(int userId, int ingredientId, boolean isFrozen) {
    new QueryBuilder(
        "UPDATE inventory SET is_frozen = ? WHERE user_id = ? AND ingredient_id = ?")
        .addBoolean(isFrozen)
        .addInt(userId)
        .addInt(ingredientId)
        .executeUpdateSafe();
  }
}