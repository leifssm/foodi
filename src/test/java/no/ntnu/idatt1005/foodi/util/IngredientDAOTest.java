package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientDAOTest {
  private static IngredientDAO ingredientDA;
  private static Ingredient testIngredient1;
  private static Ingredient testIngredient2;

  @BeforeAll
  public static void setUp() throws SQLException {
    // Initialize the database if not already initialized
    DatabaseMain databaseMain = new DatabaseMain();
    databaseMain.initializeDatabaseMain();

    String deleteInventorySql = "DELETE FROM inventory";
    String deleteShoppingListSql = "DELETE FROM shopping_list";
    String deleteUserSql = "DELETE FROM MAIN.PUBLIC.\"user\"";
    String deleteRecipe_IngredientSql = "DELETE FROM recipe_ingredient";
    String deleteIngredientSql = "DELETE FROM ingredient";
    String deleteRecipeSql = "DELETE FROM recipe";
    ;


    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
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


    // Initialize a new IngredientDatabaseAccess object and two Ingredient objects
    ingredientDA = new IngredientDAO();
    testIngredient1 = new Ingredient(5, "Carrot", Ingredient.Unit.PIECE, Ingredient.Category.VEGETABLE);
    testIngredient2 = new Ingredient(6, "Potato", Ingredient.Unit.PIECE, Ingredient.Category.VEGETABLE);



  }

  @Test
  public void testSave() throws SQLException {
    ingredientDA.save(testIngredient1);
    Ingredient savedIngredient = ingredientDA.retrieve(testIngredient1);
    assertEquals(testIngredient1.toString(), savedIngredient.toString());
  }

  @Test
  public void testUpdate() {
    testIngredient1.setName("Onion");
    ingredientDA.update(testIngredient1);
    Ingredient updatedIngredient = ingredientDA.retrieve(testIngredient1);
    assertEquals(testIngredient1.toString(), updatedIngredient.toString());
    ingredientDA.delete(testIngredient1);
  }

  @Test
  public void testDelete() throws SQLException {
    ingredientDA.save(testIngredient2);
    ingredientDA.delete(testIngredient2);
    assertNull(ingredientDA.retrieve(testIngredient2));
  }
}