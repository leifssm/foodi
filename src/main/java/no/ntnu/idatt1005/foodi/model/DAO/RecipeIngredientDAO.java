package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.objects.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.Recipe;
import no.ntnu.idatt1005.foodi.model.objects.RecipeIngredient;
import java.util.HashMap;
import java.util.Map;
import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.*; // DB_URL, USER, PASS

import java.sql.*;

/**
 * This class is responsible for handling the database access for the Recipe_Ingredient class.
 *
 * @version 0.1.0
 * @author Snake727
 */

public class RecipeIngredientDAO {


  public void save(RecipeIngredient obj) throws SQLException {
    String checkSql = "SELECT COUNT(*) FROM recipe_ingredient WHERE recipe_id = ? AND ingredient_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

      pstmt.setInt(1, obj.getRecipe().getId());
      pstmt.setInt(2, obj.getIngredient().getId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next() && rs.getInt(1) > 0) {
        throw new SQLException("Error: Recipe_Ingredient with Recipe ID " + obj.getRecipe().getId() + " and Ingredient ID " + obj.getIngredient().getId() + " already exists in the database.");
      }
    }

    String insertSql = "INSERT INTO recipe_ingredient (recipe_id, ingredient_id, amount) VALUES (?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(insertSql)) {

      pstmt.setInt(1, obj.getRecipe().getId());
      pstmt.setInt(2, obj.getIngredient().getId());
      pstmt.setDouble(3, obj.getAmount());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void update(RecipeIngredient obj) {
    String sql = "UPDATE recipe_ingredient SET amount = ? WHERE recipe_id = ? AND ingredient_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setDouble(1, obj.getAmount());
      pstmt.setInt(2, obj.getRecipe().getId());
      pstmt.setInt(3, obj.getIngredient().getId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public void delete(RecipeIngredient obj) {
    String sql = "DELETE FROM recipe_ingredient WHERE recipe_id = ? AND ingredient_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, obj.getRecipe().getId());
      pstmt.setInt(2, obj.getIngredient().getId());
      pstmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public RecipeIngredient retrieve(RecipeIngredient obj) {
    String sql = "SELECT * FROM recipe_ingredient WHERE recipe_id = ? AND ingredient_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, obj.getRecipe().getId());
      pstmt.setInt(2, obj.getIngredient().getId());
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        RecipeDAO recipeDAO = new RecipeDAO();
        IngredientDAO ingredientDAO = new IngredientDAO();

        // Retrieve the Recipe and Ingredient objects using their respective DAOs
        Recipe recipe = recipeDAO.retrieveById(rs.getInt("recipe_id"));
        Ingredient ingredient = ingredientDAO.retrieveById(rs.getInt("ingredient_id"));

        // Create a new RecipeIngredient object using the retrieved Recipe and Ingredient objects
        return new RecipeIngredient(recipe, ingredient, rs.getDouble("amount"));
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }

  public Map<Integer, Double> getRequiredAmountOfIngredientBasedOnChosenRecipe(int recipeId) {
    Map<Integer, Double> requiredIngredients = new HashMap<>();

    String sql = "SELECT ingredient_id, amount FROM recipe_ingredient WHERE recipe_id = ?";

    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, recipeId);
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        int ingredientId = rs.getInt("ingredient_id");
        double amount = rs.getDouble("amount");
        requiredIngredients.put(ingredientId, amount);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return requiredIngredients;
  }


}
