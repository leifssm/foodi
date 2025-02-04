package no.ntnu.idatt1005.foodi.model.repository;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;


/**
 * This class is responsible for creating a connection pool to the database.
 */
public class ConnectionPool {

  public static final String DB_URL = "jdbc:h2:~/main"; // This URL will create an H2 database in the user's home directory
  public static final String USER = "main";
  public static final String PASS = "";
  private static final DataSource dataSource;

  static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(DB_URL);
    config.setUsername(USER);
    config.setPassword(PASS);
    config.setMaximumPoolSize(10);
    config.setDriverClassName("org.h2.Driver");

    dataSource = new HikariDataSource(config);
  }

  private ConnectionPool() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Get a connection to the database.
   *
   * @return a connection to the database
   * @throws SQLException if a database access error occurs
   */
  public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}
