package no.ntnu.idatt1005.foodi.model.DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.*;

public class ShoppingListDAO {

    public void save(Map<Integer, Double> shoppingList, int userId, int listId) throws SQLException {
        String deleteSql = "DELETE FROM shopping_list WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement deletePstmt = conn.prepareStatement(deleteSql)) {

            // Slett eksisterende oppføringer for brukeren for å unngå duplikater
            deletePstmt.setInt(1, userId);
            deletePstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error when trying to delete existing shopping list for user " + userId + ": " + e.getMessage());
        }

        String insertSql = "INSERT INTO shopping_list (SHOPPINGLIST_ID, ITEM_ID, INGREDIENT_ID, AMOUNT, USER_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement insertPstmt = conn.prepareStatement(insertSql)) {

            int item_nr = 1;
            for (Map.Entry<Integer, Double> entry : shoppingList.entrySet()) {
                insertPstmt.setInt(1, listId);
                insertPstmt.setInt(2, item_nr++);
                insertPstmt.setInt(3, entry.getKey());
                insertPstmt.setDouble(4, entry.getValue());
                insertPstmt.setInt(5, userId);
                insertPstmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public void deleteAllForUser(int userId) {
        String sql = "DELETE FROM shopping_list WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error when trying to delete shopping list for user " + userId + ": " + e.getMessage());
        }
    }


    public Map<Integer, Double> getShoppingListForUser(int userId) {
        String sql = "SELECT * FROM shopping_list WHERE user_id = ?";

        Map<Integer, Double> current_shoppingList = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                current_shoppingList.put(rs.getInt("ingredient_id"), rs.getDouble("amount"));
            }
            return current_shoppingList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }




}
