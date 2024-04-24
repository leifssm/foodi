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
  private Boolean isFrozen;
  private int inventoryId = -1;

  /**
   * Constructor for an amounted ingredient object with an expiration date and an inventory id.
   *
   * @param id             the id of the ingredient
   * @param name           the name of the ingredient
   * @param unit           the unit of the ingredient
   * @param category       the category of the ingredient
   * @param amount         the amount of the ingredient in the unit of the given unit
   * @param expirationDate the expiration date of the ingredient without a timestamp
   * @param isFrozen       whether the ingredient is frozen or not
   * @param inventoryId    the id which the element (not ingredient) is stored as in backend
   */
  public ExpiringIngredient(
      int id,
      String name,
      Unit unit,
      Category category,
      double amount,
      LocalDate expirationDate,
      Boolean isFrozen,
      int inventoryId
  ) {
    this(id, name, unit, category, amount, expirationDate, isFrozen);
    setInventoryId(inventoryId);
  }

  /**
   * Constructor for extending an amounted ingredient object with an expiration date.
   *
   * @param amountedIngredient the amounted ingredient object to extend
   * @param expirationDate     the expiration date of the ingredient without a timestamp
   * @param isFrozen           whether the ingredient is frozen or not
   */
  public ExpiringIngredient(
      AmountedIngredient amountedIngredient,
      LocalDate expirationDate,
      Boolean isFrozen
  ) {
    this(
        amountedIngredient.getId(),
        amountedIngredient.getName(),
        amountedIngredient.getUnit(),
        amountedIngredient.getCategory(),
        amountedIngredient.getAmount(),
        expirationDate,
        isFrozen
    );
  }

  /**
   * Constructor for an amounted ingredient object with an expiration date.
   *
   * @param id             the id of the ingredient
   * @param name           the name of the ingredient
   * @param unit           the unit of the ingredient
   * @param category       the category of the ingredient
   * @param amount         the amount of the ingredient in the unit of the given unit
   * @param expirationDate the expiration date of the ingredient without a timestamp
   * @param isFrozen       whether the ingredient is frozen or not
   */
  public ExpiringIngredient(
      int id,
      String name,
      Unit unit,
      Category category,
      double amount,
      LocalDate expirationDate,
      Boolean isFrozen
  ) {
    super(id, name, unit, category, amount);
    setExpirationDate(expirationDate);
    setIsFrozen(isFrozen);
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

  /**
   * Returns whether the ingredient is frozen or not.
   *
   * @return whether the ingredient is frozen or not
   */
  public Boolean getIsFrozen() {
    return isFrozen;
  }

  /**
   * Sets whether the ingredient is frozen or not.
   *
   * @param isFrozen whether the ingredient is frozen or not
   */
  public void setIsFrozen(Boolean isFrozen) {
    this.isFrozen = isFrozen;
  }

  /**
   * Returns the id which the element (not ingredient) is stored as in backend.
   *
   * @return the inventoryId of the ingredient
   */
  public int getInventoryId() {
    return inventoryId;
  }

  /**
   * Sets the id which the element (not ingredient) is stored as in backend.
   *
   * @param inventoryId the new backend id of the ingredient
   */
  public void setInventoryId(int inventoryId) {
    this.inventoryId = inventoryId;
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
        expirationDate,
        isFrozen,
        inventoryId
    );
  }
}
