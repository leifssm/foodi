package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.idatt1002.view.components.button.DropdownButton;

class InventoryListItem {
  private final Node[] mainItems;
  private final InventoryListSubItem[] subItems;

  public InventoryListItem(InventoryItem mainItem, InventoryItem... items) {
    this.subItems = new InventoryListSubItem[items.length];
    for (int i = 0; i < items.length; i++) {
      subItems[i] = new InventoryListSubItem(items[i]);
    }

    Label icon = new Label(mainItem.getType());

    HBox nameBox = new HBox();
    Label name = new Label(mainItem.getName());
    name.getStyleClass().add("vertical-padding");

    nameBox.getChildren().addAll(
        name,
        new DropdownButton((expanded) -> {
          for (InventoryListSubItem subItem : subItems) {
            subItem.setVisibility(expanded);
          }
        })
    );

    InventoryExpirationDate expiryDate = new InventoryExpirationDate(mainItem.getExpiryDate());

    Label category = new Label(mainItem.getCategory());

    Label quantity = new Label(mainItem.getQuantity());
    quantity.getStyleClass().add("center");

    Label unit = new Label(mainItem.getUnit());
    unit.getStyleClass().add("center");

    Label edit = new Label("e");
    edit.getStyleClass().add("center");

    Label select = new Label("s");
    select.getStyleClass().add("center");

    mainItems = new Node[]{
        icon,
        nameBox,
        expiryDate,
        category,
        quantity,
        unit,
        edit,
        select
    };
  }

  public Node[] getMainItems() {
    return mainItems;
  }

  public InventoryListSubItem[] getSubItems() {
    return subItems;
  }
}
