package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.DAO.QueryBuilder;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;

public class UserDAO {

    public void save(User obj) {
        new QueryBuilder("INSERT INTO PUBLIC.\"user\" (id, name) VALUES (?, ?)")
              .addInt(obj.userId())
              .addString(obj.name())
              .executeUpdateSafe();
    }

    public void delete(User obj) {
        new QueryBuilder("DELETE FROM PUBLIC.\"user\" WHERE id = ?")
              .addInt(obj.userId())
              .executeUpdateSafe();
    }

    public User retrieve(User obj) {
        return new QueryBuilder("SELECT * FROM PUBLIC.\"user\" WHERE id = ?")
              .addInt(obj.userId())
              .executeQuerySafe(rs -> {
                  if (rs.next()) {
                      return new User(rs.getInt("id"), rs.getString("name"));
                  }
                  return null;
              });
    }

    public boolean userExists(User user) {
        Integer result = new QueryBuilder("SELECT COUNT(*) FROM PUBLIC.\"user\" WHERE id = ?")
              .addInt(user.userId())
              .executeQuerySafe(rs -> {
                  if (rs.next()) {
                      return rs.getInt(1);
                  }
                  return null;
              });

        return result != null && result > 0;
    }
}