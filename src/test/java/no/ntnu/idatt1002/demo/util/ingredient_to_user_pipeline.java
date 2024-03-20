package no.ntnu.idatt1002.demo.util;

import static java.sql.Types.NULL;
import static no.ntnu.idatt1002.demo.model.repository.Database.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.*;
import java.time.LocalDate;

import no.ntnu.idatt1002.demo.model.DAO.IngredientDAO;
import no.ntnu.idatt1002.demo.model.DAO.InventoryDAO;
import no.ntnu.idatt1002.demo.model.DAO.UserDAO;
import no.ntnu.idatt1002.demo.model.objects.Ingredient;
import no.ntnu.idatt1002.demo.model.objects.Inventory;
import no.ntnu.idatt1002.demo.model.objects.User;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Dette bestemmer rekkefølgen på testene
public class ingredient_to_user_pipeline {

    private static UserDAO userDA;
    private static InventoryDAO inventoryDA;

    private static Inventory inventory1;

    private static Inventory inventory2;

    private static Inventory inventory3;

    private static IngredientDAO ingredientDA;

    private static Ingredient testIngredient1;
    private static Ingredient testIngredient2;

    private static User testUser;

    @BeforeAll
    public static void setUp() throws SQLException {
        String deleteInventorySql = "DELETE FROM inventory";
        String deleteShoppingListSql = "DELETE FROM shopping_list";
        String deleteUserSql = "DELETE FROM TEST.PUBLIC.\"user\"";
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



        // clean sheet

        userDA = new UserDAO();
        testUser = new User(25, "Ola");

        ingredientDA = new IngredientDAO();
        testIngredient1 = new Ingredient(1, "Carrot", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
        testIngredient2 = new Ingredient(2, "Potato", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);

        // Konverter strengen til et LocalDate-objekt
        LocalDate localDate = LocalDate.of(2004, 10, 14);

        // Konverter LocalDate til java.sql.Date
        Date sqlDate = Date.valueOf(localDate);


        //når de initialiseres, bør fremmednøkkelverdiene være null og så senere, bli tilegnet, gjennom save metoden
        inventoryDA = new InventoryDAO();
        inventory1 = new Inventory(1, NULL, 7, sqlDate, NULL);
        inventory2 = new Inventory(2, NULL, 2, sqlDate, NULL);
        inventory3 = new Inventory(3, NULL, 23, sqlDate, NULL);

        // save the user, ingredients and inventory - indirectly testing the save method
        // in the user, ingredient and inventory DAO classes
        //allows the other tests to be run
        userDA.save(testUser);

        ingredientDA.save(testIngredient1);
        ingredientDA.save(testIngredient2);

        inventoryDA.save(inventory1, testIngredient1, testUser);
        inventoryDA.save(inventory2, testIngredient2, testUser);
        inventoryDA.save(inventory3, testIngredient1, testUser);

    }

/*
    @Test
    public void empty_tables(){

        String number_of_entries_inventory = "Select COUNT(*) FROM inventory";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(number_of_entries_inventory);
            resultSet.next();
            int number_of_entries = resultSet.getInt(1);
            assertEquals(0, number_of_entries);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String number_of_entries_shopping_list = "Select COUNT(*) FROM shopping_list";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(number_of_entries_shopping_list);
            resultSet.next();
            int number_of_entries = resultSet.getInt(1);
            assertEquals(0, number_of_entries);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String number_of_entries_ingredient = "Select COUNT(*) FROM ingredient";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(number_of_entries_ingredient);
            resultSet.next();
            int number_of_entries = resultSet.getInt(1);
            assertEquals(0, number_of_entries);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        String number_of_entries_user = "Select COUNT(*) FROM \"user\"";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(number_of_entries_user);
            resultSet.next();
            int number_of_entries = resultSet.getInt(1);
            assertEquals(0, number_of_entries);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
*/


    @Nested
    @DisplayName("Behavioral Tests maybe Inventory Test")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class BehavioralTests {


        @Test
        @Order(1)
        public void save_function()  {

            //testing the save method for the inventory, ingredient and user DAO classes
            String number_of_entries_inventory = "Select COUNT(*) FROM inventory";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(number_of_entries_inventory);
                resultSet.next();
                int number_of_entries = resultSet.getInt(1);
                assertEquals(2, number_of_entries);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


        @Test
        @Order(1)
        public void deleteAllOfIngredient() {
            inventoryDA.delete_ingredient(1, 1);
            String number_of_entries_inventory = "Select COUNT(*) FROM inventory";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(number_of_entries_inventory);
                resultSet.next();
                int number_of_entries = resultSet.getInt(1);
                assertEquals(2, number_of_entries);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Test
        @Order(2)
        public void update_amount_of_ingredient() {
            inventoryDA.update_amount_of_ingredient(inventory2, 24, 1);
            String checkSql = "SELECT * FROM inventory";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 PreparedStatement pstmt = connection.prepareStatement(checkSql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int amount = rs.getInt("amount");
                    if (id == 1){
                        assertEquals(24, amount);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Test
        @Order(5)
        public void delete_inventory() {
            inventoryDA.delete_inventory(inventory2);
            String number_of_entries_inventory = "Select COUNT(*) FROM inventory";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(number_of_entries_inventory);
                resultSet.next();
                int number_of_entries = resultSet.getInt(1);
                assertEquals(1, number_of_entries);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Test
        @Order(4)
        public void update_expiration_date() {
            LocalDate localDate = LocalDate.of(2004, 10, 14);
            Date sqlDate = Date.valueOf(localDate);
            inventoryDA.update_expiration_date(inventory1, sqlDate, 1);
            String checkSql = "SELECT * FROM inventory";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 PreparedStatement pstmt = connection.prepareStatement(checkSql)) {
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    Date expiration_date = rs.getDate("expiration_date");
                    if (id == 1){
                        assertEquals(sqlDate, expiration_date);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }


        @Test
        @Order(3)
        //fikser dette senere, ellers er inventory tingz gucci.
        public void retrieve_inventory() {
            Inventory inventory = inventoryDA.retrieve(inventory3, testIngredient1, testUser);
            assertEquals(inventory3.getInventoryId(), inventory.getInventoryId());
            assertEquals(testIngredient1.getId(), inventory.getIngredientId());
            assertEquals(inventory3.getExperationDate(), inventory.getExperationDate());
            assertEquals(testUser.getUserId(), inventory.getUserId());
            assertEquals(inventory3.getAmount(), inventory.getAmount());
        }


    }

    @Nested
    @DisplayName("User Tests")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class UserTests {


        @Test
        @Order(2)
        public void delete_function() throws SQLException {

            User user = new User(2, "Kevin");
            userDA.save(user);

            String number_of_entries_user = "Select COUNT(*) FROM TEST.PUBLIC.\"user\"";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(number_of_entries_user);
                resultSet.next();
                int number_of_entries_before_deletion = resultSet.getInt(1);
                assertEquals(2, number_of_entries_before_deletion);
                userDA.delete(user);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(number_of_entries_user);
                resultSet.next();
                int number_of_entries_after_deletion = resultSet.getInt(1);
                assertEquals(1, number_of_entries_after_deletion);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Test
        @Order(1)
        public void retrieve_function() {
            User user = userDA.retrieve(testUser);
            assertEquals(testUser.getUserId(), user.getUserId());
            assertEquals(testUser.getName(), user.getName());
        }

    }

    @Nested
    @DisplayName("Ingredient Tests")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class IngredientTests {


        @Test
        @Order(1)
        public void delete_function() throws SQLException {

            Ingredient newIngredient = new Ingredient(3, "Onion", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
            ingredientDA.save(newIngredient);

            String number_of_entries_ingredient = "Select COUNT(*) FROM ingredient";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(number_of_entries_ingredient);
                resultSet.next();
                int number_of_entries_before_deletion = resultSet.getInt(1);
                assertEquals(3, number_of_entries_before_deletion);
                ingredientDA.delete(newIngredient);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(number_of_entries_ingredient);
                resultSet.next();
                int number_of_entries_after_deletion = resultSet.getInt(1);
                assertEquals(2, number_of_entries_after_deletion);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }


        }

        @Test
        @Order(2)
        public void retrieve_function() {
            Ingredient ingredient = ingredientDA.retrieve(testIngredient2);
            assertEquals(testIngredient2.getId(), ingredient.getId());
            assertEquals(testIngredient2.getName(), ingredient.getName());
            assertEquals(testIngredient2.getUnit(), ingredient.getUnit());
            assertEquals(testIngredient2.getCategory(), ingredient.getCategory());
        }

    }



}
