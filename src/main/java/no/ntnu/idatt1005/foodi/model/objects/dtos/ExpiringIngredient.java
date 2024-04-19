package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.jetbrains.annotations.NotNull;

/**
 * This class represents the amounted ingredient object with an expiration date. It extends the
 * AmountedIngredient class and adds an expirationDate field.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ExpiringIngredient extends AmountedIngredient {

  private LocalDate expirationDate;

  /**
   * Constructor for an amounted ingredient object with an expiration date.
   *
   * @param id             the id of the ingredient
   * @param name           the name of the ingredient
   * @param unit           the unit of the ingredient
   * @param category       the category of the ingredient
   * @param amount         the amount of the ingredient in the unit of the given unit
   * @param expirationDate the expiration date of the ingredient without a timestamp
   */
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

  /**
   * Returns the expiration date of the ingredient.
   *
   * @return the expiration date of the ingredient
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  /**
   * Sets the expiration date of the ingredient.
   *
   * @param expirationDate the new expiration date of the ingredient
   */
  public void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

  /**
   * Returns the expiration date of the ingredient as a legacy Date object. Not ideal, but the views
   * in Inventory depends on it.
   *
   * @return the expiration date of the ingredient as a Date object
   */
  public Date getExpirationDateAsDate() {
    return Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  @Override
  public @NotNull String toString() {
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

  @Override
  public ExpiringIngredient copy() {
    return new ExpiringIngredient(
        getId(),
        getName(),
        getUnit(),
        getCategory(),
        getAmount(),
        expirationDate
    );
  }
}
