package no.ntnu.idatt1002.demo.model.DAO;

import no.ntnu.idatt1002.demo.model.objects.Ingredient;
import no.ntnu.idatt1002.demo.model.objects.Inventory;
import no.ntnu.idatt1002.demo.model.objects.User;

import java.sql.*;

import static no.ntnu.idatt1002.demo.model.repository.Database.*;

public class InventoryDatabaseAccess {

    //putt inn Save Ingredient obj and User obj, as parameters, to not need to hardcode the user_id and ingredient_id
    public void save (Inventory obj, Ingredient obj2, User obj3) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM inventory WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

            pstmt.setInt(1, obj.getInventoryId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Error: Inventory with ID " + obj.getInventoryId() + " already exists in the database.");
            }

            String insertSql = "INSERT INTO inventory (id, ingredient_id, amount, EXPIRATION_DATE, user_id) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt2 = conn.prepareStatement(insertSql)) {
                pstmt2.setInt(1, obj.getInventoryId());
                pstmt2.setInt(2, obj2.getId());
                pstmt2.setInt(3, obj.getAmount());
                pstmt2.setDate(4, (Date) obj.getExperationDate());
                pstmt2.setInt(5, obj3.getUserId());
                pstmt2.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }



    }


    public void update_amount_of_ingredient(Inventory obj, int amount, int ingredient_id) {
        String sql = "UPDATE inventory SET amount = ? WHERE ingredient_id = ? AND id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, amount);
            pstmt.setInt(2, ingredient_id);
            pstmt.setInt(3, obj.getInventoryId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    //evt. om noe blir froset, og utløpsdatoen endres
    public void update_expiration_date(Inventory obj, Date date, int ingredient_id) {
        String sql = "UPDATE inventory SET expiration_date = ? WHERE ingredient_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, date);
            pstmt.setInt(2, ingredient_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    //slette inventory
    public void delete_inventory(Inventory obj) {
        String sql = "DELETE FROM inventory WHERE TEST.PUBLIC.INVENTORY.ID = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, obj.getInventoryId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //slette en ingrediens fra inventory

    public void delete_ingredient(int inventory_id, int ingredient_id) {
        String sql = "DELETE FROM inventory WHERE ingredient_id = ? AND id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, ingredient_id);
            pstmt.setInt(2, inventory_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Inventory retrieve(Inventory obj, Ingredient obj2, User obj3){
        String sql = "SELECT * FROM inventory WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, obj.getInventoryId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Inventory(rs.getInt("id"), obj2.getId(), rs.getInt("amount"), rs.getDate("expiration_date"), obj3.getUserId());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}
