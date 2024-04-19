package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.GroupedExpiringIngredients;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Class for displaying a list of ingredients.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class InventoryList extends VBox implements ComponentUtils {

  private final GridPane gridPane;

  /**
   * Constructor for the InventoryList class.
   */
  public InventoryList() {
    addStylesheet("components/inventory/inventory-list");
    addClass("inventory-list");

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.getStyleClass().add("inventory-list-scroll");
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

    // GridPane was used in favour of TableView because of the greater easy of customization
    gridPane = new GridPane();

    ColumnConstraints iconCol = new ColumnConstraints();
    iconCol.setPercentWidth(5);
    ColumnConstraints nameCol = new ColumnConstraints();
    nameCol.setPercentWidth(30);
    ColumnConstraints expiryCol = new ColumnConstraints();
    expiryCol.setPercentWidth(20);
    ColumnConstraints categoryCol = new ColumnConstraints();
    categoryCol.setPercentWidth(20);
    ColumnConstraints quantityCol = new ColumnConstraints();
    quantityCol.setPercentWidth(10);
    ColumnConstraints unitCol = new ColumnConstraints();
    unitCol.setPercentWidth(10);
    ColumnConstraints selectCol = new ColumnConstraints();
    selectCol.setPercentWidth(5);

    gridPane.getColumnConstraints().addAll(
        iconCol,
        nameCol,
        expiryCol,
        categoryCol,
        quantityCol,
        unitCol,
        selectCol
    );

    Label amount = new Label("AMOUNT");
    amount.getStyleClass().add("center");
    Label unit = new Label("UNIT");
    unit.getStyleClass().add("center");
    gridPane.addRow(
        0,
        new Label("o"),
        new Label("NAME"),
        new Label("EXPIRATION DATE"),
        new Label("CATEGORY"),
        amount,
        unit,
        new Label()
    );

    scrollPane.setContent(gridPane);
    getChildren().add(scrollPane);
  }

  /**
   * Clears the current cells and displays all stored cells to the view.
   *
   * @param currentPage a list of grouped expiring ingredients
   */
  public void render(@NotNull final List<GroupedExpiringIngredients> currentPage) {
    clearCells();
    int rowNum = 2;
    for (GroupedExpiringIngredients ingredientGroup : currentPage) {

      InventoryListItem rows = new InventoryListItem(ingredientGroup);
      gridPane.addRow(rowNum++, rows.getMainItems());

      for (InventoryListSubItem subRow : rows.getSubItems()) {
        Node[] subRowItems = subRow.getNodes();

        gridPane.addRow(rowNum++, subRowItems);
        GridPane.setColumnIndex(subRowItems[0], 1);
      }
    }
  }

  /**
   * Removes all the displayed cells from view.
   */
  public void clearCells() {
    gridPane.getChildren().removeIf(node ->
        GridPane.getRowIndex(node) == null || GridPane.getRowIndex(node) > 1
    );
  }
}
