package no.ntnu.idatt1002.demo.data;

import java.util.Date;

/**
 * This class represents the ingredient object.
 *
 * @version 0.1.0
 * @author Snake727
 */


public class Ingredient {
  private String name;
  private double quantity;
  private String unit;
  private Date expiration;
  private String category;

  private Ingredient(String name,
                     double quantity,
                     String unit,
                     Date expiration,
                     String category) {
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.expiration = expiration;
    this.category = category;
  }

  private Ingredient(String name,
                     double quantity,
                     String unit,
                     String category) {
    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
    this.category = category;
  }

  // Get and set methods for every attribute
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getQuantity() {
    return quantity;
  }

  public void setQuantity(double quantity) {
    this.quantity = quantity;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Date getExpiration() {
    return expiration;
  }

  public void setExpiration(Date expiration) {
    this.expiration = expiration;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * This method creates a string representation of the ingredient object.
   *
   * @return a string representation of the ingredient object
   */
  public String toString() {
    return "Name: "
        + name
        + ", Quantity: "
        + quantity
        + " "
        + unit
        + ", Expiration: "
        + expiration
        + ", Category: " + category;
  }
}
