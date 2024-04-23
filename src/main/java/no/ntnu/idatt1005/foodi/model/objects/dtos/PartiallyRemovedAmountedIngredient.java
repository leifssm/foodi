package no.ntnu.idatt1005.foodi.model.objects.dtos;

import org.jetbrains.annotations.NotNull;

/**
 * This class represents an amounted ingredient object with a removed amount to be displayed to show
 * the difference.
 */
public class PartiallyRemovedAmountedIngredient extends AmountedIngredient {

  private double removedAmount;

  /**
   * Constructor for the partially removed amounted ingredient object.
   *
   * @param id            the id of the ingredient
   * @param name          the name of the ingredient
   * @param unit          the unit of the ingredient
   * @param category      the category of the ingredient
   * @param amount        the amount of the ingredient in the unit of the given unit
   * @param removedAmount the amount of the ingredient that has been removed
   */
  public PartiallyRemovedAmountedIngredient(
      int id,
      String name,
      Unit unit,
      Category category,
      double amount,
      double removedAmount
  ) {
    super(id, name, unit, category, amount);
    setRemovedAmount(removedAmount);
  }

  /**
   * Returns the amount of the ingredient that has been removed.
   *
   * @return the amount of the ingredient that has been removed
   */
  public double getRemovedAmount() {
    return removedAmount;
  }

  /**
   * Sets the amount of the ingredient that has been removed.
   *
   * @param removedAmount the new amount of the ingredient that has been removed
   * @throws IllegalArgumentException if the removed amount is negative
   */
  public void setRemovedAmount(double removedAmount) throws IllegalArgumentException {
    if (removedAmount < 0) {
      throw new IllegalArgumentException("Error: Removed amount cannot be negative.");
    }
    this.removedAmount = removedAmount;
  }

  /**
   * Returns the total amount subtracted by the removed amount of the ingredient.
   *
   * @return the remaining amount of the ingredient
   */
  public double getRemainingAmount() {
    return getAmount() - removedAmount;
  }

  @Override
  public @NotNull String toString() {
    return String.format(
        "PartiallyRemovedAmountedIngredient{id=%o, name='%s', unit=%s, category=%s, amount=%.2f, removedAmount=%.2f}",
        getId(),
        getName(),
        getUnit(),
        getCategory(),
        getAmount(),
        removedAmount
    );
  }
}
