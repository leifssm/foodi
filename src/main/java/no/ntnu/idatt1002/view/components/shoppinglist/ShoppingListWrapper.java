package no.ntnu.idatt1002.view.components.shoppinglist;

import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.components.button.DoubleButton;
import no.ntnu.idatt1002.view.components.button.StandardButton;
import no.ntnu.idatt1002.view.testclasses.InventoryItem;
import no.ntnu.idatt1002.view.testclasses.InventoryItem.*;
import no.ntnu.idatt1002.view.utils.ComponentUtils;

/**
 * Wrapper for the shopping list
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class ShoppingListWrapper extends VBox implements ComponentUtils {
  public ShoppingListWrapper(InventoryItem... items) {
    super();
    addStylesheet("components/shopping-list/shopping-list-wrapper");
    addClass("shopping-list-wrapper");
    GroupedInventoryItems groupItems = InventoryItem.groupItemsBy(
        items,
        "Category",
        InventoryItem::getCategory
    );

    for (InventoryItemGroup group : groupItems.getGroups()) {
      ShoppingListCategoryGroup shoppingListGroup = new ShoppingListCategoryGroup(group);
      getChildren().add(shoppingListGroup);
    }

    getChildren().add(
        new DoubleButton(
          new StandardButton("Add Items to Inventory", () -> System.out.println("Add items to inventory"), StandardButton.Style.SUCCESS),
          new StandardButton("Clear", () -> System.out.println("Clear"), StandardButton.Style.ERROR)
        )
    );
  }
}
