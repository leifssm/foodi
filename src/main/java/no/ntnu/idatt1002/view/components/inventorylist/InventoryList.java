package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.Paginator;
import no.ntnu.idatt1002.view.utils.CssUtils;
import java.util.ArrayList;

/**
 * Class for displaying a list of ingredients
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class InventoryList extends VBox implements CssUtils {
  private final Paginator<InventoryItem> items = new Paginator<>();

  private final GridPane gridPane;
  public InventoryList() {
    addStylesheet("components/inventory/inventory-list");
    addClass("inventory-list");

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.getStyleClass().add("inventory-list-scroll");
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    // GridPane was used in favour of TableView because of the greater easy of customization
    gridPane = new GridPane();

    ColumnConstraints iconCol = new ColumnConstraints();
    iconCol.setPercentWidth(5);
    ColumnConstraints nameCol = new ColumnConstraints();
    nameCol.setPercentWidth(25);
    ColumnConstraints expiryCol = new ColumnConstraints();
    expiryCol.setPercentWidth(20);
    ColumnConstraints categoryCol = new ColumnConstraints();
    categoryCol.setPercentWidth(20);
    ColumnConstraints quantityCol = new ColumnConstraints();
    quantityCol.setPercentWidth(10);
    ColumnConstraints unitCol = new ColumnConstraints();
    unitCol.setPercentWidth(10);
    ColumnConstraints editCol = new ColumnConstraints();
    editCol.setPercentWidth(5);
    ColumnConstraints selectCol = new ColumnConstraints();
    selectCol.setPercentWidth(5);

    gridPane.getColumnConstraints().addAll(
        iconCol,
        nameCol,
        expiryCol,
        categoryCol,
        quantityCol,
        unitCol,
        editCol,
        selectCol
    );

    Label amount = new Label("AMOUNT");
    amount.getStyleClass().add("center");
    Label unit = new Label("UNIT");
    unit.getStyleClass().add("center");
    gridPane.add(new Label("o"), 0, 0);
    gridPane.add(new Label("NAME"), 1, 0);
    gridPane.add(new Label("EXPIRATION DATE"), 2, 0);
    gridPane.add(new Label("CATEGORY"), 3, 0);
    gridPane.add(amount, 4, 0);
    gridPane.add(unit, 5, 0);
    gridPane.add(new Label(), 6, 0);
    gridPane.add(new Label(), 7, 0);

    scrollPane.setContent(gridPane);
    getChildren().add(scrollPane);

    items.addItems(
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem()
    );
    render();
  }

  public Paginator<InventoryItem> getItems() {
    return items;
  }

  public void clearCells() {
    gridPane.getChildren().removeIf(node ->
        GridPane.getRowIndex(node) == null && GridPane.getRowIndex(node) > 1
    );
  }

  public void render() {
    clearCells();

    ArrayList<InventoryItem> currentPage = items.getCurrentPage();
    int rowNum = 2;
    for (InventoryItem item : currentPage) {
      InventoryListItem rows = new InventoryListItem(item, item, item);
      gridPane.addRow(rowNum++, rows.getMainItems());

      for (InventoryListSubItem subRow : rows.getSubItems()) {
        Node[] subRowItems = subRow.getItems();

        gridPane.addRow(rowNum++, subRowItems);
        GridPane.setColumnIndex(subRowItems[0], 1);
      }
    }
  }
}
