package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientDAOTest {
  private static IngredientDAO ingredientDAO;
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


    // Initialize a new IngredientDatabaseAccess object and two Ingredient objects
    testIngredient1 = new Ingredient(5, "Carrot", Ingredient.Unit.PIECE, Ingredient.Category.VEGETABLE);
    testIngredient2 = new Ingredient(6, "Potato", Ingredient.Unit.PIECE, Ingredient.Category.VEGETABLE);


  }

  @Test
  void testSaveIngredientObject() {
    ingredientDAO.saveIngredientObject(testIngredient1);
    Ingredient retrievedIngredient = ingredientDAO.retrieveIngredient(testIngredient1);
    assertEquals(testIngredient1.toString(), retrievedIngredient.toString());
  }

  @Test
  public void testUpdateIngredient() {
    testIngredient1.setName("Updated Ingredient");
    ingredientDAO.updateIngredient(testIngredient2);
    Ingredient updatedIngredient = ingredientDAO.retrieveIngredient(testIngredient1);
    assertEquals(testIngredient1.toString(), updatedIngredient.toString());
  }

  @Test
  public void testDeleteIngredient() {
    ingredientDAO.deleteIngredient(testIngredient1);
    assertNull(ingredientDAO.retrieveIngredient(testIngredient1));
  }

  @Test
  public void testSaveIngredientToUserInventory() throws Exception {
    ingredientDAO.saveIngredientToUserInventory(1, "Test Ingredient", Ingredient.Unit.GRAM, Ingredient.Category.MEAT, 100.0, Date.valueOf(LocalDate.now()));
    double totalAmount = ingredientDAO.getTotalAmountOfIngredientsInInventory(1);
    assertTrue(totalAmount >= 100.0);
  }

  @Test
  public void testUpdateIngredientInUserInventory() {
    ingredientDAO.updateIngredientInUserInventory(1, testIngredient1.getId(), 200.0, LocalDate.now());
    double totalAmount = ingredientDAO.getTotalAmountOfIngredientsInInventory(1);
    assertTrue(totalAmount >= 200.0);
  }

  @Test
  public void testDeleteIngredientFromUserInventory() {
    ingredientDAO.deleteIngredientFromUserInventory(1, testIngredient1.getId());
    double totalAmount = ingredientDAO.getTotalAmountOfIngredientsInInventory(1);
    assertTrue(totalAmount < 200.0);
  }
}