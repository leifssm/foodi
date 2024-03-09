package no.ntnu.idatt1002.demo.model.DAO;

import no.ntnu.idatt1002.demo.model.objects.ShoppingList;

import java.sql.*;

import static no.ntnu.idatt1002.demo.model.repository.Database.*;

public class ShoppingListDAO {

    public void save (ShoppingList obj) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM shopping_list WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

            pstmt.setInt(1, obj.getShoppingListId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Error: ShoppingList with ID " + obj.getShoppingListId() + " already exists in the database.");
            }

            String insertSql = "INSERT INTO shopping_list (id, INGREDIENT_ID, AMOUNT, user_id) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt2 = conn.prepareStatement(insertSql)) {
                pstmt2.setInt(1, obj.getShoppingListId());
                pstmt2.setInt(2, obj.getIngredientId());
                pstmt2.setInt(3, obj.getAmount());
                pstmt2.setInt(4, obj.getUserId());
                pstmt2.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void delete (ShoppingList obj) {
        String sql = "DELETE FROM shopping_list WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, obj.getShoppingListId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}
