package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.util.List;

/**
 * A list of recipes with partially removed ingredients.
 */
public class ShoppingList {

  private final int shoppingListId;
  private List<RecipeWithPartiallyRemovedIngredients> recipes;

  /**
   * Constructor for a shopping list.
   *
   * @param shoppingListId the id of the shopping list
   * @param recipes        a list of recipes with partially removed ingredients
   */
  public ShoppingList(int shoppingListId, List<RecipeWithPartiallyRemovedIngredients> recipes) {
    this.shoppingListId = shoppingListId;
    setRecipes(recipes);
  }

  /**
   * Returns the id of the shopping list.
   *
   * @return the id of the shopping list
   */
  public int getShoppingListId() {
    return shoppingListId;
  }

  /**
   * Returns an immutable list of recipes with partially removed ingredients.
   *
   * @return an immutable list of recipes with partially removed ingredients
   */
  public List<RecipeWithPartiallyRemovedIngredients> getRecipes() {
    return recipes;
  }

  /**
   * Sets the recipes of the shopping list.
   *
   * @param recipes a list of recipes with partially removed ingredients.
   */
  public void setRecipes(List<RecipeWithPartiallyRemovedIngredients> recipes) {
    this.recipes = List.copyOf(recipes);
  }
}
