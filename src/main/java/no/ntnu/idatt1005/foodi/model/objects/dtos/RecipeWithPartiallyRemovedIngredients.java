package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents a recipe with partially removed ingredients. To be used with the shopping
 * list.
 */
public class RecipeWithPartiallyRemovedIngredients extends Recipe {

  private List<PartiallyRemovedAmountedIngredient> ingredients;

  /**
   * Constructor for a recipe with partially removed ingredients.
   *
   * @param id          the id of the recipe
   * @param name        the name of the recipe
   * @param description the description of the recipe
   * @param difficulty  the difficulty of the recipe
   * @param dietaryTag  the dietary tag of the recipe
   * @param duration    the duration of the recipe in minutes
   * @param ingredients a list of partially removed ingredients
   */
  public RecipeWithPartiallyRemovedIngredients(
      int id,
      String name,
      String description,
      Difficulty difficulty,
      DietaryTag dietaryTag,
      int duration,
      List<PartiallyRemovedAmountedIngredient> ingredients
      List<PartiallyRemovedAmountedIngredient> ingredients,
  ) {
    super(id, name, description, difficulty, dietaryTag, duration);
    setIngredients(ingredients);
  }

  /**
   * Returns an immutable list of partially removed ingredients.
   *
   * @return an immutable list of partially removed ingredients
   */
  public List<PartiallyRemovedAmountedIngredient> getIngredients() {
    return ingredients;
  }

  /**
   * Sets the partially removed ingredients of the recipe.
   *
   * @param ingredients a list of partially removed ingredients.
   */
  public void setIngredients(List<PartiallyRemovedAmountedIngredient> ingredients) {
    this.ingredients = List.copyOf(ingredients);
  }
}
