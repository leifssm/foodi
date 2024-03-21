package no.ntnu.idatt1005.foodi.model.objects;

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
   * @throws IllegalArgumentException if name is null or empty
   */
  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }
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
   * @throws IllegalArgumentException if unit is null
   */
  public void setUnit(IngredientUnit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null");
    }
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
   * @throws IllegalArgumentException if category is null
   */
  public void setCategory(IngredientCategory category) {
    if (category == null) {
      throw new IllegalArgumentException("Category cannot be null");
    }
    this.category = category;
  }


  public enum IngredientUnit {
    GRAM, KILOGRAM, LITER, MILLILITER, PIECE, POUNDS, OUNCE, GALLON, QUART, PINT, CUP, TABLESPOON, TEASPOON
  }

  //evt. legge til legumes, nuts, seeds, oil, vinegar, alcohol, etc. - kort sagt revidering av kategorier
  public enum IngredientCategory {
    DAIRY, MEAT, VEGETABLE, FRUIT, GRAIN, SPICE, SAUCE, SWEET, BEVERAGE
  }

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