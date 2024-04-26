package no.ntnu.idatt1005.foodi.view.views;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.GroupedExpiringIngredients;
import no.ntnu.idatt1005.foodi.view.components.TitledPage;
import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.components.button.StandardCheckBoxHandler;
import no.ntnu.idatt1005.foodi.view.components.inventorylist.AddItemDialog;
import no.ntnu.idatt1005.foodi.view.components.inventorylist.InventoryList;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;

/**
 * Class for displaying the inventory page.
 *
 * @author Leif Mørstad
 * @version 1.2
 */
public class Inventory extends TitledPage implements ComponentUtils {

  private final InventoryList inventoryList;
  private StandardButton addItemButton;
  private StandardButton freezeButton;
  private StandardButton deleteButton;

  /**
   * Constructor for the Inventory class.
   */
  public Inventory() {
    super("Inventory", "Manage your inventory");
    addStylesheet("components/inventory/inventory");
    addClass("inventory");

    inventoryList = new InventoryList();
    VBox content = new VBox(createTopBar(), inventoryList);

    setContent(content, true);
  }

  private BorderPane createTopBar() {
    BorderPane topBar = new BorderPane();
    topBar.getStyleClass().add("inventory-top-bar");

    addItemButton = new StandardButton("Add item").setType(StandardButton.Style.SUCCESS);
    freezeButton = new StandardButton("Toggle Freeze").setType(StandardButton.Style.PRIMARY);
    deleteButton = new StandardButton("Delete").setType(StandardButton.Style.ERROR);
    topBar.setLeft(addItemButton);

    HBox actions = new HBox(freezeButton, deleteButton);
    actions.getStyleClass().add("inventory-actions");
    topBar.setRight(actions);

    return topBar;
  }

  /**
   * Method for setting the onAddItem action in the AddItemDialog.
   *
   * @param onAddItem The action to be set
   */
  public void setOnAddItem(Consumer<ExpiringIngredient> onAddItem) {
    addItemButton.setOnAction(event -> new AddItemDialog(onAddItem));
  }

  /**
   * Method for setting the runnable triggered when the delete button is clicked.
   *
   * @param onDeleteItems The action to be set
   */
  public void setOnDeleteItems(Runnable onDeleteItems) {
    deleteButton.setOnAction(e -> onDeleteItems.run());
  }

  public void setOnAmountChange(Consumer<ExpiringIngredient> onAmountChange) {
    inventoryList.setOnAmountChange(onAmountChange);
  }

  /**
   * Method for setting the runnable triggered when the freeze button is clicked.
   *
   * @param onFreeze The action to be set
   */
  public void setOnFreezeItems(Runnable onFreeze) {
    freezeButton.setOnAction(e -> onFreeze.run());
  }

  /**
   * Method for rendering the inventory page.
   */
  public void render(List<GroupedExpiringIngredients> groupedExpiringIngredients) {
    inventoryList.render(groupedExpiringIngredients);
  }

  /**
   * Method for getting the selected items in the inventory.
   *
   * @return A list of selected items
   */
  public List<ExpiringIngredient> getSelectedItems() {
    ArrayList<ExpiringIngredient> combinedItems = new ArrayList<>();

    for (StandardCheckBoxHandler<ExpiringIngredient> checkbox : inventoryList.getCheckboxHandlers()) {
      combinedItems.addAll(checkbox.getSelectedData());
    }

    return combinedItems;
  }
}