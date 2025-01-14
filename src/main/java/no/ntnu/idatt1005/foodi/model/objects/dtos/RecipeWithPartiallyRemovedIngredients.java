package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.util.List;

/**
 * This class represents a recipe with partially removed ingredients. To be used with the shopping
 * list.
 */
public class RecipeWithPartiallyRemovedIngredients extends Recipe {

  private List<PartiallyRemovedAmountedIngredient> ingredients;
  private int portions;

  /**
   * Constructor for a recipe with partially removed ingredients.
   *
   * @param id          the id of the recipe
   * @param name        the name of the recipe
   * @param description the description of the recipe
   * @param difficulty  the difficulty of the recipe
   * @param dietaryTag  the dietary tag of the recipe
   * @param duration    the duration of the recipe in minutes
   * @param imagePath   the path to the image of the recipe
   * @param instruction the instruction of the recipe
   * @param ingredients a list of partially removed ingredients
   * @param portions    the number of portions the recipe should be multiplied by
   */
  public RecipeWithPartiallyRemovedIngredients(
      int id,
      String name,
      String description,
      Difficulty difficulty,
      DietaryTag dietaryTag,
      int duration,
      String imagePath,
      String instruction,
      List<PartiallyRemovedAmountedIngredient> ingredients,
      int portions
  ) {
    super(id, name, description, difficulty, dietaryTag, duration, imagePath, instruction);
    setIngredients(ingredients);
    setPortions(portions);
  }

  /**
   * Constructor for extending a recipe with partially removed ingredients.
   *
   * @param recipe      the recipe to extend
   * @param ingredients a list of partially removed ingredients
   * @param portions    the number of portions
   */
  public RecipeWithPartiallyRemovedIngredients(
      RecipeWithIngredients recipe,
      List<PartiallyRemovedAmountedIngredient> ingredients,
      int portions
  ) {
    super(
        recipe.getId(),
        recipe.getName(),
        recipe.getDescription(),
        recipe.getDifficulty(),
        recipe.getDietaryTag(),
        recipe.getDuration(),
        recipe.getImagePath(),
        recipe.getInstruction()
    );
    setIngredients(ingredients);
    setPortions(portions);
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

  /**
   * Returns the number of portions the recipe should be multiplied by.
   *
   * @return the number of portions
   */
  public int getPortions() {
    return portions;
  }

  /**
   * Sets the number of portions recipe should be multiplied by.
   *
   * @param portions the number of portions
   */
  public void setPortions(int portions) {
    if (portions < 1) {
      throw new IllegalArgumentException("Error: Portions cannot be less than 1.");
    }
    this.portions = portions;
  }
}
