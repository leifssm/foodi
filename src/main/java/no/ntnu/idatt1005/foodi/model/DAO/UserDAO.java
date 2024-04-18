package no.ntnu.idatt1005.foodi.model.DAO;

import java.util.ArrayList;
import java.util.List;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;

/**
 * This class is a Data Access Object (DAO) for the User class. It is used to interact with the
 * database. The class contains methods for saving, deleting, updating and retrieving user objects
 * from the database.
 *
 * @author Snake727
 * @version 0.5.0
 */
public class UserDAO {

  /**
   * Saves a user to the database.
   *
   * @param name the name of the user
   */

  public void saveUser(String name) {
    new QueryBuilder("INSERT INTO PUBLIC.\"user\" (name) VALUES (?)")
        .addString(name)
        .executeUpdateSafe();
  }

  /**
   * Deletes a user from the database.
   *
   * @param userId the id of the user to delete
   */

  public void deleteUser(int userId) {
    new QueryBuilder("DELETE FROM PUBLIC.\"user\" WHERE id = ?")
        .addInt(userId)
        .executeUpdateSafe();
  }

  /**
   * Updates the name of a user in the database.
   *
   * @param userId the id of the user to update
   * @param name   the new name of the user
   */
  public void updateUserName(int userId, String name) {
    new QueryBuilder("UPDATE PUBLIC.\"user\" SET name = ? WHERE id = ?")
        .addString(name)
        .addInt(userId)
        .executeUpdateSafe();
  }

  /**
   * Retrieves the id of a user from the database.
   *
   * @param name the name of the user
   * @return the id of the user
   */
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

  /**
   * Retrieves the name of a user from the database.
   *
   * @param userId the id of the user
   * @return the name of the user
   */
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

  /**
   * Checks if a user exists in the database.
   *
   * @param userId the id of the user
   * @return true if the user exists, false otherwise
   */
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

  /**
   * Retrieves all users from the database.
   *
   * @return a list of all users
   */
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