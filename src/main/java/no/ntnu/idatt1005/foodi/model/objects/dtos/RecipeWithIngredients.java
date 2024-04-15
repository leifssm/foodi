package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.util.List;

public class RecipeWithIngredients extends Recipe {

  private List<AmountedIngredient> ingredients;

  public RecipeWithIngredients(int id, String name, String description, Difficulty difficulty,
      DietaryTag dietaryTag, int duration, List<AmountedIngredient> ingredients) {
    super(id, name, description, difficulty, dietaryTag, duration);
    setIngredients(ingredients);
  }

  public List<AmountedIngredient> getIngredients() {
    return List.copyOf(ingredients);
  }

  public void setIngredients(List<AmountedIngredient> ingredients) {
    this.ingredients = List.copyOf(ingredients);
  }
}
