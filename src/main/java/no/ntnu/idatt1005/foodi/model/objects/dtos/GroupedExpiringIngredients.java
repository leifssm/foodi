package no.ntnu.idatt1005.foodi.model.objects.dtos;

import java.util.List;

public class GroupedExpiringIngredients {

  private final String groupedBy;
  private final List<ExpiringIngredient> ingredients;

  public GroupedExpiringIngredients(String groupedBy, List<ExpiringIngredient> ingredients) {
    this.groupedBy = groupedBy;
    this.ingredients = List.copyOf(ingredients);
  }

  public String getGroupedBy() {
    return groupedBy;
  }

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
