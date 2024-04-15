package no.ntnu.idatt1005.foodi.model.objects.dtos;

import org.jetbrains.annotations.NotNull;

/**
 * This class represents a recipe. A recipe has a name, description, difficulty, and duration. The
 * duration is in minutes.
 *
 * @author Snake727
 * @version 0.4.0
 */
public class Recipe {

  private final int id;
  private String name;
  private String description;
  private Difficulty difficulty;
  private DietaryTag dietaryTag;
  private int duration;

  public Recipe(int id, String name, String description, Difficulty difficulty,
      DietaryTag dietaryTag, int duration) {
    this.id = id;
    setName(name);
    setDescription(description);
    setDifficulty(difficulty);
    setDietaryTag(dietaryTag);
    // Duration is in minutes
    setDuration(duration);
  }

  /**
   * Get the id of the recipe.
   *
   * @return the id of the recipe
   */
  public int getId() {
    return id;
  }

  /**
   * Get the name of the recipe.
   *
   * @return the name of the recipe
   */
  public String getName() {
    return name;
  }

  /**
   * Get the difficulty of the recipe.
   *
   * @return the difficulty of the recipe
   */
  public Difficulty getDifficulty() {
    return difficulty;
  }

  /**
   * Get the duration of the recipe. The number represents the duration in minutes.
   *
   * @return the duration of the recipe
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Get the description of the recipe.
   *
   * @return the description of the recipe.
   */
  public String getDescription() {
    return description;
  }

  public DietaryTag getDietaryTag() {
    return dietaryTag;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public void setDietaryTag(DietaryTag dietaryTag) {
    this.dietaryTag = dietaryTag;
  }

  /**
   * Set the duration of the recipe. The number represents the duration in minutes.
   *
   * @param duration
   */

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public enum Difficulty {
    EASY("EASY", "Easy"),
    MEDIUM("MEDIUM", "Medium"),
    HARD("HARD", "Hard");

    private final @NotNull String databaseKey;
    private final @NotNull String difficulty;

    Difficulty(@NotNull String databaseKey, @NotNull String difficulty) {
      this.databaseKey = databaseKey;
      this.difficulty = difficulty;
    }

    public @NotNull String getDifficulty() {
      return difficulty;
    }

    public @NotNull String getDatabaseKey() {
      return databaseKey;
    }

    public static @NotNull Difficulty fromKey(@NotNull String key) {
      for (Difficulty difficulty : Difficulty.values()) {
        if (difficulty.getDatabaseKey().equals(key)) {
          return difficulty;
        }
      }
      throw new IllegalArgumentException("No difficulty found for key: " + key);
    }

    public @NotNull String toString() {
      return databaseKey;
    }
  }

  public enum DietaryTag {
    VEGAN("VEGAN", "Vegan"),
    VEGETARIAN("VEGETARIAN", "Vegetarian"),
    GLUTEN_FREE("GLUTEN_FREE", "Gluten-free"),
    DAIRY_FREE("DAIRY_FREE", "Dairy-free"),
    NUT_FREE("NUT_FREE", "Nut-free"),
    EGG_FREE("EGG_FREE", "Egg-free"),
    SEA_FREE("SEA_FREE", "Sea-free"),
    NONE("NONE", "None");
    private final @NotNull String databaseKey;
    private final @NotNull String name;

    DietaryTag(@NotNull String databaseKey, @NotNull String name) {
      this.databaseKey = databaseKey;
      this.name = name;
    }

    public @NotNull String getName() {
      return name;
    }

    public @NotNull String getDatabaseKey() {
      return databaseKey;
    }

    public static @NotNull DietaryTag fromKey(@NotNull String key) {
      for (DietaryTag tag : DietaryTag.values()) {
        if (tag.getDatabaseKey().equals(key)) {
          return tag;
        }
      }
      throw new IllegalArgumentException("No dietary tag found for key: " + key);
    }

    public @NotNull String toString() {
      return databaseKey;
    }
  }

  @Override
  public String toString() {
    return "Recipe{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", difficulty=" + difficulty +
        ", duration=" + duration +
        '}';
  }
}
