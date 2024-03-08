package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.DAO.IngredientDatabaseAccess;
import no.ntnu.idatt1002.demo.model.DAO.InventoryDatabaseAccess;
import no.ntnu.idatt1002.demo.model.DAO.UserDatabaseAccess;
import no.ntnu.idatt1002.demo.model.objects.Ingredient;
import no.ntnu.idatt1002.demo.model.objects.Inventory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static no.ntnu.idatt1002.demo.model.repository.Database.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class the_Mother_Test {

    private static IngredientDatabaseAccess ingredientDA;

    private static Ingredient testIngredient1;

    private static Ingredient testIngredient2;

    private static UserDatabaseAccess userDA;
    private static InventoryDatabaseAccess inventoryDA;

    private static Inventory inventory1;

    private static Inventory inventory2;

    @BeforeAll
    public static void setUp(){

        String number_of_entries_ingredient = "Select COUNT(*) FROM ingredient";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(number_of_entries_ingredient);
            resultSet.next();
            int number_of_entries = resultSet.getInt(1);
            if (number_of_entries == 0){
                ingredientDA = new IngredientDatabaseAccess();
                testIngredient1 = new Ingredient(1, "Carrot", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
                testIngredient2 = new Ingredient(2, "Potato", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
                ingredientDA.save(testIngredient1);
                ingredientDA.save(testIngredient2);
            }
            else {
                System.out.println("There are already entries in the ingredient table");

                System.out.println("they are");

                String checkSql = "SELECT * FROM ingredient";
                try (PreparedStatement pstmt = connection.prepareStatement(checkSql)) {
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("name");
                        System.out.println("id: " + id + " name: " + name);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
      }
    } catch (SQLException e) {
            e.printStackTrace();

      }

        String number_of_entries_inventory = "Select COUNT(*) FROM inventory";

        String number_of_entries_user = "Select COUNT(*) FROM TEST.PUBLIC.\"user\"";

    }

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
}
