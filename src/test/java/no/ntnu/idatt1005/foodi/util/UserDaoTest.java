package no.ntnu.idatt1005.foodi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import no.ntnu.idatt1005.foodi.model.daos.UserDao;
import no.ntnu.idatt1005.foodi.model.repository.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

  private UserDao userDao;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    Database.initializeEmpty();

    // Initialize a new IngredientDAO object
    userDao = new UserDao();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection conn = DriverManager.getConnection(Database.DB_URL, Database.USER,
        Database.PASS);
        Statement stmt = conn.createStatement()) {
      stmt.execute(
          "DROP ALL OBJECTS DELETE FILES"); // This will delete all tables and files associated with the database
    }
  }

  @Test
  void testSaveUser() throws SQLException {
    // Save a new user
    userDao.saveUser("Test User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDao.retrieveUserName(1), "Test User");
  }

  @Test
  void testDeleteUser() throws SQLException {
    // Save a new user
    userDao.saveUser("Test User");

    // Delete the user
    userDao.deleteUser(1);

    // Check if the user exists
    assertFalse(userDao.userExists(1));
  }

  @Test
  void testUpdateUserName() throws SQLException {
    // Save a new user
    userDao.saveUser("Test User");

    // Update the user's name
    userDao.updateUserName(1, "Updated User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDao.retrieveUserName(1), "Updated User");
  }

  @Test
  void testRetrieveUserId() throws SQLException {
    // Save a new user
    userDao.saveUser("Test User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDao.retrieveUserId("Test User"), 1);
  }

  @Test
  void testRetrieveUserName() throws SQLException {
    // Save a new user
    userDao.saveUser("Test User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDao.retrieveUserName(1), "Test User");
  }

  @Test
  void testUserExists() throws SQLException {
    // Save a new user
    userDao.saveUser("Test User");

    // Check if the user exists
    assertTrue(userDao.userExists(1));
  }

  @Test
  void testUserDoesNotExist() throws SQLException {
    // Check if the user exists
    assertFalse(userDao.userExists(1));
  }

  @Test
  void testRetrieveAllUsers() throws SQLException {
    // Save a new user
    userDao.saveUser("Test User");

    // Compare the user with the one retrieved from the database
    assertEquals(userDao.retrieveAllUsers().size(), 1);
  }

  @Test
  void testRetrieveAllUsersWithManyUsers() throws SQLException {
    // Save a new user
    userDao.saveUser("Test User 1");
    userDao.saveUser("Test User 2");
    userDao.saveUser("Test User 3");
    userDao.saveUser("Test User 4");
    userDao.saveUser("Test User 5");

    // Compare the user with the one retrieved from the database
    assertEquals(userDao.retrieveAllUsers().size(), 5);
  }

  @Test
  @DisplayName("Default user should have user id 1")
  void testDefaultUser() throws SQLException {
    userDao.addDefaultUserIfNotExists();
    assertEquals(userDao.retrieveUserName(1), "Default");
  }
}
