package no.ntnu.idatt1005.foodi.model.objects;

/**
 * Enum for the different units that can be used in the application. The units are grams, kilograms,
 * liters, milliliters and pieces. Each unit has a corresponding string representation.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public enum IngredientUnit {
  GRAM("g"),
  KILOGRAM("kg"),
  LITER("l"),
  MILLILITER("ml"),
  PIECE("pcs"),
  POUNDS("lbs"),
  OUNCE("oz"),
  GALLON("gal"),
  QUART("qt"),
  PINT("pt"),
  CUP("cup"),
  TABLESPOON("tbsp"),
  TEASPOON("tsp");

  private final String unit;

  IngredientUnit(String unit) {
    this.unit = unit;
  }

  public String getUnit() {
    return unit;
  }
}
