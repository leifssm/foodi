package no.ntnu.idatt1005.foodi.view.views;

import java.util.List;
import java.util.function.Consumer;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.GroupedExpiringIngredients;
import no.ntnu.idatt1005.foodi.view.components.TitledPage;
import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.components.inventorylist.AddItemDialog;
import no.ntnu.idatt1005.foodi.view.components.inventorylist.InventoryList;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;

/**
 * Class for displaying the inventory page.
 *
 * @author Leif Mørstad
 * @version 1.2
 */
public class Inventory extends TitledPage implements CssUtils {

  private final InventoryList inventoryList;
  private StandardButton addItemButton;

  /**
   * Constructor for the Inventory class.
   */
  public Inventory() {
    super("Inventory", "Manage your inventory");
    addStylesheet("components/inventory/inventory");
    addClass("inventory");

    inventoryList = new InventoryList();
    VBox content = new VBox(createTopBar(), inventoryList);
    setContent(content);
  }

  private BorderPane createTopBar() {
    BorderPane topBar = new BorderPane();
    topBar.getStyleClass().add("inventory-top-bar");

    addItemButton = new StandardButton("Add item");
    topBar.setLeft(
        addItemButton.setType(
            StandardButton.Style.SUCCESS));

    HBox actions = new HBox(
        new StandardButton("Freeze").setType(StandardButton.Style.PRIMARY),
        new StandardButton("Delete").setType(StandardButton.Style.ERROR)
    );
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
   * Method for rendering the inventory page.
   */
  public void render(List<GroupedExpiringIngredients> groupedExpiringIngredients) {
    inventoryList.render(groupedExpiringIngredients);
  }
}