package no.ntnu.idatt1005.foodi.util;

import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.DB_URL;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.PASS;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.USER;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DatabaseTestMainTest {

  private DatabaseMain databaseMain;

  @BeforeEach
  public void setUp() {
    databaseMain = new DatabaseMain();
    String deleteInventorySql = "DELETE FROM inventory";
    String deleteShoppingListSql = "DELETE FROM shopping_list";
    String deleteUserSql = "DELETE FROM MAIN.PUBLIC.\"user\"";
    String deleteRecipe_IngredientSql = "DELETE FROM recipe_ingredient";
    String deleteIngredientSql = "DELETE FROM ingredient";
    String deleteRecipeSql = "DELETE FROM recipe";

    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(deleteInventorySql);
      statement.executeUpdate(deleteShoppingListSql);
      statement.executeUpdate(deleteUserSql);
      statement.executeUpdate(deleteRecipe_IngredientSql);
      statement.executeUpdate(deleteIngredientSql);
      statement.executeUpdate(deleteRecipeSql);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testInitializeDatabaseMain() {
    assertDoesNotThrow(() -> databaseMain.initializeDatabaseMain());
  }
}