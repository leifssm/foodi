package no.ntnu.idatt1005.foodi.model.objects;

/**
 * This class represents a recipe. A recipe has a name, description, difficulty, and duration.
 * The duration is in minutes.
 *
 * @version 0.3.0
 * @author Snake727
 */
public class Recipe {
  private final int id;
  private String name;
  private String description;
  private Difficulty difficulty;
  private int duration;

  public Recipe(int id, String name, String description, Difficulty difficulty, int duration) {
    this.id = id;
    setName(name);
    setDescription(description);
    setDifficulty(difficulty);
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

  public void setName (String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
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
    EASY, MEDIUM, HARD
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
