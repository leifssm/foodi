package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.idatt1002.view.components.button.DropdownButton;
import no.ntnu.idatt1002.view.components.button.StandardCheckBox;
import no.ntnu.idatt1002.view.components.button.StandardCheckBoxHandler;

class InventoryListItem {
  private final Node[] mainItems;
  private final InventoryListSubItem[] subItems;

  private final StandardCheckBoxHandler selectHandler = new StandardCheckBoxHandler();

  public InventoryListItem(InventoryItem mainItem, InventoryItem... items) {
    this.subItems = new InventoryListSubItem[items.length];

    for (int i = 0; i < items.length; i++) {
      InventoryListSubItem subItem = new InventoryListSubItem(items[i]);
      subItems[i] = subItem;
      selectHandler.bindCheckBox(subItem.getSelect());
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

    StandardCheckBox select = new StandardCheckBox();
    select.setScale(0.6);
    select.getStyleClass().add("center");

    selectHandler.bindMainCheckBox(select);

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
