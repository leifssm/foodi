package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.util.List;

/**
 * This class represents a recipe with added ingredients.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class RecipeWithIngredients extends Recipe {

  private List<AmountedIngredient> ingredients;

  /**
   * Constructor for a recipe with ingredients.
   *
   * @param id          the id of the recipe
   * @param name        the name of the recipe
   * @param description the description of the recipe
   * @param difficulty  the difficulty of the recipe
   * @param dietaryTag  the dietary tag of the recipe
   * @param duration    the duration of the recipe in minutes
   * @param ingredients a list of ingredients
   */
  public RecipeWithIngredients(
      int id,
      String name,
      String description,
      Difficulty difficulty,
      DietaryTag dietaryTag,
      int duration,
      List<AmountedIngredient> ingredients,
      String imagePath,
      String instruction
  ) {
    super(id, name, description, difficulty, dietaryTag, duration, imagePath, instruction);
    setIngredients(ingredients);
  }

  /**
   * Returns an immutable list of ingredients.
   *
   * @return an immutable list of ingredients
   */
  public List<AmountedIngredient> getIngredients() {
    return ingredients;
  }

  /**
   * Sets the ingredients of the recipe.
   *
   * @param ingredients a list of ingredients.
   */
  public void setIngredients(List<AmountedIngredient> ingredients) {
    this.ingredients = List.copyOf(ingredients);
  }
}
