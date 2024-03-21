package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.model.DAO.InventoryDAO;
import no.ntnu.idatt1005.foodi.model.objects.Inventory;
import no.ntnu.idatt1005.foodi.model.objects.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.User;

import java.sql.Date;
import java.sql.SQLException;

/**
 * This class is responsible for handling the usage of
 * database operations regarding stored inventory in the frontend.
 *
 * @version 0.2.0
 * @author Snake727
 */

public class InventoryController {
  private InventoryDAO inventoryDAO;

  public InventoryController() {
    inventoryDAO = new InventoryDAO();
  }

  public void save(Inventory inventory, Ingredient ingredient, User user) {
    try {
      inventoryDAO.save(inventory, ingredient, user);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Inventory retrieve(Inventory inventory, Ingredient ingredient, User user) {
      return inventoryDAO.retrieve(inventory, ingredient, user);
  }

  public void delete(Inventory inventory) {
      inventoryDAO.delete_inventory(inventory);
  }

  public void delete_ingredient(int inventory_id, int ingredient_id) {
      inventoryDAO.delete_ingredient(inventory_id, ingredient_id);
  }

  public void update_amount_of_ingredient(Inventory inventory, int amount, int ingredient_id) {
      inventoryDAO.update_amount_of_ingredient(inventory, amount, ingredient_id);
  }

  public void update_expiration_date(Inventory inventory, Date expiration_date, int ingredient_id) {
      inventoryDAO.update_expiration_date(inventory, expiration_date, ingredient_id);
  }
}
