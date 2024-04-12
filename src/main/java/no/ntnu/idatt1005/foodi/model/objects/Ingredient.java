package no.ntnu.idatt1005.foodi.model.objects;

import org.jetbrains.annotations.NotNull;

/**
 * This class represents the ingredient object.
 *
 * @version 0.2.0
 * @author Snake727
 */
public class Ingredient {
  private final int id;
  private String name;
  private IngredientUnit unit;
  private IngredientCategory category;

  public Ingredient (int id, String name, IngredientUnit unit, IngredientCategory category) {
    this.id = id;
    setName(name);
    setUnit(unit);
    setCategory(category);
  }

  public int getId() {
    return id;
  }

  /**
   * Returns the name of the ingredient.
   *
   * @return the name of the ingredient
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name of the ingredient.
   *
   * @param name the new name of the ingredient
   */
  public void setName(@NotNull String name) {
    this.name = name;
  }

  /**
   * Returns the unit of the ingredient.
   *
   * @return the unit of the ingredient
   */
  public IngredientUnit getUnit() {
    return unit;
  }

  /**
   * Sets the unit of the ingredient.
   *
   * @param unit the new unit of the ingredient
   */
  public void setUnit(@NotNull IngredientUnit unit) {
    this.unit = unit;
  }

  /**
   * Returns the category of the ingredient.
   *
   * @return the category of the ingredient
   */
  public IngredientCategory getCategory() {
    return category;
  }

  /**
   * Sets the category of the ingredient.
   *
   * @param category the new category of the ingredient
   */
  public void setCategory(@NotNull IngredientCategory category) {
    this.category = category;
  }


  /**
   * This enum represents the different units an ingredient can have.
   */
  public enum IngredientUnit {
    GRAM, KILOGRAM, LITER, MILLILITER, PIECE, POUNDS, OUNCE, GALLON, QUART, PINT, CUP, TABLESPOON, TEASPOON
  }

  /**
   * This enum represents the different categories an ingredient can have.
   * This can be updated as needed.
   */
  public enum IngredientCategory {
    DAIRY, MEAT, VEGETABLE, FRUIT, GRAIN, SPICE, SAUCE, SWEET, BEVERAGE, POULTRY, FISH, CRUSTACEAN
  }

  /**
   * Returns a string representation of the ingredient. This is useful for debugging.
   *
   * @return a string representation of the ingredient.
   */
  @Override
  public String toString() {
    return "Ingredient{" +
          "id=" + id +
          ", name='" + name + '\'' +
          ", unit=" + unit +
          ", category=" + category +
          '}';
  }
}