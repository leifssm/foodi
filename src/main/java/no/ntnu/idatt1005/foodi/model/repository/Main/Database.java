package no.ntnu.idatt1005.foodi.model.repository.Main;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;

/**
 * This class is responsible for creating and initializing the database. It also checks if the
 * database already exists, and if it does, it will not create a new one.
 *
 * @author Snake727
 * @version 0.2.0
 */

public class Database {

  public static final String DB_URL = "jdbc:h2:~/main"; // This URL will create an H2 database in the user's home directory
  public static final String USER = "main";
  public static final String PASS = "";
  private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

  private Database() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * This method creates a database in the user's home directory with empty tables. Used by test
   * classes.
   */
  public static void initializeEmpty() {
    try (Connection conn = getConnection()) {
      createTablesIfNotExists(conn);
      LOGGER.info("Database has been created and initialized.");
      removeAllData();
    } catch (SQLException e) {
      LOGGER.severe("SQL Exception: " + e.getMessage());
    }
  }

  private static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL, USER, PASS);
  }

  private static void createTablesIfNotExists(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {
      stmt.execute("CREATE TABLE IF NOT EXISTS \"user\" ("
          + "id INT AUTO_INCREMENT PRIMARY KEY,"
          + "name VARCHAR);");

      // Ingredient Table
      stmt.execute("CREATE TABLE IF NOT EXISTS ingredient ("
          + "id INT AUTO_INCREMENT PRIMARY KEY,"
          + "name VARCHAR NOT NULL,"
          + "unit VARCHAR,"
          + "CHECK (unit IN ('GRAM', 'KILOGRAM', 'LITER', 'MILLILITER', 'PIECE', 'POUNDS', 'OUNCE', 'GALLON', 'QUART', 'PINT', 'CUP', 'TABLESPOON', 'TEASPOON')),"
          + "category VARCHAR,"
          + "CHECK (category IN ('DAIRY', 'MEAT', 'VEGETABLE', 'FRUIT', 'GRAIN', 'SPICE', 'SAUCE', 'SWEET', 'BEVERAGE', 'FISH', 'POULTRY', 'CRUSTACEAN')));");

      // Recipe Table
      stmt.execute("CREATE TABLE IF NOT EXISTS recipe ("
          + "id INT AUTO_INCREMENT PRIMARY KEY,"
          + "name VARCHAR NOT NULL,"
          + "description VARCHAR,"
          + "difficulty VARCHAR,"
          + "CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD')),"
          + "dietary_tag VARCHAR,"
          + "CHECK (dietary_tag IN ('VEGAN', 'VEGETARIAN', 'GLUTEN_FREE', 'DAIRY_FREE', 'NUT_FREE', 'EGG_FREE', 'SEA_FREE', 'NONE')),"
          + "duration INT,"
          + "imagePath VARCHAR,"
          + "Instruction VARCHAR);");

      // Recipe Ingredient Table
      stmt.execute("CREATE TABLE IF NOT EXISTS recipe_ingredient ("
          + "recipe_id INT,"
          + "ingredient_id INT,"
          + "amount DOUBLE,"
          + "PRIMARY KEY (recipe_id, ingredient_id),"
          + "FOREIGN KEY (recipe_id) REFERENCES recipe(id),"
          + "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id));");

      // Inventory Table
      stmt.execute("CREATE TABLE IF NOT EXISTS inventory ("
          + "id INT AUTO_INCREMENT,"
          + "ingredient_id INT,"
          + "amount DOUBLE,"
          + "expiration_date DATE,"
          + "user_id INT,"
          + "PRIMARY KEY (id, ingredient_id),"
          + "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id),"
          + "FOREIGN KEY (user_id) REFERENCES \"user\"(id));");

      // Shopping List Table
      stmt.execute(
          "CREATE TABLE IF NOT EXISTS shopping_list ("
              + "shoppinglist_id INT,"
              + "item_id INT AUTO_INCREMENT,"
              + "ingredient_id INT,"
              + "amount DOUBLE,"
              + "user_id INT,"
              + "PRIMARY KEY (shoppinglist_id, item_id),"
              + "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id),"
              + "FOREIGN KEY (user_id) REFERENCES \"user\"(id));");
    }
  }

  /**
   * This method removes all data from the database.
   */
  public static void removeAllData() {
    String deleteInventorySql = "DELETE FROM inventory";
    String deleteShoppingListSql = "DELETE FROM shopping_list";
    String deleteUserSql = "DELETE FROM MAIN.PUBLIC.\"user\"";
    String deleteRecipe_IngredientSql = "DELETE FROM recipe_ingredient";
    String deleteIngredientSql = "DELETE FROM ingredient";
    String deleteRecipeSql = "DELETE FROM recipe";

    try (Connection connection = getConnection()) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(deleteInventorySql);
      statement.executeUpdate(deleteShoppingListSql);
      statement.executeUpdate(deleteUserSql);
      statement.executeUpdate(deleteRecipe_IngredientSql);
      statement.executeUpdate(deleteIngredientSql);
      statement.executeUpdate(deleteRecipeSql);
    } catch (SQLException e) {
      LOGGER.info("SQL Exception: " + e.getMessage());
    }

  }

  /**
   * This method creates a database in the user's home directory. It also populates the database
   * with the necessary tables. The method also checks if the database already exists, and if it
   * does, it will not create a new one.
   */
  public static void initialize() {
    try (Connection conn = getConnection()) {
      createTablesIfNotExists(conn);
      LOGGER.info("Database has been created and initialized.");
    } catch (SQLException e) {
      LOGGER.severe("SQL Exception: " + e.getMessage());
    }

    insertDefaultData();
  }

  private static void insertDefaultData() {
    try {
      insertRecipes();
      LOGGER.info("Recipes inserted successfully.");
      // insertIngredients();
      // LOGGER.info("Ingredients inserted successfully.");
      insertDefaultUser();
      LOGGER.info("Default user inserted successfully.");
    } catch (SQLException e) {
      LOGGER.severe("SQL Exception: " + e.getMessage());
    }
  }

  private static void insertRecipes() throws SQLException {
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

    try (Connection connection = getConnection()) {
      for (int i = 0; i < recipesData.length; i++) {
        String[] recipeData = recipesData[i];
        mergeRecipe(
            connection,
            i + 1,
            recipeData[0],
            recipeData[1],
            recipeData[2].toUpperCase(),
            recipeData[3].toUpperCase(),
            Integer.parseInt(recipeData[4]),
            recipeData[5],
            recipeData[6]
        );
      }
    } catch (SQLException e) {
      LOGGER.severe("SQL Exception: " + e.getMessage());
    }
  }

  private static void insertDefaultUser() throws SQLException {
    new UserDAO().addDefaultUserIfNotExists();
  }

  private static void mergeRecipe(Connection connection, int id, String name, String description,
      String difficulty,
      String dietaryTag,
      int duration, String imagePath, String instruction) {
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(
          "MERGE INTO recipe (id, name, description, difficulty, dietary_tag, duration, imagePath, instruction) VALUES ("
              + id + ", '" + name + "', '" + description + "', '" + difficulty + "', '" + dietaryTag
              + "', " + duration + ", '" + imagePath + "', '" + instruction + "')");
    } catch (SQLException e) {
      LOGGER.severe("SQL Exception: " + e.getMessage());
    }
  }

  private static void insertIngredients() throws SQLException {
    final String[][] ingredientsData = {
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

    try (Connection connection = getConnection()) {
      for (int i = 0; i < ingredientsData.length; i++) {
        String[] ingredientData = ingredientsData[i];
        mergeIngredient(
            connection,
            i + 1,
            ingredientData[0],
            Ingredient.Unit.valueOf(ingredientData[2].toUpperCase()),
            Ingredient.Category.valueOf(ingredientData[1].toUpperCase())
        );
      }
    } catch (SQLException e) {
      LOGGER.severe("SQL Exception: " + e.getMessage());
    }
  }

  private static void mergeIngredient(Connection connection, int id, String name,
      Ingredient.Unit unit, Ingredient.Category category) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(
          "MERGE INTO ingredient (id, name, unit, category) VALUES (" + id + ", '" + name + "', '"
              + unit + "', '" + category + "')");
    } catch (SQLException e) {
      LOGGER.severe("SQL Exception: " + e.getMessage());
    }
  }

  private static boolean tablesExist(Connection conn) throws SQLException {
    DatabaseMetaData dbm = conn.getMetaData();
    ResultSet tables = dbm.getTables(null, null, "user", null);
    return tables.next();
  }
}

