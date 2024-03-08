package no.ntnu.idatt1002.demo.util;

import static no.ntnu.idatt1002.demo.model.DAO.InventoryDatabaseAccess.*;
import static no.ntnu.idatt1002.demo.model.repository.Database.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.time.LocalDate;

import no.ntnu.idatt1002.demo.model.DAO.IngredientDatabaseAccess;
import no.ntnu.idatt1002.demo.model.DAO.InventoryDatabaseAccess;
import no.ntnu.idatt1002.demo.model.DAO.UserDatabaseAccess;
import no.ntnu.idatt1002.demo.model.objects.Ingredient;
import no.ntnu.idatt1002.demo.model.objects.Inventory;
import no.ntnu.idatt1002.demo.model.objects.User;
import org.junit.jupiter.api.*;

public class InventoryDatabaseAccessTest {

    private static UserDatabaseAccess userDA;
    private static InventoryDatabaseAccess inventoryDA;

    private static Inventory inventory1;

    private static Inventory inventory2;

  private static IngredientDatabaseAccess ingredientDA;

  private static Ingredient testIngredient1;
  private static Ingredient testIngredient2;
  @BeforeAll
  public static void setUp() throws SQLException {

      userDA = new UserDatabaseAccess();
      User testUser = new User(1, "Ola");
      userDA.save(testUser);

      ingredientDA = new IngredientDatabaseAccess();
      testIngredient1 = new Ingredient(1, "Carrot", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
      testIngredient2 = new Ingredient(2, "Potato", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);

        // Konverter strengen til et LocalDate-objekt
        LocalDate localDate = LocalDate.of(2004, 10, 14);

        // Konverter LocalDate til java.sql.Date
        Date sqlDate = Date.valueOf(localDate);

      inventoryDA = new InventoryDatabaseAccess();
      inventory1 = new Inventory(1, 1, 7, sqlDate, 1);
      inventory2 = new Inventory(2, 2, 2, sqlDate, 1);


  }

  // for meg selv, for å se hva som er der,
    @Test
    public void currentTable() throws SQLException {
        String checkSql = "SELECT * FROM inventory";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int ingredient_id = rs.getInt("ingredient_id");
                int amount = rs.getInt("amount");
                Date expiration_date = rs.getDate("expiration_date");
                int user_id = rs.getInt("user_id");

                System.out.println("ID: " + id + ", Ingredient ID: " + ingredient_id + ", Amount: " + amount + ", Expiration Date: " + expiration_date + ", User ID: " + user_id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

  @Test
  public void delete() throws SQLException {


  }

    @Test
    public void deleteAllOfIngredient() throws SQLException {

    }






}