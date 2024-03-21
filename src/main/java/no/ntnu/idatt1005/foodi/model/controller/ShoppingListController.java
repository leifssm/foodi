package no.ntnu.idatt1005.foodi.model.controller;

import no.ntnu.idatt1005.foodi.model.DAO.ShoppingListDAO;
import no.ntnu.idatt1005.foodi.model.objects.ShoppingList;

import java.sql.SQLException;

/**
 * This class is responsible for handling the usage of
 * database operations regarding stored shopping lists in the frontend.
 *
 * @version 0.1.0
 * @author Snake727
 */

public class ShoppingListController {
  private ShoppingListDAO shoppingListDAO;

  public ShoppingListController() {
    shoppingListDAO = new ShoppingListDAO();
  }

  public void save(ShoppingList shoppingList) {
    try {
      shoppingListDAO.save(shoppingList);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ShoppingList retrieve(ShoppingList shoppingList) {
      return shoppingListDAO.retrieve(shoppingList);
  }

  public void delete(ShoppingList shoppingList) {
      shoppingListDAO.delete(shoppingList);
  }
}
