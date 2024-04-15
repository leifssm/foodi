package no.ntnu.idatt1005.foodi.model.objects.dtos;

import org.jetbrains.annotations.NotNull;

public class AmountedIngredient extends Ingredient {

  private double amount;

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

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    if (amount < 0) {
      throw new IllegalArgumentException("Error: Amount cannot be negative.");
    }
    this.amount = amount;
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

}
