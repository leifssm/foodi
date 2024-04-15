package no.ntnu.idatt1005.foodi.model.objects;

/**
 * This class represents the recipe_ingredient object. Its responsibility
 * is to keep track of the ingredients and their amounts in a recipe.
 *
 * @version 0.3.0
 * @author Snake727
 */
public class RecipeIngredient {
  private Recipe recipe;
  private Ingredient ingredient;
  private double amount;

  public RecipeIngredient(Recipe recipe, Ingredient ingredient, double amount) {
    this.recipe = recipe;
    this.ingredient = ingredient;
    this.amount = amount;
  }

  public Recipe getRecipe() {
    return recipe;
  }

  public Ingredient getIngredient() {
    return ingredient;
  }

  public double getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "Recipe_Ingredient{" +
            "recipe = " + recipe +
            ", ingredient = " + ingredient +
            ", amount = " + amount +
            '}';
  }

  // main method that tests the toString method
  public static void main(String[] args) {
    Recipe recipe = new Recipe(1, "Pasta", "Pasta with tomato sauce", Recipe.Difficulty.MEDIUM, Recipe.DietaryTag.VEGAN, 30);
    Ingredient ingredient = new Ingredient(1, "Pasta", Ingredient.IngredientUnit.GRAM, Ingredient.IngredientCategory.GRAIN);
    RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingredient, 200);
    System.out.println(recipeIngredient.toString());
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }
}
