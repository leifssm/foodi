package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.GroupedExpiringIngredients;
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
   * @param items The items to display
   * @throws IllegalArgumentException if the list of items is empty
   */
  public InventoryListItem(
      @NotNull GroupedExpiringIngredients items
  ) throws IllegalArgumentException {
    if (items.getIngredients().isEmpty()) {
      throw new IllegalArgumentException("At least one item must be provided");
    }

    ExpiringIngredient mainItem = items.getMainExpiringIngredient();
    List<ExpiringIngredient> ingredients = items.getIngredients();

    this.subItems = new InventoryListSubItem[ingredients.size()];

    StandardCheckBoxHandler selectHandler = new StandardCheckBoxHandler();
    for (int i = 0; i < ingredients.size(); i++) {
      InventoryListSubItem subItem = new InventoryListSubItem(ingredients.get(i));
      subItems[i] = subItem;
      selectHandler.bindCheckBox(subItem.getSelect());
    }

    Label icon = new Label(mainItem.getCategory().getIcon());

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

    InventoryExpirationDate expiryDate = new InventoryExpirationDate(mainItem.getExpirationDate());

    Label category = new Label(mainItem.getCategory().getName());

    double quantityValue = 0;
    for (ExpiringIngredient ingredient : ingredients) {
      quantityValue += ingredient.getAmount();
    }
    String quantityString = "%.2f %s".formatted(
        quantityValue,
        mainItem.getUnit().getName()
    );
    Label quantity = new Label(quantityString);
    quantity.getStyleClass().add("center");

    Label unit = new Label(mainItem.getUnit().getName());
    unit.getStyleClass().add("center");

    Label edit = new Label("🖊️");
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
