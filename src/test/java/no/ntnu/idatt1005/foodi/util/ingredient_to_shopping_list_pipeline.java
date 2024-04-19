//package no.ntnu.idatt1005.foodi.util;
//
//import static java.sql.Types.NULL;
//import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.Map;
//
//import no.ntnu.idatt1005.foodi.model.DAO.*;
//import no.ntnu.idatt1005.foodi.model.objects.*;
//import org.junit.jupiter.api.*;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Dette bestemmer rekkefølgen på testene
//public class ingredient_to_shopping_list_pipeline {
//
//    private static UserDAO userDA;
//    private static InventoryIngredientDAO inventoryDA;
//
//    private static InventoryIngredient inventoryIngredient1;
//
//    private static InventoryIngredient inventoryIngredient2;
//
//    private static InventoryIngredient inventoryIngredient3;
//
//    private static InventoryIngredient inventoryIngredient4;
//
//    private static IngredientDAO ingredientDA;
//
//    private static Ingredient testIngredient1;
//    private static Ingredient testIngredient2;
//
//    private static User testUser;
//
//
//    //recipe og recipe_ingredient
//
//    private static RecipeDAO recipeDAO;
//    private static Recipe testRecipe;
//    private static Recipe testRecipe2;
//
//    private static RecipeIngredientDAO recipe_ingredient_DAO;
//
//    private static RecipeIngredient testRecipe_Ingredient;
//    private static RecipeIngredient testRecipe_Ingredient2;
//
//    //shopping list
//
//    private static ShoppingListDAO shoppingListDAO;
//
//    @BeforeAll
//    public static void setUp() throws SQLException {
//        String deleteInventorySql = "DELETE FROM inventory";
//        String deleteShoppingListSql = "DELETE FROM shopping_list";
//        String deleteUserSql = "DELETE FROM MAIN.PUBLIC.\"user\"";
//        String deleteRecipe_IngredientSql = "DELETE FROM recipe_ingredient";
//        String deleteIngredientSql = "DELETE FROM ingredient";
//        String deleteRecipeSql = "DELETE FROM recipe";
//        ;
//
//
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//            Statement statement = connection.createStatement();
//            statement.executeUpdate(deleteInventorySql);
//            statement.executeUpdate(deleteShoppingListSql);
//            statement.executeUpdate(deleteUserSql);
//            statement.executeUpdate(deleteRecipe_IngredientSql);
//            statement.executeUpdate(deleteIngredientSql);
//            statement.executeUpdate(deleteRecipeSql);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//
//
//        // clean sheet
//
//        userDA = new UserDAO();
//        testUser = new User(25, "Ola");
//
//        ingredientDA = new IngredientDAO();
//        testIngredient1 = new Ingredient(1, "Egg", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.MEAT);
//        testIngredient2 = new Ingredient(2, "Potato", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
//
//        // Konverter strengen til et LocalDate-objekt
//        LocalDate localDate = LocalDate.of(2004, 10, 14);
//
//        // Konverter LocalDate til java.sql.Date
//        Date sqlDate = Date.valueOf(localDate);
//
//
//        //når de initialiseres, bør fremmednøkkelverdiene være null og så senere, bli tilegnet, gjennom save metoden
//        inventoryDA = new InventoryIngredientDAO();
//        inventoryIngredient1 = new InventoryIngredient(1, NULL, 7, sqlDate, NULL);
//        inventoryIngredient2 = new InventoryIngredient(2, NULL, 2, sqlDate, NULL);
//        inventoryIngredient3 = new InventoryIngredient(3, NULL, 23, sqlDate, NULL);
//        inventoryIngredient4 = new InventoryIngredient(1, NULL, 5, sqlDate, NULL);
//
//        // save the user, ingredients and inventory - indirectly testing the save method
//        // in the user, ingredient and inventory DAO classes
//        //allows the other tests to be run
//        userDA.save(testUser);
//
//        ingredientDA.save(testIngredient1);
//        ingredientDA.save(testIngredient2);
//
//        inventoryDA.save(inventoryIngredient1, testIngredient1, testUser);
//        inventoryDA.save(inventoryIngredient2, testIngredient2, testUser);
//        inventoryDA.save(inventoryIngredient3, testIngredient1, testUser);
//        inventoryDA.save(inventoryIngredient4, testIngredient1, testUser);  //samme ingrediens og id som i inventory 1, bare med annerledes megnde, bør resultere i bare 1 tuppel, hvor mengden er 12
//
//
//        //recipe og recipe_ingredient
//
//        recipeDAO = new RecipeDAO();
//
//        Recipe testRecipe1 = new Recipe(1, "Pasta", "Pasta with tomato sauce", Recipe.Difficulty.EASY, Recipe.DietaryTag.NONE, 30, "imagePath", "Instruction");
//        Recipe testRecipe2 = new Recipe(2, "Chipsi Mayai", "Potet omelett", Recipe.Difficulty.EASY,Recipe.DietaryTag.NONE,30, "imagePath", "Instruction");
//
//        recipe_ingredient_DAO = new RecipeIngredientDAO();
//
//        testIngredient1 = new Ingredient(1, "Egg", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.MEAT);
//        testIngredient2 = new Ingredient(2, "Potato", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
//
//        testRecipe_Ingredient = new RecipeIngredient(testRecipe1, testIngredient1, 35.0);
//        testRecipe_Ingredient2 = new RecipeIngredient(testRecipe2, testIngredient2, 3.0);
//
//        recipeDAO.save(testRecipe1);
//        recipeDAO.save(testRecipe2);
//
//        recipe_ingredient_DAO.save(testRecipe_Ingredient);
//        recipe_ingredient_DAO.save(testRecipe_Ingredient2);
//
//
//        //shopping list
//        shoppingListDAO = new ShoppingListDAO();
//
//    }
//
///*
//    @Test
//    public void empty_tables(){
//
//        String number_of_entries_inventory = "Select COUNT(*) FROM inventory";
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(number_of_entries_inventory);
//            resultSet.next();
//            int number_of_entries = resultSet.getInt(1);
//            assertEquals(0, number_of_entries);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        String number_of_entries_shopping_list = "Select COUNT(*) FROM shopping_list";
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(number_of_entries_shopping_list);
//            resultSet.next();
//            int number_of_entries = resultSet.getInt(1);
//            assertEquals(0, number_of_entries);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        String number_of_entries_ingredient = "Select COUNT(*) FROM ingredient";
//
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(number_of_entries_ingredient);
//            resultSet.next();
//            int number_of_entries = resultSet.getInt(1);
//            assertEquals(0, number_of_entries);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//        String number_of_entries_user = "Select COUNT(*) FROM \"user\"";
//        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(number_of_entries_user);
//            resultSet.next();
//            int number_of_entries = resultSet.getInt(1);
//            assertEquals(0, number_of_entries);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//
//    }
//*/
//
//
//    @Nested
//    @DisplayName("Behavioral Tests maybe Inventory Test")
//    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//    class BehavioralTests {
//
//
//        @Test
//        @Order(1)
//        public void save_function()  {
//
//            //testing the save method for the inventory, ingredient and user DAO classes
//            String number_of_entries_inventory = "Select COUNT(*) FROM inventory";
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(number_of_entries_inventory);
//                resultSet.next();
//                int number_of_entries = resultSet.getInt(1);
//                assertEquals(2, number_of_entries);
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//
//        @Test
//        @Order(1)
//        public void deleteAllOfIngredient() {
//            inventoryDA.delete_ingredient(2, 2);
//            String number_of_entries_inventory = "Select COUNT(*) FROM inventory";
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(number_of_entries_inventory);
//                resultSet.next();
//                int number_of_entries = resultSet.getInt(1);
//                assertEquals(2, number_of_entries);
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        @Test
//        @Order(2)
//        public void update_amount_of_ingredient() {
//            inventoryDA.update_amount_of_ingredient(inventoryIngredient2, 24, 1);
//            String checkSql = "SELECT * FROM inventory";
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
//                 PreparedStatement pstmt = connection.prepareStatement(checkSql)) {
//                ResultSet rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    int id = rs.getInt("id");
//                    int amount = rs.getInt("amount");
//                    if (id == 1){
//                        assertEquals(12, amount);
//                    }
//                }
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        @Test
//        @Order(5)
//        public void delete_inventory() {
//            inventoryDA.delete_inventory(inventoryIngredient2);
//            String number_of_entries_inventory = "Select COUNT(*) FROM inventory";
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(number_of_entries_inventory);
//                resultSet.next();
//                int number_of_entries = resultSet.getInt(1);
//                assertEquals(2, number_of_entries);
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        @Test
//        @Order(4)
//        public void update_expiration_date() {
//            LocalDate localDate = LocalDate.of(2004, 10, 14);
//            Date sqlDate = Date.valueOf(localDate);
//            inventoryDA.update_expiration_date(inventoryIngredient1, sqlDate, 1);
//            String checkSql = "SELECT * FROM inventory";
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
//                 PreparedStatement pstmt = connection.prepareStatement(checkSql)) {
//                ResultSet rs = pstmt.executeQuery();
//                while (rs.next()) {
//                    int id = rs.getInt("id");
//                    Date expiration_date = rs.getDate("expiration_date");
//                    if (id == 1){
//                        assertEquals(sqlDate, expiration_date);
//                    }
//                }
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//
//        @Test
//        @Order(3)
//        //fikser dette senere, ellers er inventory tingz gucci.
//        public void retrieve_inventory() {
//            InventoryIngredient inventoryIngredient = inventoryDA.retrieve(inventoryIngredient3, testIngredient1, testUser);
//            assertEquals(inventoryIngredient3.getInventoryId(), inventoryIngredient.getInventoryId());
//            assertEquals(testIngredient1.getId(), inventoryIngredient.getIngredientId());
//            assertEquals(inventoryIngredient3.getExperationDate(), inventoryIngredient.getExperationDate());
//            assertEquals(testUser.getUserId(), inventoryIngredient.getUserId());
//            assertEquals(inventoryIngredient3.getAmount(), inventoryIngredient.getAmount());
//        }
//
//
//    }
//
//    @Nested
//    @DisplayName("User Tests")
//    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//    class UserTests {
//
//
//        @Test
//        @Order(2)
//        public void delete_function() throws SQLException {
//
//            User user = new User(2, "Kevin");
//            userDA.save(user);
//
//            String number_of_entries_user = "Select COUNT(*) FROM TEST.PUBLIC.\"user\"";
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(number_of_entries_user);
//                resultSet.next();
//                int number_of_entries_before_deletion = resultSet.getInt(1);
//                assertEquals(2, number_of_entries_before_deletion);
//                userDA.delete(user);
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(number_of_entries_user);
//                resultSet.next();
//                int number_of_entries_after_deletion = resultSet.getInt(1);
//                assertEquals(1, number_of_entries_after_deletion);
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        @Test
//        @Order(1)
//        public void retrieve_function() {
//            User user = userDA.retrieve(testUser);
//            assertEquals(testUser.getUserId(), user.getUserId());
//            assertEquals(testUser.getName(), user.getName());
//        }
//
//    }
//
//    @Nested
//    @DisplayName("Ingredient Tests")
//    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//    class IngredientTests {
//
//
//        @Test
//        @Order(1)
//        public void delete_function() throws SQLException {
//
//            Ingredient newIngredient = new Ingredient(3, "Onion", Ingredient.IngredientUnit.PIECE, Ingredient.IngredientCategory.VEGETABLE);
//            ingredientDA.save(newIngredient);
//
//            String number_of_entries_ingredient = "Select COUNT(*) FROM ingredient";
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(number_of_entries_ingredient);
//                resultSet.next();
//                int number_of_entries_before_deletion = resultSet.getInt(1);
//                assertEquals(3, number_of_entries_before_deletion);
//                ingredientDA.delete(newIngredient);
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//
//            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)){
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(number_of_entries_ingredient);
//                resultSet.next();
//                int number_of_entries_after_deletion = resultSet.getInt(1);
//                assertEquals(2, number_of_entries_after_deletion);
//            } catch (SQLException e) {
//                System.out.println(e.getMessage());
//            }
//
//
//        }
//
//        @Test
//        @Order(2)
//        public void retrieve_function() {
//            Ingredient ingredient = ingredientDA.retrieve(testIngredient2);
//            assertEquals(testIngredient2.getId(), ingredient.getId());
//            assertEquals(testIngredient2.getName(), ingredient.getName());
//            assertEquals(testIngredient2.getUnit(), ingredient.getUnit());
//            assertEquals(testIngredient2.getCategory(), ingredient.getCategory());
//        }
//
//    }
//
//    @Nested
//    @DisplayName("Shopping List Tests")
//    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//    class ShoppingListTest{
//
//        @Test
//        @Order(1)
//        public void generateShoppingListBasedOnInventoryAndRecipes() throws SQLException {
//
//
//            int recipeId = testRecipe.getId();
//            int inventoryId = inventoryIngredient3.getInventoryId();
//
//            // Assuming we have a method in inventoryDA to get the total amount of each ingredient in inventory
//            Map<Integer, Double> currentInventory = inventoryDA.getTotalAmountPerIngredient(inventoryId);
//
//            System.out.println(currentInventory);
//
//
//            // And assuming we have a method in recipe_ingredient_dbAccess to get required ingredients for a recipe
//            Map<Integer, Double> requiredIngredientsForRecipe = recipe_ingredient_DAO.getRequiredAmountOfIngredientBasedOnChosenRecipe(recipeId);
//            System.out.println((requiredIngredientsForRecipe));
//
//
//
//      // Create a shopping list map to hold ingredient IDs and amounts needed
//      Map<Integer, Double> shoppingList = new HashMap<>();
//
//            // Compare required ingredients with inventory
//            for (Map.Entry<Integer, Double> entry : requiredIngredientsForRecipe.entrySet()) {
//                int ingredientId = entry.getKey();
//                double requiredAmount = entry.getValue();
//                double currentAmount = currentInventory.getOrDefault(ingredientId, 0.0);
//
//                if (currentAmount < requiredAmount) {
//                    shoppingList.put(ingredientId, requiredAmount - currentAmount);
//                    //int changed_amount = (int) (currentAmount - requiredAmount);
//                    //inventoryDA.update_amount_of_ingredient(inventory3, changed_amount, ingredientId);
//                }
//
//
//
//            }
//            //print out shopping list content
//
//            for (Map.Entry<Integer, Double> entry : shoppingList.entrySet()) {
//                System.out.println("Ingredient ID: " + entry.getKey() + " Amount: " + entry.getValue());
//            }
//
//            System.out.println(shoppingList);
//
//            // Assuming we have a method to save shopping list in ShoppingListDatabaseAccess
//            shoppingListDAO.save(shoppingList, testUser.getUserId(), 1);
//
//            // Verify the shopping list is saved correctly
//            Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(testUser.getUserId());
//            assertEquals(shoppingList.size(), retrievedShoppingList.size());
//            for (Map.Entry<Integer, Double> entry : shoppingList.entrySet()) {
//                assertTrue(retrievedShoppingList.containsKey(entry.getKey()));
//                assertEquals(entry.getValue(), retrievedShoppingList.get(entry.getKey()), 0.01);
//            }
//        }
//
//    }
//
//
//
//
//
//
//}
