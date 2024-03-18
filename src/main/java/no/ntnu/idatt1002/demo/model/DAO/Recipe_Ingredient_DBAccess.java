package no.ntnu.idatt1002.demo.model.DAO;

import no.ntnu.idatt1002.demo.model.objects.Recipe_Ingredient;

import java.sql.*;

/**
 * This class is responsible for handling the database access for the Recipe_Ingredient class.
 *
 * @version 0.1.0
 * @author Snake727
 */

public class Recipe_Ingredient_DBAccess {
  private static final String DB_URL = "jdbc:h2:~/test";
  private static final String USER = "sa";
  private static final String PASS = "";

  public void save(Recipe_Ingredient obj) throws SQLException {
    String checkSql = "SELECT COUNT(*) FROM recipe_ingredient WHERE recipe_id = ? AND ingredient_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

      pstmt.setInt(1, obj.getRecipeId());
      pstmt.setInt(2, obj.getIngredientId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next() && rs.getInt(1) > 0) {
        throw new SQLException("Error: Recipe_Ingredient with Recipe ID " + obj.getRecipeId() + " and Ingredient ID " + obj.getIngredientId() + " already exists in the database.");
      }
    }

    String insertSql = "INSERT INTO recipe_ingredient (recipe_id, ingredient_id, amount) VALUES (?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

      pstmt.setInt(1, obj.getRecipeId());
      pstmt.setInt(2, obj.getIngredientId());
      pstmt.setDouble(3, obj.getAmount());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void update(Recipe_Ingredient obj) {
    String sql = "UPDATE recipe_ingredient SET amount = ? WHERE recipe_id = ? AND ingredient_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setDouble(1, obj.getAmount());
      pstmt.setInt(2, obj.getRecipeId());
      pstmt.setInt(3, obj.getIngredientId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void delete(Recipe_Ingredient obj) {
    String sql = "DELETE FROM recipe_ingredient WHERE recipe_id = ? AND ingredient_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, obj.getRecipeId());
      pstmt.setInt(2, obj.getIngredientId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public Recipe_Ingredient retrieve(Recipe_Ingredient obj) {
    String sql = "SELECT * FROM recipe_ingredient WHERE recipe_id = ? AND ingredient_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, obj.getRecipeId());
      pstmt.setInt(2, obj.getIngredientId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        return new Recipe_Ingredient(rs.getInt("recipe_id"), rs.getInt("ingredient_id"), rs.getDouble("amount"));
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
}
