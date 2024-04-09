package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.idatt1005.foodi.view.components.button.DropdownButton;
import no.ntnu.idatt1005.foodi.view.components.button.StandardCheckBox;
import no.ntnu.idatt1005.foodi.view.components.button.StandardCheckBoxHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Class for creating a standard inventory list item.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
class InventoryListItem {

  private final Node[] mainItems;
  private final InventoryListSubItem[] subItems;

  /**
   * Constructor for the InventoryListItem class.
   *
   * @param mainItem The main item to display
   * @param items    The sub items to display, if any
   */
  public InventoryListItem(@NotNull InventoryItem mainItem,
      @NotNull InventoryItem @NotNull ... items) {
    this.subItems = new InventoryListSubItem[items.length];

    StandardCheckBoxHandler selectHandler = new StandardCheckBoxHandler();
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

  /**
   * Returns the main items.
   */
  public Node[] getMainItems() {
    return mainItems;
  }

  /**
   * Returns the sub items.
   */
  public InventoryListSubItem[] getSubItems() {
    return subItems;
  }
}
