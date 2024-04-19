package no.ntnu.idatt1005.foodi.model.DAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShoppingListDAO {

  public void save(Map<Integer, Double> shoppingList, int userId, int listId) throws SQLException {
    // Delete existing entries for the user to avoid duplicates
    new QueryBuilder("DELETE FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeUpdateSafe();

    int item_nr = 1;
    for (Map.Entry<Integer, Double> entry : shoppingList.entrySet()) {
      new QueryBuilder(
          "INSERT INTO shopping_list (SHOPPINGLIST_ID, ITEM_ID, INGREDIENT_ID, AMOUNT, USER_ID) VALUES (?, ?, ?, ?, ?)")
          .addInt(listId)
          .addInt(item_nr++)
          .addInt(entry.getKey())
          .addDouble(entry.getValue())
          .addInt(userId)
          .executeUpdateSafe();
    }
  }

  public void deleteAllForUser(int userId) {
    new QueryBuilder("DELETE FROM shopping_list WHERE user_id = ?")
        .addInt(userId)
        .executeUpdateSafe();
  }

  public Map<Integer, Double> getShoppingListForUser(int userId) {
    Map<Integer, Double> current_shoppingList = new HashMap<>();

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