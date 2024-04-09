package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A class to display a grouped category of items in the shopping list, e.g. "Meat" or "Vegetables".
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class ShoppingListCategoryGroup extends VBox implements ComponentUtils {
  /**
   * Constructor for the ShoppingListCategoryGroup class.
   *
   * @param items The items to display
   */
  public ShoppingListCategoryGroup(InventoryItem.@NotNull InventoryItemGroup items) {
    super();
    addStylesheet("components/shopping-list/shopping-list-category-group");
    addClass("shopping-list-category-group");

    VBox content = new VBox();
    content.getStyleClass().add("shopping-list-category-group-content");

    for (InventoryItem item : items.getItems()) {
      ShoppingListCategoryItem listItem = new ShoppingListCategoryItem(item);
      content.getChildren().add(listItem);
    }

    getChildren().addAll(
        new Label(items.getName()),
        content
    );
  }
}
