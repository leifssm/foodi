package no.ntnu.idatt1002.demo.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
  private static final String DB_URL = "jdbc:h2:~/test"; // This URL will create an H2 database in the user's home directory
  private static final String USER = "sa";
  private static final String PASS = "";

  public void initializeDatabase() {
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
      System.out.println("Connected to the database.");
      createTables(conn);
      System.out.println("Database has been initialized.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void createTables(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement()) {
      // User Table
      stmt.execute("CREATE TABLE IF NOT EXISTS \"user\" (" +
          "id INT AUTO_INCREMENT PRIMARY KEY," +
          "name VARCHAR);");

      // Ingredient Table
      stmt.execute("CREATE TABLE IF NOT EXISTS ingredient (" +
          "id INT AUTO_INCREMENT PRIMARY KEY," +
          "name VARCHAR NOT NULL," +
          "unit VARCHAR," +
          "CHECK (unit IN ('GRAM', 'KILOGRAM', 'LITER', 'MILLILITER', 'PIECE', 'POUNDS', 'OUNCE', 'GALLON', 'QUART', 'PINT', 'CUP', 'TABLESPOON', 'TEASPOON'))," +
          "category VARCHAR," +
          "CHECK (category IN ('DAIRY', 'MEAT', 'VEGETABLE', 'FRUIT', 'GRAIN', 'SPICE', 'SAUCE', 'SWEET', 'BEVERAGE')));");

      // Recipe Table
      stmt.execute("CREATE TABLE IF NOT EXISTS recipe (" +
          "id INT AUTO_INCREMENT PRIMARY KEY," +
          "name VARCHAR NOT NULL," +
          "description VARCHAR," +
          "difficulty VARCHAR," +
          "CHECK (difficulty IN ('Easy', 'Medium', 'Hard'))," +
          "duration INT);");

      // Recipe Ingredient Table
      stmt.execute("CREATE TABLE IF NOT EXISTS recipe_ingredient (" +
          "recipe_id INT," +
          "ingredient_id INT," +
          "amount DOUBLE," +
          "PRIMARY KEY (recipe_id, ingredient_id)," +
          "FOREIGN KEY (recipe_id) REFERENCES recipe(id)," +
          "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id));");

      // Inventory Table
      stmt.execute("CREATE TABLE IF NOT EXISTS inventory (" +
          "id INT AUTO_INCREMENT PRIMARY KEY," +
          "ingredient_id INT," +
          "amount DOUBLE," +
          "expiration_date DATE," +
          "user_id INT," +
          "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)," +
          "FOREIGN KEY (user_id) REFERENCES \"user\"(id));");

      // Shopping List Table
      stmt.execute("CREATE TABLE IF NOT EXISTS shopping_list (" +
          "id INT AUTO_INCREMENT PRIMARY KEY," +
          "ingredient_id INT," +
          "amount DOUBLE," +
          "user_id INT," +
          "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)," +
          "FOREIGN KEY (user_id) REFERENCES \"user\"(id));");

      System.out.println("Tables created successfully.");
    }
  }

  // Main method for testing purposes
  public static void main(String[] args) {
    DatabaseUtils dbInitializer = new DatabaseUtils();
    dbInitializer.initializeDatabase();
  }
}

