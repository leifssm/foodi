package no.ntnu.idatt1002.demo.model.DAO;

import no.ntnu.idatt1002.demo.model.Ingredient;

import java.sql.*;

/**
 * This class is responsible for handling the interaction between
 * the Ingredient class and the Database.
 *
 * @version 0.2.0
 * @author Snake727
 */

public class IngredientDatabaseAccess {
  private static final String DB_URL = "jdbc:h2:~/test";
  private static final String USER = "sa";
  private static final String PASS = "";

  public void save(Ingredient obj) throws SQLException {
    String checkSql = "SELECT COUNT(*) FROM ingredient WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

      pstmt.setInt(1, obj.getId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next() && rs.getInt(1) > 0) {
        throw new SQLException("Error: Ingredient with ID " + obj.getId() + " already exists in the database.");
      }
    }

    String insertSql = "INSERT INTO ingredient (id, name, unit, category) VALUES (?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

      pstmt.setInt(1, obj.getId());
      pstmt.setString(2, obj.getName());
      pstmt.setString(3, obj.getUnit().toString());
      pstmt.setString(4, obj.getCategory().toString());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void update(Ingredient obj) {
    String sql = "UPDATE ingredient SET name = ?, unit = ?, category = ? WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, obj.getName());
      pstmt.setString(2, obj.getUnit().toString());
      pstmt.setString(3, obj.getCategory().toString());
      pstmt.setInt(4, obj.getId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void delete(Ingredient obj) {
    String sql = "DELETE FROM ingredient WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, obj.getId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public Ingredient retrieve(Ingredient obj) {
    String sql = "SELECT * FROM ingredient WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, obj.getId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        String name = rs.getString("name");
        Ingredient.IngredientUnit unit = Ingredient.IngredientUnit.valueOf(rs.getString("unit"));
        Ingredient.IngredientCategory category = Ingredient.IngredientCategory.valueOf(rs.getString("category"));

        return new Ingredient(obj.getId(), name, unit, category);
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    // Return null if the ingredient was not found
    return null;
  }
}
