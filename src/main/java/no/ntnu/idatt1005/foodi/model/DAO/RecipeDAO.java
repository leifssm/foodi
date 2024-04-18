package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import org.jetbrains.annotations.NotNull;

/**
 * This class is responsible for handling the usage of database operations regarding stored recipes in the database.
 *
 * @version 0.2.0
 */
public class RecipeDAO {
  public void saveRecipeObject(@NotNull Recipe obj) {
    new QueryBuilder("INSERT INTO recipe (id, name, description, difficulty, dietary_tag, duration, imagePath, instruction) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")
          .addInt(obj.getId())
          .addString(obj.getName())
          .addString(obj.getDescription())
          .addString(obj.getDifficulty().toString())
          .addString(obj.getDietaryTag().toString())
          .addString(obj.getImagePath())
          .addString(obj.getInstruction())
          .addInt(obj.getDuration())
          .executeUpdateSafe();
  }

  public void saveRecipe(String name, String description, String difficulty, String dietaryTag, int duration, String imagePath, String instruction){
    new QueryBuilder("INSERT INTO recipe (name, description, difficulty, dietary_tag, duration, imagePath, instruction) VALUES (?, ?, ?, ?, ?, ?, ?)")
          .addString(name)
          .addString(description)
          .addString(difficulty)
          .addString(dietaryTag)
          .addInt(duration)
          .addString(imagePath)
          .addString(instruction)
          .executeUpdateSafe();
  }

  public void updateRecipeById(int id, String name, String description, String difficulty, String dietaryTag, int duration, String imagePath, String instruction){
    new QueryBuilder("UPDATE recipe SET name = ?, description = ?, difficulty = ?, dietary_tag = ?, duration = ?, imagePath = ?, instruction = ? WHERE id = ?")
          .addString(name)
          .addString(description)
          .addString(difficulty)
          .addString(dietaryTag)
          .addInt(duration)
          .addString(imagePath)
          .addString(instruction)
          .addInt(id)
          .executeUpdateSafe();
  }

  public void update(@NotNull Recipe obj) {
    new QueryBuilder("UPDATE recipe SET name = ?, description = ?, difficulty = ?, duration = ?, imagePath = ?, instruction = ? WHERE id = ?")
          .addString(obj.getName())
          .addString(obj.getDescription())
          .addString(obj.getDifficulty().toString())
          .addInt(obj.getDuration())
          .addString(obj.getImagePath())
          .addString(obj.getInstruction())
          .addInt(obj.getId())
          .executeUpdateSafe();
  }

  public void delete(@NotNull Recipe obj) {
    new QueryBuilder("DELETE FROM recipe WHERE id = ?")
          .addInt(obj.getId())
          .executeUpdateSafe();
  }

  public void deleteRecipeById(int id) {
    new QueryBuilder("DELETE FROM recipe WHERE id = ?")
          .addInt(id)
          .executeUpdateSafe();
  }

  public Recipe retrieve(@NotNull Recipe obj) {
    return new QueryBuilder("SELECT * FROM recipe WHERE id = ?")
          .addInt(obj.getId())
          .executeQuerySafe(rs -> {
            if (rs.next()) {
              return new Recipe(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    Recipe.Difficulty.valueOf(rs.getString("difficulty").toUpperCase()),
                    Recipe.DietaryTag.valueOf(rs.getString("dietary_tag").toUpperCase()),
                    rs.getInt("duration"),
                    rs.getString("imagePath"),
                    rs.getString("instruction")
              );
            }
            return null;
          });
  }

  public Recipe retrieveById(int id) {
    return new QueryBuilder("SELECT * FROM recipe WHERE id = ?")
          .addInt(id)
          .executeQuerySafe(rs -> {
            if (rs.next()) {
              return new Recipe(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    Recipe.Difficulty.valueOf(rs.getString("difficulty").toUpperCase()),
                    Recipe.DietaryTag.valueOf(rs.getString("dietary_tag").toUpperCase()),
                    rs.getInt("duration"),
                    rs.getString("imagePath"),
                    rs.getString("instruction")
              );
            }
            return null;
          });
  }
}