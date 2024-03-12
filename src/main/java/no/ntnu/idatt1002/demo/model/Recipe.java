package no.ntnu.idatt1002.demo.model;

import java.time.Duration;

/**
 * This class represents a recipe.
 *
 * @version 0.1.0
 * @author Snake727
 */
public class Recipe {
  private int id;
  private String name;
  private String description;
  private Difficulty difficulty;
  private Duration duration;

  public Recipe(int id, String name, String description, Difficulty difficulty, Duration duration) {
    this.id = id;
    setName(name);
    setDescription(description);
    setDifficulty(difficulty);
    setDuration(duration);
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public Duration getDuration() {
    return duration;
  }

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

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public enum Difficulty {
    EASY, MEDIUM, HARD
  }
}
