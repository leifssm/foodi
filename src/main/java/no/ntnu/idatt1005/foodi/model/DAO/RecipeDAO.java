package no.ntnu.idatt1005.foodi.model.DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithIngredients;
import org.jetbrains.annotations.NotNull;

/**
 * This class is responsible for handling the usage of database operations regarding stored recipes
 * in the database.
 *
 * @version 0.8.0
 */
public class RecipeDAO {

  /**
   * Saves a recipe object to the database.
   *
   * @param obj the recipe object to be saved,
   */
  public void saveRecipeObject(@NotNull Recipe obj) {
    saveRecipe(
        obj.getName(),
        obj.getDescription(),
        obj.getDifficulty().toString(),
        obj.getDietaryTag().toString(),
        obj.getDuration(),
        obj.getImagePath(),
        obj.getInstruction()
    );
  }

  /**
   * Saves a new recipe to the database.
   *
   * @param name        The name of the recipe.
   * @param description The description of the recipe.
   * @param difficulty  The difficulty of the recipe.
   * @param dietaryTag  The dietary tag of the recipe.
   * @param duration    The duration of the recipe.
   * @param imagePath   The image path of the recipe.
   * @param instruction The instruction of the recipe.
   */
  public void saveRecipe(String name, String description, String difficulty, String dietaryTag,
      int duration, String imagePath, String instruction) {
    new QueryBuilder(
        "INSERT INTO recipe (name, description, difficulty, "
            + "dietary_tag, duration, imagePath, instruction) VALUES (?, ?, ?, ?, ?, ?, ?)")
        .addString(name)
        .addString(description)
        .addString(difficulty)
        .addString(dietaryTag)
        .addInt(duration)
        .addString(imagePath)
        .addString(instruction)
        .executeUpdateSafe();
  }

