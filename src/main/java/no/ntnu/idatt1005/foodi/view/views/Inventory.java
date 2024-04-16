package no.ntnu.idatt1005.foodi.view.views;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

  private BorderPane topBar;
  private Runnable controllerUpdate;

  /**
   * Constructor for the Inventory class.
   */
  public Inventory() {
    super("Inventory", "Manage your inventory");
    addStylesheet("components/inventory/inventory");
    addClass("inventory");

    setTopBar();

    render();
  }

  private void setTopBar() {
    topBar = new BorderPane();
    topBar.getStyleClass().add("inventory-top-bar");
    topBar.setLeft(
        new StandardButton("Add item", () -> new AddItemDialog(null)).setType(
            StandardButton.Style.SUCCESS));

    HBox actions = new HBox(
        new StandardButton("Freeze").setType(StandardButton.Style.PRIMARY),
        new StandardButton("Delete").setType(StandardButton.Style.ERROR)
    );
    actions.getStyleClass().add("inventory-actions");
    topBar.setRight(actions);
  }


  /**
   * Method for rendering the inventory page.
   */
  public void render() {
    InventoryList inventoryList = new InventoryList();
    VBox content = new VBox(topBar, inventoryList);

    setContent(content);
  }
}