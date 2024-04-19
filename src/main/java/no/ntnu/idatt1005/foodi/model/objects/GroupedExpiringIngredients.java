package no.ntnu.idatt1005.foodi.model.objects;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * This class represents a group of expiring ingredients.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class GroupedExpiringIngredients {

  private final String groupedBy;
  private final List<ExpiringIngredient> ingredients;

  /**
   * Constructor for a group of expiring ingredients.
   *
   * @param groupedBy   the group by which the ingredients are grouped, only for display purposes
   * @param ingredients a list of expiring ingredients
   */
  public GroupedExpiringIngredients(String groupedBy, List<ExpiringIngredient> ingredients) {
    this.groupedBy = groupedBy;
    this.ingredients = sortIngredientsByExpiryDate(ingredients);
  }

  private List<ExpiringIngredient> sortIngredientsByExpiryDate(
      List<ExpiringIngredient> ingredients) {
    return ingredients.stream()
        .sorted(Comparator.comparing(ExpiringIngredient::getExpirationDate))
        .toList();
  }

  /**
   * Returns the group by which the ingredients are grouped.
   *
   * @return the group by which the ingredients are grouped
   */
  public String getGroupedBy() {
    return groupedBy;
  }

  /**
   * Returns the main expiring ingredient of the group, which is just the sum of the sub-items with
   * the lowest expiry date.
   *
   * @return the main expiring ingredient of the group
   */
  public ExpiringIngredient getMainExpiringIngredient() {
    double totalAmount = 0;
    LocalDate lowestExpiryDate = null;

    for (ExpiringIngredient ingredient : getIngredients()) {
      totalAmount += ingredient.getAmount();
      lowestExpiryDate = minDate(ingredient.getExpirationDate(), lowestExpiryDate);
    }

    final String ingredientName = getIngredients().get(0).getName();
    final Ingredient.Unit unit = getIngredients().get(0).getUnit();
    final Ingredient.Category category = getIngredients().get(0).getCategory();
    final boolean isFrozen = getIngredients().get(0).getIsFrozen();

    return new ExpiringIngredient(
        -1,
        ingredientName,
        unit,
        category,
        totalAmount,
        lowestExpiryDate,
        isFrozen
    );
  }

  /**
   * Returns the list of expiring ingredients.
   *
   * @return an immutable list of expiring ingredients
   */
  public List<ExpiringIngredient> getIngredients() {
    return List.copyOf(ingredients);
  }

  private LocalDate minDate(LocalDate date1, LocalDate date2) {
    if (date1 == null) {
      return date2;
    }
    if (date2 == null) {
      return date1;
    }
    return date1.isBefore(date2) ? date1 : date2;
  }

  @Override
  public String toString() {
    return String.format(
        "GroupedExpiringIngredients{groupedBy='%s', ingredients=%s}",
        groupedBy,
        ingredients
    );
  }
}