  /**
   * Merges a recipe to the recipe table in the database.
   *
   * @param id          the id of the recipe.
   * @param name        the name of the recipe.
   * @param description the description of the recipe.
   * @param difficulty  the difficulty of the recipe.
   * @param dietaryTag  the dietary tag of the recipe.
   * @param duration    the duration of the recipe.
   * @param imagePath   the image path of the recipe.
   * @param instruction the instruction of the recipe.
   */
  public void mergeRecipe(int id, String name, String description, String difficulty,
      String dietaryTag,
      int duration, String imagePath, String instruction) {
    new QueryBuilder(
        "MERGE INTO recipe (id, name, description, difficulty,"
            + "dietary_tag, duration, imagePath, instruction)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
        .addInt(id)
        .addString(name)
        .addString(description)
        .addString(difficulty)
        .addString(dietaryTag)
        .addInt(duration)
        .addString(imagePath)
        .addString(instruction)
        .executeUpdateSafe();
  }

  /**
   * Updates a recipe in the recipe table in the database.
   *
   * @param id          the id of the recipe.
   * @param name        the name of the recipe.
   * @param description the description of the recipe.
   * @param difficulty  the difficulty of the recipe.
   * @param dietaryTag  the dietary tag of the recipe.
   * @param duration    the duration of the recipe.
   * @param imagePath   the image path of the recipe.
   * @param instruction the instruction of the recipe.
   */
  public void updateRecipeById(int id, String name, String description, String difficulty,
      String dietaryTag, int duration, String imagePath, String instruction) {
    new QueryBuilder(
        "UPDATE recipe SET name = ?, description = ?, difficulty = ?, "
            + "dietary_tag = ?, duration = ?, imagePath = ?, instruction = ? WHERE id = ?")
        .addString(name)
        .addString(description)
        .addString(difficulty)
        .addString(dietaryTag)
        .addInt(duration)
        .addString(imagePath)
        .addString(instruction)
        .addInt(id)
        .executeUpdateSafe();
  }

  /**
   * Updates a recipe object in the recipe table in the database.
   *
   * @param obj the recipe object to be updated,
   */
  public void update(@NotNull Recipe obj) {
    new QueryBuilder(
        "UPDATE recipe SET name = ?, description = ?, difficulty = ?, "
            + "dietary_tag = ?, duration = ?, imagePath = ?, instruction = ? WHERE id = ?")
        .addString(obj.getName())
        .addString(obj.getDescription())
        .addString(obj.getDifficulty().toString())
        .addString(obj.getDietaryTag().toString())
        .addInt(obj.getDuration())
        .addString(obj.getImagePath())
        .addString(obj.getInstruction())
        .addInt(obj.getId())
        .executeUpdateSafe();
  }

  /**
   * Deletes a recipe from the recipe table in the database.
   *
   * @param obj the recipe object to be deleted.
   */
  public void delete(@NotNull Recipe obj) {
    new QueryBuilder("DELETE FROM recipe WHERE id = ?")
        .addInt(obj.getId())
        .executeUpdateSafe();
  }

  /**
   * Deletes a recipe from the recipe table in the database.
   *
   * @param id the id of the recipe to be deleted.
   */
  public void deleteRecipeById(int id) {
    new QueryBuilder("DELETE FROM recipe WHERE id = ?")
        .addInt(id)
        .executeUpdateSafe();
  }

  /**
   * Retrieves a recipe object from the database.
   *
   * @param obj the recipe object to be retrieved
   * @return the recipe object
   */
  public Recipe retrieveRecipeObject(@NotNull Recipe obj) {
    return new QueryBuilder("SELECT * FROM recipe WHERE id = ?")
        .addInt(obj.getId())
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            return new Recipe(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                Recipe.Difficulty.valueOf(rs.getString("difficulty").toUpperCase()),
                Recipe.DietaryTag.valueOf(rs.getString("dietary_tag").toUpperCase()),
                rs.getInt("duration"),
                rs.getString("imagePath"),
                rs.getString("instruction")
            );
          }
          return null;
        });
  }

  /**
   * Retrieves all recipes from the database as objects and returns them as a list.
   *
   * @return a list of all recipes in the database.
   */
  public List<Recipe> retrieveAll() {
    return new QueryBuilder("SELECT * FROM recipe")
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
   * Returns a RecipeWithIngredients object by retrieving the necessary data from the database by
   * searching using the recipe id.
   *
   * @param recipeId The id of the recipe to retrieve.
   * @return A RecipeWithIngredients object.
   */
  public RecipeWithIngredients retrieveRecipeWithIngredientsById(int recipeId) {
    Recipe recipe = retrieveById(recipeId);
    if (recipe == null) {
      return null;
    }

    IngredientDAO ingredientDAO = new IngredientDAO();
    List<AmountedIngredient> ingredients = ingredientDAO.retrieveAmountedIngredientsFromRecipe(
        recipeId);

    return new RecipeWithIngredients(
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
  }

  /**
   * Retrieves a recipe object from the database.
   *
   * @param id the id of the recipe to be retrieved.
   * @return the recipe object.
   */
  public Recipe retrieveById(int id) {
    return new QueryBuilder("SELECT * FROM recipe WHERE id = ?")
        .addInt(id)
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            return new Recipe(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                Recipe.Difficulty.valueOf(rs.getString("difficulty").toUpperCase()),
                Recipe.DietaryTag.valueOf(rs.getString("dietary_tag").toUpperCase()),
                rs.getInt("duration"),
                rs.getString("imagePath"),
                rs.getString("instruction")
            );
          }
          return null;
        });
  }

  public Recipe[] retrieveAllRecipesWithIngredients(int @NotNull ... ingredientIds) {
    String query = "SELECT * FROM recipe WHERE id IN (" +
        "SELECT recipe_id FROM recipe_ingredient WHERE ingredient_id IN (" +
        String.join(",", Collections.nCopies(ingredientIds.length, "?")) +
        ") GROUP BY recipe_id HAVING COUNT(DISTINCT ingredient_id) = ?)";
    QueryBuilder queryBuilder = new QueryBuilder(query);
    for (int ingredientId : ingredientIds) {
      queryBuilder.addInt(ingredientId);
    }
    queryBuilder.addInt(ingredientIds.length);
    Recipe[] recipes = queryBuilder.executeQuerySafe(rs -> {
      List<Recipe> recipesForIngredient = new ArrayList<>();
      while (rs.next()) {
        recipesForIngredient.add(new Recipe(
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
      return recipesForIngredient.toArray(new Recipe[0]);
    });
    List<Recipe> allRecipes = new ArrayList<>(Arrays.asList(recipes));
    return allRecipes.toArray(new Recipe[0]);
  }
}