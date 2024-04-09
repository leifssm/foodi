package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.objects.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.Inventory;
import no.ntnu.idatt1005.foodi.model.objects.User;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static no.ntnu.idatt1005.foodi.model.repository.Main.Database.*;

public class InventoryDAO {

    /*
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


    */


    public void save (Inventory obj, Ingredient obj2, User obj3) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM inventory WHERE id = ? AND ingredient_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

            pstmt.setInt(1, obj.getInventoryId());
            pstmt.setInt(2, obj2.getId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // If the ingredient already exists in the inventory, update the amount
                String updateSql = "UPDATE inventory SET amount = amount + ? WHERE id = ? AND ingredient_id = ?";

                try (PreparedStatement pstmt2 = conn.prepareStatement(updateSql)) {
                    pstmt2.setInt(1, obj.getAmount());
                    pstmt2.setInt(2, obj.getInventoryId());
                    pstmt2.setInt(3, obj2.getId());
                    pstmt2.executeUpdate();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                // If the ingredient does not exist in the inventory, insert a new record
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

    public Map<Integer, Double> getTotalAmountPerIngredient(int inventory_id) {
        String sql = "SELECT ingredient_id, SUM(amount) FROM inventory WHERE INVENTORY.ID = ? GROUP BY ingredient_id";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, inventory_id);
            ResultSet rs = pstmt.executeQuery();

            Map<Integer, Double> ingredientAmountMap = new HashMap<>();
            while (rs.next()) {
                ingredientAmountMap.put(rs.getInt(1), rs.getDouble(2));
            }

            return ingredientAmountMap;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}
