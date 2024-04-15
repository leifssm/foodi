package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.util.List;

public class RecipeWithPartiallyRemovedIngredients extends Recipe {

  private List<PartiallyRemovedAmountedIngredient> ingredients;

  public RecipeWithPartiallyRemovedIngredients(
      int id,
      String name,
      String description,
      Difficulty difficulty,
      DietaryTag dietaryTag,
      int duration,
      List<PartiallyRemovedAmountedIngredient> ingredients
  ) {
    super(id, name, description, difficulty, dietaryTag, duration);
    setIngredients(ingredients);
  }

  public List<PartiallyRemovedAmountedIngredient> getIngredients() {
    return List.copyOf(ingredients);
  }

  public void setIngredients(List<PartiallyRemovedAmountedIngredient> ingredients) {
    this.ingredients = List.copyOf(ingredients);
  }
}
