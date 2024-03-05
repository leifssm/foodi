package no.ntnu.idatt1002.demo.model;

/**
 * This class represents the ingredient object.
 *
 * @version 0.1.0
 * @author Snake727
 */

public class Ingredient {
  private int id;
  private String name;
  private IngredientUnit unit;
  private IngredientCategory category;

  public Ingredient (int id, String name, IngredientUnit unit, IngredientCategory category) {
    this.id = id;
    this.name = name;
    this.unit = unit;
    this.category = category;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public IngredientUnit getUnit() {
    return unit;
  }

  public IngredientCategory getCategory() {
    return category;
  }

  public enum IngredientUnit {
    GRAM, KILOGRAM, LITER, MILLILITER, PIECE, POUNDS, OUNCE, GALLON, QUART, PINT, CUP, TABLESPOON, TEASPOON
  }

  public enum IngredientCategory {
    DAIRY, MEAT, VEGETABLE, FRUIT, GRAIN, SPICE, SAUCE, SWEET, BEVERAGE
  }
}
