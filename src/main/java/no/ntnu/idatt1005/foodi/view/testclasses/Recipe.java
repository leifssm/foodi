package no.ntnu.idatt1002.view.testclasses;

/**
 * temp data class, will be replaced with actual data from the database.
 */
public class Recipe {
  private final String name;
  private final int servings;
  private final InventoryItem[] ingredients;
  public Recipe(String name, int servings, InventoryItem[] ingredients) {
    this.name = name;
    this.servings = servings;
    this.ingredients = ingredients;
  }

  public int getServings() {
    return servings;
  }

  public InventoryItem[] getIngredients() {
    return ingredients;
  }

  public String getName() {
    return name;
  }
}
