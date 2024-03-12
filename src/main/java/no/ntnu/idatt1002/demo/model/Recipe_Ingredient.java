package no.ntnu.idatt1002.demo.model;

/**
 * This class represents the recipe_ingredient object. Its responsibility
 * is to keep track of the ingredients and their amounts in a recipe.
 *
 * @version 0.1.0
 * @author Snake727
 */
public class Recipe_Ingredient {
  private int recipe_id;
  private int ingredient_id;
  private double amount;

  public Recipe_Ingredient(int recipe_id, int ingredient_id, double amount) {
    this.recipe_id = recipe_id;
    this.ingredient_id = ingredient_id;
    this.amount = amount;
  }

  public int getRecipeId() {
    return recipe_id;
  }

  public int getIngredientId() {
    return ingredient_id;
  }

  public double getAmount() {
    return amount;
  }
}
