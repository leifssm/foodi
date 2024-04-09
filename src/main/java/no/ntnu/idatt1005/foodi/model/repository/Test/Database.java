package no.ntnu.idatt1005.foodi.model.repository.Test;

import java.sql.*;

/**
 * This class is responsible for creating and initializing the database.
 * It also checks if the database already exists, and if it does, it will not create a new one.
 *
 * @version 0.2.0
 * @author Snake727
 */

public class Database {
  public static final String DB_URL = "jdbc:h2:~/test"; // This URL will create an H2 database in the user's home directory
  public static final String USER = "sa";
  public static final String PASS = "";

  /**
   * This method creates a database in the user's home directory. It also populates the database with the necessary tables.
   * The method also checks if the database already exists, and if it does, it will not create a new one.
   *
   */

  public void initializeDatabase() {
    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
      if (!tablesExist(conn)) {
        System.out.println("Database does not exist. Creating a new one...");
        createTables(conn);
        System.out.println("Database has been created and initialized.");
      } else {
        System.out.println("Connected to the existing database.");
        createTables(conn);
        System.out.println("Database has been initialized.");
      }
    } catch (SQLException e) {
      System.out.println("Error while creating the database.");
      e.printStackTrace();
    }
  }

  private boolean tablesExist(Connection conn) throws SQLException {
    DatabaseMetaData dbm = conn.getMetaData();
    ResultSet tables = dbm.getTables(null, null, "user", null);
    return tables.next();
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
          "CHECK (difficulty IN ('EASY', 'MEDIUM', 'HARD'))," +
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
          "id INT AUTO_INCREMENT," +
          "ingredient_id INT," +
          "amount DOUBLE," +
          "expiration_date DATE," +
          "user_id INT," +
          "PRIMARY KEY (id, ingredient_id)," +
          "FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)," +
          "FOREIGN KEY (user_id) REFERENCES \"user\"(id));");

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

      System.out.println("Tables created successfully.");
    }
  }

  // Main method for testing purposes
  public static void main(String[] args){
    no.ntnu.idatt1005.foodi.model.repository.Main.Database dbInitializer = new no.ntnu.idatt1005.foodi.model.repository.Main.Database();
    dbInitializer.initializeDatabase();
  }
}

