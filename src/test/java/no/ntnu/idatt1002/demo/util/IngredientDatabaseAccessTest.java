package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.DAO.IngredientDatabaseAccess;
import no.ntnu.idatt1002.demo.model.objects.Ingredient;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class IngredientDatabaseAccessTest {
  private static IngredientDatabaseAccess ingredientDA;
  private static Ingredient testIngredient1;
  private static Ingredient testIngredient2;



  @BeforeAll
  public static void setUp() throws SQLException {
    // Initialize the database if not already initialized

    // Initialize a new IngredientDatabaseAccess object and two Ingredient objects
    ingredientDA = new IngredientDatabaseAccess();
    testIngredient1 = new Ingredient(5, "Carrot", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
    testIngredient2 = new Ingredient(6, "Potato", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
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