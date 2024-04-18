package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.DAO.QueryBuilder;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void saveUser(String name) {
        new QueryBuilder("INSERT INTO PUBLIC.\"user\" (name) VALUES (?)")
              .addString(name)
              .executeUpdateSafe();
    }

    public void deleteUser(int userId) {
        new QueryBuilder("DELETE FROM PUBLIC.\"user\" WHERE id = ?")
              .addInt(userId)
              .executeUpdateSafe();
    }

    public void updateUserName(int userId, String name) {
        new QueryBuilder("UPDATE PUBLIC.\"user\" SET name = ? WHERE id = ?")
              .addString(name)
              .addInt(userId)
              .executeUpdateSafe();
    }

    public int retrieveUserId(String name) {
      Integer result = new QueryBuilder("SELECT id FROM PUBLIC.\"user\" WHERE name = ?")
            .addString(name)
            .executeQuerySafe(rs -> {
              if (rs.next()) {
                return rs.getInt(1);
              }
              return null;
            });

      return (result != null) ? result : -1; // or any default value
    }

    public String retrieveUserName(int userId) {
        return new QueryBuilder("SELECT name FROM PUBLIC.\"user\" WHERE id = ?")
              .addInt(userId)
              .executeQuerySafe(rs -> {
                  if (rs.next()) {
                      return rs.getString(1);
                  }
                  return null;
              });
    }


  public boolean userExists(int userId) {
    Integer count = new QueryBuilder("SELECT COUNT(*) FROM PUBLIC.\"user\" WHERE id = ?")
          .addInt(userId)
          .executeQuerySafe(rs -> {
            if (rs.next()) {
              return rs.getInt(1);
            }
            return null;
          });
    return count != null && count > 0;
  }

  public List<User> retrieveAllUsers() {
    List<User> users = new ArrayList<>();
    new QueryBuilder("SELECT * FROM PUBLIC.\"user\"")
          .executeQuerySafe(rs -> {
            while (rs.next()) {
              users.add(new User(rs.getInt("id"), rs.getString("name")));
            }
            return users;
          });
    return users;
  }
}