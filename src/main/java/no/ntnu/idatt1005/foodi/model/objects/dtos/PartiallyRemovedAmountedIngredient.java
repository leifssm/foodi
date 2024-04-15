package no.ntnu.idatt1005.foodi.model.objects.dtos;

public class PartiallyRemovedAmountedIngredient extends AmountedIngredient {

  private double removedAmount;

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

  public double getRemovedAmount() {
    return removedAmount;
  }

  public void setRemovedAmount(double removedAmount) {
    if (removedAmount < 0) {
      throw new IllegalArgumentException("Error: Removed amount cannot be negative.");
    }
    this.removedAmount = removedAmount;
  }

  @Override
  public String toString() {
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
