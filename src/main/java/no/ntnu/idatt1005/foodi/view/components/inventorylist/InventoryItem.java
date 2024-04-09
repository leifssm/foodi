package no.ntnu.idatt1002.view.components.inventorylist;

import java.util.Date;

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
