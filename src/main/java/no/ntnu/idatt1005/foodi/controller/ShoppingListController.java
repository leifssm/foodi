package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.model.DAO.ShoppingListDAO;
import no.ntnu.idatt1005.foodi.model.objects.ShoppingList;

import java.sql.SQLException;
import java.util.Map;

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

  public void save(Map<Integer, Double> shoppingList, int userId, int listId) {
    try {
      shoppingListDAO.save(shoppingList, userId, listId);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /* legger til metoder for det senere, etter start av mvp-sikring (kobling av frontend og backend)
  public ShoppingList retrieve(ShoppingList shoppingList) {
      return shoppingListDAO.retrieve(shoppingList);
  }

  public void delete(ShoppingList shoppingList) {
      shoppingListDAO.delete(shoppingList);
  }
  */

}
