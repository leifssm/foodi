package no.ntnu.idatt1005.foodi.controller;

import java.sql.SQLException;
import java.util.Map;
import no.ntnu.idatt1005.foodi.model.DAO.ShoppingListDAO;

/**
 * This class is responsible for handling the usage of database operations regarding stored shopping
 * lists in the frontend.
 *
 * @author Snake727
 * @version 0.1.0
 */

public class ShoppingListController {

  private final ShoppingListDAO shoppingListDAO;

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
