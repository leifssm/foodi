package no.ntnu.idatt1002.view.components.shoppinglist;

import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.components.button.DoubleButton;
import no.ntnu.idatt1002.view.components.button.StandardButton;
import no.ntnu.idatt1002.view.testclasses.InventoryItem;
import no.ntnu.idatt1002.view.testclasses.InventoryItem.*;
import no.ntnu.idatt1002.view.utils.ComponentUtils;

/**
 * Class representing several groups of items in the shopping list, with buttons to either remove or
 * add the items to the inventory.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class ShoppingListWrapper extends VBox implements ComponentUtils {
  /**
   * Constructor for the ShoppingListWrapper class.
   *
   * @param items The items to display
   */
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
          new StandardButton(
              "Add Items to Inventory",
              () -> System.out.println("Add items to inventory"),
              StandardButton.Style.SUCCESS
          ),
          new StandardButton("Clear", () -> System.out.println("Clear"), StandardButton.Style.ERROR)
        )
    );
  }
}
