package no.ntnu.idatt1002.demo.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import no.ntnu.idatt1002.demo.model.DAO.IngredientDatabaseAccess;
import no.ntnu.idatt1002.demo.model.DAO.InventoryDatabaseAccess;
import no.ntnu.idatt1002.demo.model.objects.Ingredient;
import no.ntnu.idatt1002.demo.model.objects.Inventory;
import org.junit.jupiter.api.*;

public class InventoryDatabaseAccessTest {

    private static InventoryDatabaseAccess inventoryDA;

    private static Inventory inventory1;

    private static Inventory inventory2;

  private static IngredientDatabaseAccess ingredientDA;

  private static Ingredient testIngredient1;
  private static Ingredient testIngredient2;
  @BeforeAll
  public static void setUp() throws SQLException {

      ingredientDA = new IngredientDatabaseAccess();
      testIngredient1 = new Ingredient(1, "Carrot", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
      testIngredient2 = new Ingredient(2, "Potato", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);

        // Konverter strengen til et LocalDate-objekt
        LocalDate localDate = LocalDate.of(2004, 10, 14);

        // Konverter LocalDate til java.sql.Date
        Date sqlDate = Date.valueOf(localDate);

      inventoryDA = new InventoryDatabaseAccess();
      inventory1 = new Inventory(1, 1, 7, sqlDate, 1);
      inventory2 = new Inventory(2, 2, 2, sqlDate, 2);


  }

  @Test
  public void testSave() throws SQLException {


      //ingredientDA.save(testIngredient1);
      //ingredientDA.save(testIngredient2);
      inventoryDA.save(inventory1);
      Inventory savedInventory = inventoryDA.retrieve(inventory1);
      System.out.println(inventoryDA);
      System.out.println(savedInventory);
  }





}