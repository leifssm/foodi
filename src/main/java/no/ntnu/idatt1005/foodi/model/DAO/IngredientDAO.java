package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.objects.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import org.jetbrains.annotations.Nullable;

/**
 * This class is responsible for handling the interaction between
 * the Ingredient class and the Database.
 *
 * @author Snake727
 * @version 0.2.0
 */

public class IngredientDAO {
  public void save(@NotNull Ingredient obj) throws SQLException {
    new QueryBuilder("SELECT COUNT(*) FROM ingredient WHERE id = ?")
        .addInt(obj.getId())
        .executeQuery(result -> {
          if (result.next() && result.getInt(1) > 0) {
            throw new SQLException("Error: Ingredient with ID " + obj.getId() + " already exists in the database.");
          }
        });


    new QueryBuilder("INSERT INTO ingredient (id, name, unit, category) VALUES (?, ?, ?, ?)")
        .addInt(obj.getId())
        .addString(obj.getName())
        .addString(obj.getUnit().toString())
        .addString(obj.getCategory().toString())
        .executeUpdate();
  }

  public void update(@NotNull Ingredient obj) {
    new QueryBuilder("UPDATE ingredient SET name = ?, unit = ?, category = ? WHERE id = ?")
        .addString(obj.getName())
        .addString(obj.getUnit().toString())
        .addString(obj.getCategory().toString())
        .addInt(obj.getId())
        .executeUpdateSafe();
  }

  public void delete(@NotNull Ingredient obj) {
    new QueryBuilder("DELETE FROM ingredient WHERE id = ?")
        .addInt(obj.getId())
        .executeUpdateSafe();
  }

  public @Nullable Ingredient retrieve(@NotNull Ingredient obj) {
    return new QueryBuilder("SELECT * FROM ingredient WHERE id = ?")
        .addInt(obj.getId())
        .executeQuerySafe(rs -> {
          if (rs.next()) {
            String name = rs.getString("name");
            Ingredient.IngredientUnit unit = Ingredient.IngredientUnit.valueOf(rs.getString("unit"));
            Ingredient.IngredientCategory category = Ingredient.IngredientCategory.valueOf(rs.getString("category"));

            return new Ingredient(obj.getId(), name, unit, category);
          }
          return null;
        });
  }
}
