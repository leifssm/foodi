package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.text.DecimalFormat;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the amounted ingredient object. It extends the Ingredient class and adds an
 * amount field.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class AmountedIngredient extends Ingredient {

  private double amount;

  /**
   * Constructor for the amounted ingredient object.
   *
   * @param ingredient the ingredient to copy
   * @param amount     the amount of the ingredient in the unit of the given unit
   */
  public AmountedIngredient(
      @NotNull Ingredient ingredient,
      double amount
  ) {
    this(
        ingredient.getId(),
        ingredient.getName(),
        ingredient.getUnit(),
        ingredient.getCategory(),
        amount
    );
  }

  /**
   * Constructor for the amounted ingredient object.
   *
   * @param id       the id of the ingredient
   * @param name     the name of the ingredient
   * @param unit     the unit of the ingredient
   * @param category the category of the ingredient
   * @param amount   the amount of the ingredient in the unit of the given unit
   */
  public AmountedIngredient(
      int id,
      @NotNull String name,
      @NotNull Unit unit,
      @NotNull Category category,
      double amount
  ) {
    super(id, name, unit, category);
    setAmount(amount);
  }

  /**
   * Returns the amount of the ingredient without a unit.
   *
   * @return the amount of the ingredient.
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Sets the amount of the ingredient.
   *
   * @param amount the new amount of the ingredient.
   * @throws IllegalArgumentException if the amount is negative.
   */
  public void setAmount(double amount) throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException("Error: Amount cannot be negative.");
    }
    this.amount = amount;
  }

  /**
   * Returns the amount of the ingredient with a suffixed unit.
   *
   * @return an amount with a unit.
   */
  public @NotNull String getUnitedAmount() {
    return String.format("%s %s", getAmountString(), getUnit().getName());
  }

  /**
   * Returns the amount of the ingredient as a string.
   *
   * @return the amount of the ingredient as a string.
   */
  public @NotNull String getAmountString() {
    DecimalFormat decimalFormat = new DecimalFormat("0.#");
    return decimalFormat.format(amount);
  }

  @Override
  public @NotNull String toString() {
    return String.format(
        "AmountedIngredient{id=%o, name='%s', unit=%s, category=%s, amount=.2%f}",
        getId(),
        getName(),
        getUnit(),
        getCategory(),
        amount
    );
  }

  /**
   * Returns a copy of the amounted ingredient.
   *
   * @return a copy of the amounted ingredient
   */
  @Override
  public AmountedIngredient copy() {
    return new AmountedIngredient(getId(), getName(), getUnit(), getCategory(), amount);
  }
}
