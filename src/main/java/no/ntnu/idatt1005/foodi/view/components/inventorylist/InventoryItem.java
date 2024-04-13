package no.ntnu.idatt1005.foodi.view.components.inventorylist;

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
