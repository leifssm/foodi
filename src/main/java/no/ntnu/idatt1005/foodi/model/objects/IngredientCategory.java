package no.ntnu.idatt1005.foodi.model.objects;

/**
 * Enum for the different categories an ingredient can belong to.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public enum IngredientCategory {
  DAIRY("Dairy"),
  MEAT("Meat"),
  VEGETABLE("Vegetable"),
  FRUIT("Fruit"),
  GRAIN("Grain"),
  SPICE("Spice"),
  SAUCE("Sauce"),
  SWEET("Sweet"),
  BEVERAGE("Beverage"),
  POULTRY("Poultry"),
  FISH("Fish"),
  CRUSTACEAN("Crustacean");

  private final String category;

  IngredientCategory(String category) {
    this.category = category;
  }

  public String getCategory() {
    return category;
  }
}
