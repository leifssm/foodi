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
  private String imagePath;
  private String instruction;

  /**
   * Creates a recipe object with the given id, name, description, difficulty, dietary tag, and
   * duration.
   *
   * @param id          the id of the recipe
   * @param name        the name of the recipe
   * @param description the description of the recipe
   * @param difficulty  the difficulty of the recipe
   * @param dietaryTag  the dietary tag of the recipe
   * @param duration    the duration of the recipe in minutes
   */
  public Recipe(
      int id,
      @NotNull String name,
      @NotNull String description,
      @NotNull Difficulty difficulty,
      @NotNull DietaryTag dietaryTag,
      int duration,
      @NotNull String imagePath,
      @NotNull String instruction
  ) {
    this.id = id;
    setName(name);
    setDescription(description);
    setDifficulty(difficulty);
    setDietaryTag(dietaryTag);
    // Duration is in minutes
    setDuration(duration);
    setImagePath(imagePath);
    setInstruction(instruction);
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
  public @NotNull String getName() {
    return name;
  }

  /**
   * Set the name of the recipe.
   *
   * @param name the new name of the recipe
   */
  public void setName(@NotNull String name) {
    this.name = name;
  }

  /**
   * Get the difficulty of the recipe.
   *
   * @return the difficulty of the recipe
   */
  public @NotNull Difficulty getDifficulty() {
    return difficulty;
  }

  /**
   * Set the difficulty of the recipe.
   *
   * @param difficulty the new difficulty of the recipe
   */
  public void setDifficulty(@NotNull Difficulty difficulty) {
    this.difficulty = difficulty;
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
   * Set the duration of the recipe. The number represents the duration in minutes.
   *
   * @param duration the new duration of the recipe
   */
  public void setDuration(int duration) {
    this.duration = duration;
  }

  /**
   * Get the description of the recipe.
   *
   * @return the description of the recipe.
   */
  public @NotNull String getDescription() {
    return description;
  }

  /**
   * Set the description of the recipe.
   *
   * @param description the new description of the recipe
   */
  public void setDescription(@NotNull String description) {
    this.description = description;
  }

  /**
   * Get the dietary tag of the recipe.
   *
   * @return the dietary tag of the recipe
   */
  public @NotNull DietaryTag getDietaryTag() {
    return dietaryTag;
  }

  /**
   * Set the dietary tag of the recipe.
   *
   * @param dietaryTag the new dietary tag of the recipe
   */
  public void setDietaryTag(@NotNull DietaryTag dietaryTag) {
    this.dietaryTag = dietaryTag;
  }

  public @NotNull String getImagePath() {
    return imagePath;
  }

  /**
   * Set the image path of the recipe.
   *
   * @param imagePath the new image path of the recipe
   */
  public void setImagePath(@NotNull String imagePath) {
    this.imagePath = imagePath;
  }

  public @NotNull String getInstruction() {
    return instruction;
  }

  /**
   * Set the instruction of the recipe.
   *
   * @param instruction the new instruction of the recipe
   */
  public void setInstruction(@NotNull String instruction) {
    this.instruction = instruction;
  }


  @Override
  public @NotNull String toString() {
    return "Recipe{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", difficulty=" + difficulty +
        ", duration=" + duration +
        ", imagePath='" + imagePath + '\'' +
        ", instruction='" + instruction + '\'' +
        '}';
  }

  /**
   * This enum represents the difficulty of a recipe. The difficulty can be easy, medium, or hard.
   */
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

    /**
     * Returns the difficulty from the given key.
     *
     * @param key the key of the difficulty
     * @return the difficulty enum
     */
    public static @NotNull Difficulty fromKey(@NotNull String key) {
      for (Difficulty difficulty : Difficulty.values()) {
        if (difficulty.getDatabaseKey().equals(key)) {
          return difficulty;
        }
      }
      throw new IllegalArgumentException("No difficulty found for key: " + key);
    }

    /**
     * Returns the key of the difficulty to be used with the database.
     *
     * @return the key of the difficulty
     */
    public @NotNull String getDatabaseKey() {
      return databaseKey;
    }

    /**
     * Returns the difficulty as a string to be used for display purposes.
     *
     * @return the difficulty as a string
     */
    public @NotNull String getDifficulty() {
      return difficulty;
    }

    /**
     * Returns the database key of the difficulty.
     *
     * @return the database key of the difficulty
     */
    public @NotNull String toString() {
      return databaseKey;
    }
  }

  /**
   * This enum represents the dietary tag of a recipe,
   */
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

    /**
     * Returns the dietary tag from the given key.
     *
     * @param key the key of the dietary tag
     * @return the dietary tag enum
     */
    public static @NotNull DietaryTag fromKey(@NotNull String key) {
      for (DietaryTag tag : DietaryTag.values()) {
        if (tag.getDatabaseKey().equals(key)) {
          return tag;
        }
      }
      throw new IllegalArgumentException("No dietary tag found for key: " + key);
    }

    /**
     * Returns the key of the dietary tag to be used with the database.
     *
     * @return the key of the dietary tag
     */
    public @NotNull String getDatabaseKey() {
      return databaseKey;
    }

    /**
     * Returns the name of the dietary tag.
     *
     * @return the name of the dietary tag
     */
    public @NotNull String getName() {
      return name;
    }

    /**
     * Returns the database key of the dietary tag.
     *
     * @return the database key of the dietary tag
     */
    public @NotNull String toString() {
      return databaseKey;
    }
  }
}
