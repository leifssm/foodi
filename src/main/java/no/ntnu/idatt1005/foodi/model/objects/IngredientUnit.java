package no.ntnu.idatt1005.foodi.model.objects;

/**
 * Enum for the different units that can be used in the application. The units are grams, kilograms,
 * liters, milliliters and pieces. Each unit has a corresponding string representation.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public enum IngredientUnit {
  GRAMS("g"),
  KILOGRAMS("kg"),
  LITERS("l"),
  MILLILITERS("ml"),
  PIECES("pcs");

  private final String unitAbbreviation;

  IngredientUnit(String unit) {
    this.unitAbbreviation = unit;
  }

  public String getUnitAbbreviation() {
    return unitAbbreviation;
  }
}
