package no.ntnu.idatt1005.foodi.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * This class is responsible for handling the usage of database operations regarding shopping
 * lists.
 *
 * @author Snake727
 * @version 0.8.0
 */
public class ShoppingListDAO {

  /**
   * Saves a shopping list to the database.
   *
   * @param shoppingList the shopping list to be saved.
   * @param userId       the id of the user.
   * @param listId       the id of the shopping list.
   * @throws SQLException if an error occurs while saving the shopping list.
   */
  public void save(@NotNull Map<Integer, Double> shoppingList, int userId, int listId)
      throws SQLException {
    // Delete existing entries for the user to avoid duplicates
    new QueryBuilder("DELETE FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeUpdateSafe();

    int itemNr = 1;
    for (Map.Entry<Integer, Double> entry : shoppingList.entrySet()) {
      new QueryBuilder(
          "INSERT INTO shopping_list "
              + "(SHOPPINGLIST_ID, ITEM_ID, INGREDIENT_ID, AMOUNT, USER_ID) VALUES (?, ?, ?, ?, ?)")
          .addInt(listId)
          .addInt(itemNr++)
          .addInt(entry.getKey())
          .addDouble(entry.getValue())
          .addInt(userId)
          .executeUpdateSafe();
    }
  }

  /**
   * Deletes all shopping list entries for a user.
   *
   * @param userId the id of the user.
   */
  public void deleteAllForUser(int userId) {
    new QueryBuilder("DELETE FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeUpdateSafe();
  }

  /**
   * Retrieves the shopping list for a user.
   *
   * @param userId the id of the user.
   * @return a map of ingredient ids and their amounts.
   */
  public Map<Integer, Double> getShoppingListForUser(int userId) {
    Map<Integer, Double> currentShoppingList = new HashMap<>();

    new QueryBuilder("SELECT * FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeQuerySafe(rs -> {
          while (rs.next()) {
            current_shoppingList.put(rs.getInt("ingredient_id"), rs.getDouble("amount"));
          }
          return current_shoppingList;
        });

    return current_shoppingList;
  }
}