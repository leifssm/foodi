package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;

import java.sql.SQLException;

/**
 * This class is responsible for handling the interaction between
 * the Ingredient class and the Database.
 *
 * @version 0.3.0
 * @author Snake727
 */

public class IngredientDAO {

  public int countIngredientItems() {
    Integer result = new QueryBuilder("SELECT COUNT(*) FROM ingredient")
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            return rs.getInt(1);
          }
          return null;
        });

    return result != null ? result : 0;
  }

  public void saveIngredient(Ingredient obj) throws SQLException {
    new QueryBuilder("INSERT INTO ingredient (id, name, unit, category) VALUES (?, ?, ?, ?)")
        .addInt(obj.getId())
        .addString(obj.getName())
        .addString(obj.getUnit().getDatabaseKey())
        .addString(obj.getCategory().getDatabaseKey())
        .executeUpdateSafe();
  }

  public void updateIngredient(Ingredient obj) {
    new QueryBuilder("UPDATE ingredient SET name = ?, unit = ?, category = ? WHERE id = ?")
        .addString(obj.getName())
        .addString(obj.getUnit().getDatabaseKey())
        .addString(obj.getCategory().getDatabaseKey())
        .addInt(obj.getId())
        .executeUpdateSafe();
  }

  public void deleteIngredient(Ingredient obj) {
    new QueryBuilder("DELETE FROM ingredient WHERE id = ?")
        .addInt(obj.getId())
        .executeUpdateSafe();
  }

  public Ingredient retrieveIngredient(Ingredient obj) {
    return new QueryBuilder("SELECT * FROM ingredient WHERE id = ?")
        .addInt(obj.getId())
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            String name = rs.getString("name");
            Ingredient.Unit unit = Ingredient.Unit.fromKey(rs.getString("unit"));
            Ingredient.Category category = Ingredient.Category.fromKey(rs.getString("category"));

            return new Ingredient(obj.getId(), name, unit, category);
          }
          return null;
        });
  }

  /**
   * Retrieve an ingredient from the database by its ID.
   * @param id the id of the ingredient to retrieve.
   * @return The Ingredient object. Returns null if nothing was found.
   */
  public Ingredient retrieveIngredientById(int id) {
    return new QueryBuilder("SELECT * FROM ingredient WHERE id = ?")
        .addInt(id)
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            String name = rs.getString("name");
            Ingredient.Unit unit = Ingredient.Unit.fromKey(rs.getString("unit"));
            Ingredient.Category category = Ingredient.Category.fromKey(rs.getString("category"));

            return new Ingredient(id, name, unit, category);
          }
          return null;
        });
  }

  public Ingredient retrieveExpiringIngredients() {
    return new QueryBuilder("SELECT * FROM ingredient WHERE expiration_date < CURRENT_DATE")
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            String name = rs.getString("name");
            Ingredient.Unit unit = Ingredient.Unit.fromKey(rs.getString("unit"));
            Ingredient.Category category = Ingredient.Category.fromKey(rs.getString("category"));

            return new Ingredient(rs.getInt("id"), name, unit, category);
          }
          return null;
        });
  }
}