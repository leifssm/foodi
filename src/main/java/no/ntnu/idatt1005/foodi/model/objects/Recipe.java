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
  private DietaryTag dietaryTag;
  private int duration;
  private String imagePath;
  private String instruction;

  public Recipe(int id, String name, String description, Difficulty difficulty, DietaryTag dietaryTag, int duration, String imagePath, String instruction) {
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

  public void setName (String name) {
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

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public void setImagePath(String imagePath) {
      this.imagePath = imagePath;
  }

    public String getImagePath() {
        return imagePath;
    }

    public String getInstruction() {
        return instruction;
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

  public enum DietaryTag {
    VEGAN, VEGETARIAN, GLUTEN_FREE, DAIRY_FREE, NUT_FREE, EGG_FREE, SEA_FREE, NONE
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
