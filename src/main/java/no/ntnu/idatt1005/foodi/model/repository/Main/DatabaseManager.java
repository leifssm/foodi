package no.ntnu.idatt1005.foodi.model.repository.Main;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.RecipeDAO;
import no.ntnu.idatt1005.foodi.model.objects.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.Recipe;

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
            System.out.println("4: Populate the recipes table");

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
                case "4":
                    populateRecipes();
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

    private static void populateRecipes() throws SQLException {
        removeAllData();

        String[][] recipesData = {
                {
                        "Crispy Tortilla Garden Salad",
                        "A vibrant garden salad garnished with crispy tortilla squares, fresh cherry tomatoes, and thinly sliced radishes, perfect for a light and healthy meal.",
                        "EASY",
                        "VEGAN",
                        "15",
                        "recipes/crispy-tortilla-garden-salad.png",
                        "1. Rinse and dry lettuce leaves and fresh herbs.\n2. Thinly slice radishes and onions.\n3. Cut tortilla into small squares and toast until crispy.\n4. Combine vegetables in a bowl, top with tortilla squares.\n5. Drizzle with olive oil and balsamic vinegar, toss gently.\n6. Serve immediately to maintain the crispiness of tortillas."
                },
                {
                        "Grilled Salmon with Zoodle Medley",
                        "Succulent grilled salmon served on a bed of zucchini noodles and carrots, with a touch of lime for an added zest.",
                        "MEDIUM",
                        "GLUTEN_FREE",
                        "30",
                        "recipes/grilled-salmon-zoodle-medley.png",
                        "1. Season the salmon with salt, pepper, and a hint of lime zest.\n2. Grill the salmon to desired doneness.\n3. Spiralize zucchini and carrots into noodles.\n4. Sauté the vegetables with a bit of olive oil and garlic.\n5. Plate the zoodles, top with the grilled salmon.\n6. Garnish with a wedge of lime and serve."
                },
                {
                        "Traditional Seafood Paella",
                        "A classic Spanish seafood paella with a mix of shrimp, mussels, and squid, infused with aromatic saffron and herbs.",
                        "HARD",
                        "SEA_FREE",
                        "60",
                        "recipes/traditional-seafood-paella.png",
                        "1. Prepare the seafood by cleaning and cutting it as needed.\n2. In a paella pan, sauté onions and garlic in olive oil.\n3. Add rice and cook until translucent, stirring frequently.\n4. Stir in saffron-infused broth and simmer.\n5. Add seafood, arranging it evenly across the pan.\n6. Cook without stirring until the rice is tender and seafood is cooked.\n7. Let it rest for a few minutes before serving.\n8. Garnish with lemon wedges and parsley."
                },
                {
                        "Banana Almond Pancakes",
                        "Fluffy pancakes stacked high, topped with freshly sliced bananas, almond slivers, and a generous drizzle of maple syrup.",
                        "EASY",
                        "NUT_FREE",
                        "20",
                        "recipes/banana-almond-pancakes.png",
                        "1. Mix flour, baking powder, sugar, and a pinch of salt.\n2. In another bowl, beat eggs and then mix in milk and melted butter.\n3. Combine wet and dry ingredients to make the batter.\n4. Pour batter onto a hot griddle to form pancakes, flipping once bubbles form.\n5. Slice bananas and toast almond slivers.\n6. Stack pancakes, adding banana slices and almonds between layers.\n7. Finish with a drizzle of maple syrup and garnish with mint."
                },
                {
                        "Rustic Cheese Pizza",
                        "A homemade pizza with a golden-brown crust, bubbly cheese topping, and a hint of rosemary, perfect for a cozy dinner.",
                        "MEDIUM",
                        "VEGETARIAN",
                        "45",
                        "recipes/rustic-cheese-pizza.png",
                        "1. Prepare pizza dough and let it rise until doubled in size.\n2. Preheat oven to a high temperature with a pizza stone inside.\n3. Roll out the dough and place on a cornmeal-dusted paddle.\n4. Spread tomato sauce and sprinkle a blend of mozzarella and cheddar cheese.\n5. Add fresh rosemary leaves.\n6. Slide pizza onto the hot stone and bake until the crust is crisp and cheese is golden.\n7. Let cool for a few minutes, then slice and serve."
                },
                {
                        "Smoky BBQ Ribs with Sides",
                        "Tender, fall-off-the-bone ribs smothered in smoky BBQ sauce, served with crispy fries, seasoned tomatoes, and zesty pickles.",
                        "HARD",
                        "DAIRY_FREE",
                        "120",
                        "recipes/smoky-bbq-ribs-with-sides.png",
                        "1. Season the ribs with a dry rub and let marinate for at least 1 hour.\n2. Preheat the grill to low heat and place ribs on indirect heat.\n3. Grill the ribs, turning occasionally, for about 2 hours.\n4. Baste with BBQ sauce during the last 30 minutes of grilling.\n5. Slice potatoes and season for fries, then bake until golden.\n6. Slice tomatoes and season with salt, pepper, and a pinch of parsley.\n7. Serve ribs with a side of fries, seasoned tomatoes, and pickles."
                },
                {
                        "Classic Grilled Cheese Sandwich",
                        "Golden-brown toasted sandwich with a gooey melted cheese filling, perfect for a quick and satisfying meal.",
                        "EASY",
                        "VEGETARIAN",
                        "10",
                        "recipes/classic-grilled-cheese-sandwich.png",
                        "1. Butter two slices of bread on one side each.\n2. Place a slice of cheese between the bread slices, buttered sides out.\n3. Heat a pan over medium heat and place the sandwich in the pan.\n4. Grill until the bread is golden brown and cheese is melted, flipping once.\n5. Remove from pan, let it cool for a minute, then cut diagonally and serve."
                },
                {
                        "Pesto Farfalle Pasta Salad",
                        "Chilled farfalle pasta coated in rich pesto sauce, mixed with cherry tomatoes and fresh greens for a light and flavorful dish.",
                        "EASY",
                        "VEGETARIAN",
                        "20",
                        "recipes/pesto-farfalle-pasta-salad.png",
                        "1. Cook farfalle pasta in salted boiling water until al dente, then drain and cool.\n2. In a bowl, mix the pasta with homemade or store-bought pesto sauce.\n3. Halve cherry tomatoes and shred some fresh basil leaves.\n4. Add the tomatoes and basil to the pasta and toss everything together.\n5. Refrigerate until serving time.\n6. Garnish with parmesan shavings before serving, if desired."
                },
                {
                        "Saucy Chicken with Broccoli and Potatoes",
                        "Tender chicken pieces simmered in a savory sauce with steamed broccoli and potatoes, a wholesome and comforting meal.",
                        "MEDIUM",
                        "DAIRY_FREE",
                        "40",
                        "recipes/saucy-chicken-with-broccoli-potatoes.png",
                        "1. Season chicken pieces with salt, pepper, and paprika.\n2. Brown chicken in a pan with olive oil, then remove and set aside.\n3. In the same pan, add diced potatoes and cook until they start to soften.\n4. Add broccoli florets and continue to cook for a few minutes.\n5. Return the chicken to the pan and add your choice of sauce (e.g., tomato-based, cream-based).\n6. Cover and simmer until the chicken is cooked through and the vegetables are tender.\n7. Serve hot, garnished with fresh herbs."
                }
        };

        RecipeDAO recipeDAO = new RecipeDAO();
        for (int i = 0; i < recipesData.length; i++) {
            String[] recipeData = recipesData[i];
            Recipe recipe = new Recipe(
                    i + 1,
                    recipeData[0],
                    recipeData[1],
                    Recipe.Difficulty.valueOf(recipeData[2].toUpperCase()),
                    Recipe.DietaryTag.valueOf(recipeData[3].toUpperCase()),
                    Integer.parseInt(recipeData[4]),
                    recipeData[5],
                    recipeData[6]
            );
            recipeDAO.save(recipe);
        }

        System.out.println("Recipes table populated successfully.");
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