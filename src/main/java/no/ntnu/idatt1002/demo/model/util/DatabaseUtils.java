package no.ntnu.idatt1002.demo.model.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that provides utility methods for database operations
 *
 * @version 0.1.0
 * @author Snake727
 */


public class DatabaseUtils {
  /**
   * Method to initialize the database
   * @return a connection to the database
   */

  public static Connection initializeDatabase() {
    // JDBC URL for H2 persistent database
    // Method should also check if a database is already created

    String jdbcURL = "jdbc:h2:~/testdb"; // This creates a persistent database named 'testdb'

    try {
      // Load H2 driver
      Class.forName("org.h2.Driver");

      // Establish a connection to the database
      return DriverManager.getConnection(jdbcURL, "", "");

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void createTables(Connection connection) {
    try {
      Statement statement = connection.createStatement();
      // Create tables
      String sql = "CREATE TABLE IF NOT EXISTS test_table (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255))";
      statement.execute(sql);
      // More table creation statements here
      System.out.println("Tables have been created!");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Method to populate the database with initial data
  public static void initializeData(Connection connection) {
    // Implementation code for initial data insertion
  }

  // Other utility methods for database operations can be added here
}
