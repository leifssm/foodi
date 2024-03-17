package no.ntnu.idatt1002.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.testclasses.InventoryItem;
import no.ntnu.idatt1002.view.utils.ComponentUtils;

public class ShoppingListCategoryGroup extends VBox implements ComponentUtils {
  public ShoppingListCategoryGroup(InventoryItem.InventoryItemGroup items) {
    super();
    addStylesheet("components/shopping-list/shopping-list-category-group");
    addClass("shopping-list-category-group");

    VBox content = new VBox();
    content.getStyleClass().add("shopping-list-category-group-content");

    getChildren().addAll(
        new Label(items.getName()),
        content
    );

    for (InventoryItem item : items.getItems()) {
      ShoppingListCategoryItem listItem = new ShoppingListCategoryItem(item);
      content.getChildren().add(listItem);
    }
  }
}
