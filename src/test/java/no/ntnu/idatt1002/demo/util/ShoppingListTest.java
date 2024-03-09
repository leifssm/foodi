package no.ntnu.idatt1002.demo.util;

import no.ntnu.idatt1002.demo.model.DAO.IngredientDatabaseAccess;
import no.ntnu.idatt1002.demo.model.DAO.ShoppingListDAO;
import no.ntnu.idatt1002.demo.model.DAO.UserDatabaseAccess;
import no.ntnu.idatt1002.demo.model.objects.Ingredient;
import no.ntnu.idatt1002.demo.model.objects.ShoppingList;
import no.ntnu.idatt1002.demo.model.objects.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.*;

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
        shoppingListDAO.save(shoppingList_item1);
        shoppingListDAO.save(shoppingList_item2);


    }

    @Test
    public void DeleteShoppingList() {

        //number of shopping list items before deleting with sql query

        String number_of_entries = "SELECT COUNT(*) FROM shopping_list";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
             Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(number_of_entries);
            rs.next();
            int number_of_entries_before_deletion = rs.getInt(1);
            assertEquals(2, number_of_entries_before_deletion);
            shoppingListDAO.delete(shoppingList_item2);
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
