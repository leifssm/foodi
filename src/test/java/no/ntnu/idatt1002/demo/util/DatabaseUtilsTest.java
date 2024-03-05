package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.util.DatabaseUtils;
import org.junit.jupiter.api.Test;
import no.ntnu.idatt1002.demo.model.Ingredient;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class represents the test class for the DatabaseUtils class
 *
 * @version 0.1.0
 * @author Snake727
 */

public class DatabaseUtilsTest {

      @Test
      public void testInitializeDatabase() {
            // Create an instance of DatabaseUtils
            DatabaseUtils dbUtils = new DatabaseUtils();

            // Call the initializeDatabase method on the instance
            dbUtils.initializeDatabase();

            // Check if the database is initialized by inserting a new ingredient into the database
            // and then checking if the ingredient is in the database

            // Create an instance of Ingredient
            Ingredient ingredient = new Ingredient(1, "Apple", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.FRUIT);

            // Insert the ingredient into the database
            dbUtils.insertIngredient(ingredient);






      }
}