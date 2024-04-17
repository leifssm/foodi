package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import java.util.Date;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;

/**
 * Temp data class, will be replaced with actual data from the database.
 */
public class InventoryItem {

  private final String type;
  private final String name;
  private final Date expiryDate;
  private final String category;
  private final String quantity;
  private final String unit;

  /*
  public InventoryItem() {
    this.type = "🍗";
    this.name = "Chicken";
    this.expiryDate = new Date(
        System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 14 - (int) (1000 * 60 * 60 * 24 * 16
            * Math.random()));
    this.category = "Meat";
    this.quantity = "100";
    this.unit = "g";
  }
  */

  //funskjonell Item kode
  public InventoryItem(String name, Date expiryDate, String category, String quantity,
      String unit) {
    this.type = "🍗";
    this.name = name;
    this.expiryDate = expiryDate;
    this.category = category;
    this.quantity = quantity;
    this.unit = unit;
  }


  /**
   * Constructor for the InventoryItem class. Ideally the Inventory view should be refactored to
   * just use the ExpiringIngredient class directly.
   *
   * @param expiringIngredient The expiring ingredient to display
   */
  public InventoryItem(ExpiringIngredient expiringIngredient) {
    this.type = "🍗";
    this.name = expiringIngredient.getName();
    this.expiryDate = expiringIngredient.getExpirationDateAsDate();
    this.category = expiringIngredient.getCategory().toString();
    this.quantity = Double.toString(expiringIngredient.getAmount());
    this.unit = expiringIngredient.getUnit().toString();
  }


  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public Date getExpiryDate() {
    return expiryDate;
  }

  public String getCategory() {
    return category;
  }

  public String getQuantity() {
    return quantity;
  }

  public String getUnit() {
    return unit;
  }
}
