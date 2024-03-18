package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.DAO.IngredientDatabaseAccess;
import no.ntnu.idatt1002.demo.model.DAO.ShoppingListDAO;
import no.ntnu.idatt1002.demo.model.DAO.UserDatabaseAccess;
import no.ntnu.idatt1002.demo.model.objects.Ingredient;
import no.ntnu.idatt1002.demo.model.objects.ShoppingList;
import no.ntnu.idatt1002.demo.model.objects.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static no.ntnu.idatt1002.demo.model.repository.Database.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingListTest {

    private static ShoppingListDAO shoppingListDAO;
    private static ShoppingList shoppingList_item1;

    private static ShoppingList shoppingList_item2;

    private static IngredientDatabaseAccess ingredientDA;

    private static UserDatabaseAccess userDA;


    private static User user1;

    private static User user2;

    private static Ingredient ingredient1;

    private static Ingredient ingredient2;
    @BeforeAll
    public static void setUp() throws SQLException {

        String sql = "DELETE FROM shopping_list";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        ingredientDA = new IngredientDatabaseAccess();
        ingredient1 = new Ingredient(5, "Kahawa Bønner", Ingredient.IngredientUnit.LITER, Ingredient.IngredientCategory.GRAIN);
        ingredient2 = new Ingredient(6, "Melk", Ingredient.IngredientUnit.LITER, Ingredient.IngredientCategory.DAIRY);
        ingredientDA.save(ingredient1);
        ingredientDA.save(ingredient2);

        user1 = new User(4, "Kari");
        user2 = new User(5, "Per");
        userDA = new UserDatabaseAccess();
        userDA.save(user1);
        userDA.save(user2);

        shoppingList_item1 = new ShoppingList(1, 5, 2, 4);

        shoppingList_item2 = new ShoppingList(2, 6, 3, 5);


        shoppingListDAO = new ShoppingListDAO();

    }
    @Nested
    @DisplayName("Testing ShoppingListDAO")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ShoppingListDAOTest {

        @Test
        @Order(1)
        public void saveShoppingList() throws SQLException {

            Map<Integer, Double> shoppingList = new HashMap<>();
            shoppingList.put(5, 2.0);
            shoppingList.put(6, 3.0);
            shoppingListDAO.save(shoppingList, user1.getUserId(), 1);

            String sql = "SELECT * FROM shopping_list WHERE user_id = ?";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, user1.getUserId());
                ResultSet rs = statement.executeQuery();
                rs.next();
                assertEquals(5, rs.getInt("ingredient_id"));
                assertEquals(2.0, rs.getDouble("amount"));
                rs.next();
                assertEquals(6, rs.getInt("ingredient_id"));
                assertEquals(3.0, rs.getDouble("amount"));
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Test
        @Order(2)
        public void DeleteShoppingList() {

            //number of shopping list items before deleting with sql query

            String number_of_entries = "SELECT COUNT(*) FROM shopping_list";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(number_of_entries);
                rs.next();
                int number_of_entries_before_deletion = rs.getInt(1);
                assertEquals(2, number_of_entries_before_deletion);
                shoppingListDAO.deleteAllForUser(user1.getUserId());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(number_of_entries);
                rs.next();
                int number_of_entries_after_deletion = rs.getInt(1);
                assertEquals(1, number_of_entries_after_deletion);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }



}
