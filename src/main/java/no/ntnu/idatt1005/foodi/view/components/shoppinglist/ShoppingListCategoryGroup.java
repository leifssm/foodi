package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.view.components.shoppinglist.IngredientGrouper.IngredientCategoryGroup;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;

/**
 * A class to display a grouped category of items in the shopping list, e.g. "Meat" or
 * "Vegetables".
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ShoppingListCategoryGroup extends VBox implements ComponentUtils {

  /**
   * Constructor for the ShoppingListCategoryGroup class.
   *
   * @param group The items to display
   */
  public ShoppingListCategoryGroup(IngredientCategoryGroup group) {
    super();
    addStylesheet("components/shopping-list/shopping-list-category-group");
    addClass("shopping-list-category-group");

    VBox content = new VBox();
    content.getStyleClass().add("shopping-list-category-group-content");

    for (AmountedIngredient item : group.ingredients()) {
      ShoppingListCategoryItem listItem = new ShoppingListCategoryItem(item);
      content.getChildren().add(listItem);
    }

    getChildren().addAll(
        new Label(group.name().getIcon() + " " + group.name().getName()),
        content
    );
  }
}
