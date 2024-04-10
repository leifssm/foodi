package no.ntnu.idatt1005.foodi.model.repository.Main;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:~/main";
    private static final String USER = "main";
    private static final String PASS = "";

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Please enter a command:");
            System.out.println("1: Populate the database");
            System.out.println("2: Remove all data from the database");
            System.out.println("3: Stop the application");

            String command = scanner.nextLine();

            switch (command) {
                case "1":
                    populateDatabase();
                    break;
                case "2":
                    removeAllData();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid command. Please enter a number between 1 and 3.");
                    break;
            }
        }

        scanner.close();
    }

    private static void populateDatabase() throws SQLException {

        removeAllData();

        String[][] ingredientsData = {
                {"Milk", "DAIRY", "LITER"},
                {"Chicken breast", "POULTRY", "KILOGRAM"},
                {"Spinach", "VEGETABLE", "KILOGRAM"},
                {"Apple", "FRUIT", "PIECE"},
                {"Rice", "GRAIN", "GRAM"},
                {"Cinnamon", "SPICE", "TEASPOON"},
                {"Tomato sauce", "SAUCE", "MILLILITER"},
                {"Honey", "SWEET", "TABLESPOON"},
                {"Trout", "FISH", "GRAM"},
                {"Shrimp", "CRUSTACEAN", "GRAM"},
                {"Yogurt", "DAIRY", "MILLILITER"},
                {"Pork loin", "MEAT", "KILOGRAM"},
                {"Lettuce", "VEGETABLE", "PIECE"},
                {"Orange", "FRUIT", "PIECE"},
                {"Oats", "GRAIN", "GRAM"},
                {"Garlic powder", "SPICE", "TEASPOON"},
                {"Barbecue sauce", "SAUCE", "MILLILITER"},
                {"Maple syrup", "SWEET", "TABLESPOON"},
                {"Salmon", "FISH", "GRAM"},
                {"Lobster", "CRUSTACEAN", "GRAM"},
                {"Cream", "DAIRY", "LITER"},
                {"Turkey", "POULTRY", "KILOGRAM"},
                {"Kale", "VEGETABLE", "KILOGRAM"},
                {"Pear", "FRUIT", "PIECE"},
                {"Wheat flour", "GRAIN", "GRAM"},
                {"Paprika", "SPICE", "TEASPOON"},
                {"Alfredo sauce", "SAUCE", "MILLILITER"},
                {"Sugar", "SWEET", "TABLESPOON"},
                {"Cod", "FISH", "GRAM"},
                {"Crab", "CRUSTACEAN", "GRAM"},
                {"Butter", "DAIRY", "GRAM"},
                {"Duck", "POULTRY", "KILOGRAM"},
                {"Broccoli", "VEGETABLE", "KILOGRAM"},
                {"Peach", "FRUIT", "PIECE"},
                {"Cornmeal", "GRAIN", "GRAM"},
                {"Turmeric", "SPICE", "TEASPOON"},
                {"Pesto sauce", "SAUCE", "MILLILITER"},
                {"Chocolate", "SWEET", "TABLESPOON"},
                {"Tuna", "FISH", "GRAM"},
                {"Crawfish", "CRUSTACEAN", "GRAM"},
                {"Buttermilk", "DAIRY", "LITER"},
                {"Quail", "POULTRY", "KILOGRAM"},
                {"Asparagus", "VEGETABLE", "KILOGRAM"},
                {"Cherry", "FRUIT", "PIECE"},
                {"Barley", "GRAIN", "GRAM"},
                {"Chili powder", "SPICE", "TEASPOON"},
                {"Marinara sauce", "SAUCE", "MILLILITER"},
                {"Jam", "SWEET", "TABLESPOON"},
                {"Halibut", "FISH", "GRAM"},
                {"Octopus", "CRUSTACEAN", "GRAM"},
                {"Cheese", "DAIRY", "GRAM"},
                {"Venison", "MEAT", "KILOGRAM"},
                {"Peppers", "VEGETABLE", "PIECE"},
                {"Grape", "FRUIT", "PIECE"},
                {"Pasta", "GRAIN", "GRAM"},
                {"Cumin", "SPICE", "TEASPOON"},
                {"Soy sauce", "SAUCE", "MILLILITER"},
                {"Molasses", "SWEET", "TABLESPOON"},
                {"Anchovy", "FISH", "GRAM"},
                {"Mussel", "CRUSTACEAN", "GRAM"},
                {"Ice cream", "DAIRY", "LITER"},
                {"Lamb chops", "MEAT", "KILOGRAM"},
                {"Cucumber", "VEGETABLE", "PIECE"},
                {"Kiwi", "FRUIT", "PIECE"},
                {"Bread", "GRAIN", "GRAM"},
                {"Cardamom", "SPICE", "TEASPOON"},
                {"Hot sauce", "SAUCE", "MILLILITER"},
                {"Honeycomb", "SWEET", "PIECE"},
                {"Mackerel", "FISH", "GRAM"},
                {"Snail", "CRUSTACEAN", "GRAM"},
                {"Paneer", "DAIRY", "GRAM"},
                {"Bacon", "MEAT", "GRAM"},
                {"Onion", "VEGETABLE", "PIECE"},
                {"Banana", "FRUIT", "PIECE"},
                {"Polenta", "GRAIN", "GRAM"},
                {"Coriander", "SPICE", "TEASPOON"},
                {"Salsa", "SAUCE", "MILLILITER"},
                {"Biscuits", "SWEET", "PIECE"},
                {"Sardines", "FISH", "GRAM"},
                {"Clam", "CRUSTACEAN", "GRAM"},
                {"Kefir", "DAIRY", "LITER"},
                {"Beef jerky", "MEAT", "GRAM"},
                {"Tomato", "VEGETABLE", "PIECE"},
                {"Strawberry", "FRUIT", "PIECE"},
                {"Couscous", "GRAIN", "GRAM"},
                {"Nutmeg", "SPICE", "TEASPOON"},
                {"Tartar sauce", "SAUCE", "MILLILITER"},
                {"Cupcake", "SWEET", "PIECE"},
                {"Catfish", "FISH", "GRAM"},
                {"Squid", "CRUSTACEAN", "GRAM"},
                {"Ghee", "DAIRY", "GRAM"},
                {"Sausage", "MEAT", "GRAM"},
                {"Zucchini", "VEGETABLE", "PIECE"},
                {"Watermelon", "FRUIT", "PIECE"},
                {"Granola", "GRAIN", "GRAM"},
                {"Mustard seeds", "SPICE", "TEASPOON"},
                {"Caesar dressing", "SAUCE", "MILLILITER"},
                {"Brownie", "SWEET", "PIECE"},
                {"Swordfish", "FISH", "GRAM"},
                {"Prawn", "CRUSTACEAN", "GRAM"}
        };

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsData.length; i++) {
            String[] ingredientData = ingredientsData[i];
            Ingredient ingredient = new Ingredient(
                    i + 1,
                    ingredientData[0],
                    Ingredient.IngredientUnit.valueOf(ingredientData[2].toUpperCase()),
                    Ingredient.IngredientCategory.valueOf(ingredientData[1].toUpperCase())
            );
            ingredients.add(ingredient);
        }

        IngredientDAO ingredientDAO = new IngredientDAO();
        for (Ingredient ingredient : ingredients) {
            ingredientDAO.save(ingredient);
        }

        System.out.println("Ingredients table populated successfully.");
    }

    private static void removeAllData() {
        String deleteInventorySql = "DELETE FROM inventory";
        String deleteShoppingListSql = "DELETE FROM shopping_list";
        String deleteUserSql = "DELETE FROM MAIN.PUBLIC.\"user\"";
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

    }
}