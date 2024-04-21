package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithPartiallyRemovedIngredients;
import org.jetbrains.annotations.NotNull;

/**
 * Class for grouping ingredients.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class IngredientGrouper {

  /**
   * Groups ingredients by category.
   *
   * @param ingredients The ingredients to group
   * @return A list of ingredient category groups
   */
  static List<IngredientCategoryGroup> groupByCategory(
      List<AmountedIngredient> ingredients
  ) {
    ArrayList<AmountedIngredient> sortedIngredients = new ArrayList<>(ingredients);
    sortedIngredients.sort(Comparator.comparing(a -> a.getCategory().getName()));

    List<IngredientCategoryGroup> groups = new ArrayList<>();

    for (AmountedIngredient ingredient : sortedIngredients) {
      IngredientCategoryGroup currentGroup;
      if (
          groups.isEmpty() ||
              !groups.get(groups.size() - 1).name.equals(ingredient.getCategory())
      ) {
        currentGroup = new IngredientCategoryGroup(ingredient.getCategory(), new ArrayList<>());
        groups.add(currentGroup);
      } else {
        currentGroup = groups.get(groups.size() - 1);
      }
      currentGroup.ingredients.add(ingredient);
    }
    return groups;
  }

  /**
   * Joins ingredients from multiple recipes and adds up the total.
   *
   * @param recipes The recipes to join
   * @return A list of ingredients with the updated amount
   */
  static @NotNull List<AmountedIngredient> joinIngredientsFromRecipes(
      @NotNull List<@NotNull RecipeWithPartiallyRemovedIngredients> recipes
  ) {
    // A hashmap using the ingredient as key and the total amount as value
    HashMap<AmountedIngredient, Double> ingredients = new HashMap<>();

    // For each recipe, add the ingredients to the hashmap, and if it already exists, increase the
    // amount
    for (RecipeWithPartiallyRemovedIngredients recipe : recipes) {
      for (AmountedIngredient ingredient : recipe.getIngredients()) {
        ingredients.computeIfPresent(ingredient, (k, v) -> v + ingredient.getAmount());
        ingredients.putIfAbsent(ingredient, ingredient.getAmount());
      }
    }

    // Create a new AmountedIngredient with the same metadata but with the new amount
    List<AmountedIngredient> combinedIngredients = new ArrayList<>();
    for (var ingredient : ingredients.entrySet()) {
      AmountedIngredient copy = ingredient.getKey().copy();
      copy.setAmount(ingredient.getValue());
      combinedIngredients.add(copy);
    }

    combinedIngredients.sort(Comparator.comparing(AmountedIngredient::getCategory));

    return combinedIngredients;
  }

  /**
   * A record for grouping ingredients by category.
   *
   * @param name        The category grouped by
   * @param ingredients The ingredients in the category
   */
  public record IngredientCategoryGroup(
      @NotNull Ingredient.Category name,
      @NotNull ArrayList<AmountedIngredient> ingredients
  ) {

  }
}
