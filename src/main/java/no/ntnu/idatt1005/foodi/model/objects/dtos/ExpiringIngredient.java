package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.time.LocalDate;

public class ExpiringIngredient extends AmountedIngredient {

  private LocalDate expirationDate;

  public ExpiringIngredient(
      int id,
      String name,
      Unit unit,
      Category category,
      double amount,
      LocalDate expirationDate
  ) {
    super(id, name, unit, category, amount);
    setExpirationDate(expirationDate);
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  @Override
  public String toString() {
    return String.format(
        "ExpiringIngredient{id=%o, name='%s', unit=%s, "
            + "category=%s, amount=%.2f, expirationDate='%s'}",
        getId(),
        getName(),
        getUnit(),
        getCategory(),
        getAmount(),
        expirationDate
    );
  }

}
