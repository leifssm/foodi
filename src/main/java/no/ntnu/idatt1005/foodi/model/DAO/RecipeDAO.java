package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.objects.Recipe;

import java.sql.*;

/**
 * This class is responsible for managing the database access for the Recipe object.
 *
 * @version 0.1.0
 * @author Snake727
 */

public class RecipeDAO {
  private static final String DB_URL = "jdbc:h2:~/test";
  private static final String USER = "sa";
  private static final String PASS = "";

  public void save(Recipe obj) throws SQLException {
    String checkSql = "SELECT COUNT(*) FROM recipe WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

      pstmt.setInt(1, obj.getId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next() && rs.getInt(1) > 0) {
        throw new SQLException("Error: Recipe with ID " + obj.getId() + " already exists in the database.");
      }
    }

    String insertSql = "INSERT INTO recipe (id, name, description, difficulty, duration) VALUES (?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

      pstmt.setInt(1, obj.getId());
      pstmt.setString(2, obj.getName());
      pstmt.setString(3, obj.getDescription());
      pstmt.setString(4, obj.getDifficulty().toString());
      pstmt.setInt(5, obj.getDuration());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void update(Recipe obj) {
    String sql = "UPDATE recipe SET name = ?, description = ?, difficulty = ?, duration = ? WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, obj.getName());
      pstmt.setString(2, obj.getDescription());
      pstmt.setString(3, obj.getDifficulty().toString());
      pstmt.setInt(4, obj.getDuration());
      pstmt.setInt(5, obj.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void delete(Recipe obj) {
    String sql = "DELETE FROM recipe WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, obj.getId());
      pstmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public Recipe retrieve(Recipe obj) {
    String sql = "SELECT * FROM recipe WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, obj.getId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        return new Recipe(
              rs.getInt("id"),
              rs.getString("name"),
              rs.getString("description"),
              Recipe.Difficulty.valueOf(rs.getString("difficulty").toUpperCase()),
              rs.getInt("duration")
        );
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
}
