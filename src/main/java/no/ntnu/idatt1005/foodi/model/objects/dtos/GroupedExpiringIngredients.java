package no.ntnu.idatt1005.foodi.model.objects.dtos;

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
    this.ingredients = List.copyOf(ingredients);
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
   * Returns the list of expiring ingredients.
   *
   * @return an immutable list of expiring ingredients
   */
  public List<ExpiringIngredient> getIngredients() {
    return List.copyOf(ingredients);
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
