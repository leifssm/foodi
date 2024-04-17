package no.ntnu.idatt1005.foodi.util;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.ShoppingListDAO;
import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingListTest {

  private static ShoppingListDAO shoppingListDAO;
  private static UserDAO userDAO;
  private static IngredientDAO ingredientDAO;
  private static User testUser;
  private static Ingredient testIngredient1;
  private static Ingredient testIngredient2;

  @BeforeEach
  public void setUp() throws SQLException {
    // Initialize the main database
    DatabaseMain databaseMain = new DatabaseMain();
    databaseMain.initializeDatabaseMain();

    // Initialize a new IngredientDAO object
    ingredientDAO = new IngredientDAO();
    userDAO = new UserDAO();
    shoppingListDAO = new ShoppingListDAO();
  }

  @AfterEach
  public void tearDown() throws SQLException {
    try (Connection conn = DriverManager.getConnection(DatabaseMain.DB_URL, DatabaseMain.USER, DatabaseMain.PASS);
         Statement stmt = conn.createStatement()) {
      stmt.execute("DROP ALL OBJECTS DELETE FILES"); // This will delete all tables and files associated with the database
    }
  }

  @Test
  @Order(1)
  public void testSaveShoppingList() throws SQLException {
    Map<Integer, Double> shoppingList = new HashMap<>();
    shoppingList.put(testIngredient1.getId(), 2.0);
    shoppingList.put(testIngredient2.getId(), 3.0);

    shoppingListDAO.save(shoppingList, testUser.userId(), 1);

    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(testUser.userId());

    assertEquals(shoppingList, retrievedShoppingList, "The saved shopping list should match the retrieved shopping list.");
  }

  @Test
  @Order(2)
  public void testDeleteShoppingList() {
    shoppingListDAO.deleteAllForUser(testUser.userId());

    Map<Integer, Double> retrievedShoppingList = shoppingListDAO.getShoppingListForUser(testUser.userId());

    assertTrue(retrievedShoppingList.isEmpty(), "The shopping list should be empty after deletion.");
  }
}