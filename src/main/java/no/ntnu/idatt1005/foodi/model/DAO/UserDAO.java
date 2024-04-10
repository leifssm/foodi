package no.ntnu.idatt1005.foodi.model.DAO;
import no.ntnu.idatt1005.foodi.model.objects.User;

import java.sql.*;

import static no.ntnu.idatt1005.foodi.model.repository.Main.DatabaseMain.*;

public class UserDAO {


    public void save (User obj) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM MAIN.PUBLIC.\"user\" WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

            pstmt.setInt(1, obj.getUserId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                throw new SQLException("Error: User with ID " + obj.getUserId() + " already exists in the database.");
            }

            String insertSql = "INSERT INTO MAIN.PUBLIC.\"user\" (id, name) VALUES (?, ?)";

            try (PreparedStatement pstmt2 = conn.prepareStatement(insertSql)) {
                pstmt2.setInt(1, obj.getUserId());
                pstmt2.setString(2, obj.getName());
                pstmt2.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public void delete (User obj) {
        String sql = "DELETE FROM MAIN.PUBLIC.\"user\" WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, obj.getUserId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User retrieve (User obj) {
        String sql = "SELECT * FROM MAIN.PUBLIC.\"user\" WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, obj.getUserId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

}
