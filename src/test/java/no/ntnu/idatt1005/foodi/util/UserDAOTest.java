package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
  private UserDAO userDAO;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    DatabaseMain databaseMain = new DatabaseMain();
    databaseMain.initializeDatabaseMain();

    // Initialize a new IngredientDAO object
    userDAO = new UserDAO();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection conn = DriverManager.getConnection(DatabaseMain.DB_URL, DatabaseMain.USER, DatabaseMain.PASS);
         Statement stmt = conn.createStatement()) {
      stmt.execute("DROP ALL OBJECTS DELETE FILES"); // This will delete all tables and files associated with the database
    }
  }

  @Test
  void testSaveUser() throws SQLException {
    // Save a new user
    userDAO.saveUser("Test User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDAO.retrieveUserName(1), "Test User");
  }

  @Test
  void testDeleteUser() throws SQLException {
    // Save a new user
    userDAO.saveUser("Test User");

    // Delete the user
    userDAO.deleteUser(1);

    // Check if the user exists
    assertFalse(userDAO.userExists(1));
  }

  @Test
  void testUpdateUserName() throws SQLException {
    // Save a new user
    userDAO.saveUser("Test User");

    // Update the user's name
    userDAO.updateUserName(1, "Updated User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDAO.retrieveUserName(1), "Updated User");
  }

  @Test
  void testRetrieveUserId() throws SQLException {
    // Save a new user
    userDAO.saveUser("Test User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDAO.retrieveUserId("Test User"), 1);
  }

  @Test
  void testRetrieveUserName() throws SQLException {
    // Save a new user
    userDAO.saveUser("Test User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDAO.retrieveUserName(1), "Test User");
  }

  @Test
  void testUserExists() throws SQLException {
    // Save a new user
    userDAO.saveUser("Test User");

    // Check if the user exists
    assertTrue(userDAO.userExists(1));
  }

  @Test
  void testUserDoesNotExist() throws SQLException {
    // Check if the user exists
    assertFalse(userDAO.userExists(1));
  }

}
