package no.ntnu.idatt1005.foodi.model.objects.dtos;

import org.jetbrains.annotations.NotNull;

/**
 * This class represents the ingredient object.
 *
 * @author Snake727
 * @version 0.2.0
 */
public class Ingredient {

  private final int id;
  private @NotNull String name;
  private @NotNull Unit unit;
  private @NotNull Category category;

  /**
   * Creates an ingredient object with the given id, name, unit and category.
   *
   * @param id       the id of the ingredient
   * @param name     the name of the ingredient
   * @param unit     the unit of the ingredient
   * @param category the category of the ingredient
   */
  public Ingredient(
      int id,
      @NotNull String name,
      @NotNull Unit unit,
      @NotNull Category category
  ) {
    this.id = id;
    setName(name);
    setUnit(unit);
    setCategory(category);
  }

  /**
   * Returns the id of the ingredient.
   *
   * @return the id of the ingredient
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the name of the ingredient.
   *
   * @return the name of the ingredient
   */
  public @NotNull String getName() {
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
  public @NotNull Unit getUnit() {
    return unit;
  }

  /**
   * Sets the unit of the ingredient.
   *
   * @param unit the new unit of the ingredient
   */
  public void setUnit(@NotNull Unit unit) {
    this.unit = unit;
  }

  /**
   * Returns the category of the ingredient.
   *
   * @return the category of the ingredient
   */
  public @NotNull Category getCategory() {
    return category;
  }

  /**
   * Sets the category of the ingredient.
   *
   * @param category the new category of the ingredient
   */
  public void setCategory(@NotNull Category category) {
    this.category = category;
  }

  /**
   * Enum for the different units that can be used in the application. The units are grams,
   * kilograms, liters, milliliters and pieces. Each unit has a corresponding string
   * representation.
   *
   * @author Henrik Kvamme
   * @version 1.1
   */
  public enum Unit {
    GRAM("GRAM", "g"),
    KILOGRAM("KILOGRAM", "kg"),
    LITER("LITER", "l"),
    MILLILITER("MILLILITER", "ml"),
    PIECE("PIECE", "pcs"),
    POUNDS("POUNDS", "lbs"),
    OUNCE("OUNCE", "oz"),
    GALLON("GALLON", "gal"),
    QUART("QUART", "qt"),
    PINT("PINT", "pt"),
    CUP("CUP", "cup"),
    TABLESPOON("TABLESPOON", "tbsp"),
    TEASPOON("TEASPOON", "tsp");

    private final @NotNull String unit;
    private final @NotNull String databaseKey;

    Unit(@NotNull String databaseKey, @NotNull String unit) {
      this.unit = unit;
      this.databaseKey = databaseKey;
    }

    public @NotNull String getUnit() {
      return unit;
    }

    public @NotNull String getDatabaseKey() {
      return databaseKey;
    }

    public static @NotNull Unit fromKey(@NotNull String key) {
      for (Unit unit : Unit.values()) {
        if (unit.getDatabaseKey().equals(key)) {
          return unit;
        }
      }
      throw new IllegalArgumentException("No unit found for key: " + key);
    }

    public @NotNull String toString() {
      return databaseKey;
    }
  }


  /**
   * Enum for the different categories an ingredient can belong to.
   *
   * @author Henrik Kvamme
   * @version 1.1
   */
  public enum Category {
    DAIRY("DAIRY", "Dairy", "🥛"),
    MEAT("MEAT", "Meat", "🥩"),
    VEGETABLE("VEGETABLE", "Vegetable", "🥦"),
    FRUIT("FRUIT", "Fruit", "🍎"),
    GRAIN("GRAIN", "Grain", "🌾"),
    SPICE("SPICE", "Spice", "🌶"),
    SAUCE("SAUCE", "Sauce", "🍅"),
    SWEET("SWEET", "Sweet", "🍬"),
    BEVERAGE("BEVERAGE", "Beverage", "🥤"),
    POULTRY("POULTRY", "Poultry", "🍗"),
    FISH("FISH", "Fish", "🐟"),
    CRUSTACEAN("CRUSTACEAN", "Crustacean", "🦐");

    private final @NotNull String databaseKey;
    private final @NotNull String category;
    private final @NotNull String icon;

    Category(@NotNull String databaseKey, @NotNull String category, @NotNull String icon) {
      this.databaseKey = databaseKey;
      this.category = category;
      this.icon = icon;
    }

    public @NotNull String getDatabaseKey() {
      return databaseKey;
    }

    public @NotNull String getCategory() {
      return category;
    }

    public @NotNull String getIcon() {
      return icon;
    }

    public static @NotNull Category fromKey(@NotNull String key) {
      for (Category category : Category.values()) {
        if (category.getDatabaseKey().equals(key)) {
          return category;
        }
      }
      throw new IllegalArgumentException("No category found for key: " + key);
    }

    public @NotNull String toString() {
      return databaseKey;
    }
  }

  /**
   * Returns a string representation of the ingredient.
   *
   * @return a string representation of the ingredient.
   */
  @Override
  public @NotNull String toString() {
    return String.format(
        "Ingredient{id=%o, name='%s', unit=%s, category=%s}",
        getId(),
        getName(),
        getUnit(),
        getCategory()
    );
  }
}