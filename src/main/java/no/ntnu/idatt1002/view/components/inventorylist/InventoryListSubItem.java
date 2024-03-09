package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import no.ntnu.idatt1002.view.components.button.StandardCheckBox;
import org.jetbrains.annotations.NotNull;

/**
 * A class for displaying the sub items in the inventory list.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
class InventoryListSubItem {
  private final Node[] nodes;
  private final StandardCheckBox select = new StandardCheckBox();

  /**
   * Constructor for the InventoryListSubItem class.
   *
   * @param item The inventory item to display
   */
  public InventoryListSubItem(@NotNull InventoryItem item) {
    Label background = new Label(); // Empty
    GridPane.setColumnSpan(background, 7);
    background.getStyleClass().add("sub-item-background");

    TextFlow expiryDate = new InventoryExpirationDate(item.getExpiryDate());
    expiryDate.getStyleClass().addAll("gray", "sub-item-expiration-date");

    InventoryListProgressBar progressBar = new InventoryListProgressBar(item.getExpiryDate());
    Label category = new Label(); // Empty

    InventoryListInput quantity = new InventoryListInput();
    quantity.setText(item.getQuantity());
    quantity.setMaxHeight(expiryDate.getMaxHeight() - 4);
    quantity.setPrefHeight(expiryDate.getMaxHeight() - 4);

    Label unit = new Label(item.getUnit());
    unit.getStyleClass().addAll("center", "gray", "vertical-padding");

    Label edit = new Label("e");
    edit.getStyleClass().add("center");

    select.setScale(0.6);
    select.getStyleClass().add("center");

    nodes = new Node[]{
        background,
        expiryDate,
        progressBar,
        category,
        quantity,
        unit,
        edit,
        select
    };
  }

  public Node[] getNodes() {
    return nodes;
  }

  /**
   * Sets the visibility of the sub item.
   *
   * @param visible Whether the sub item should be displayed
   */
  public void setVisibility(boolean visible) {
    for (Node node : nodes) {
      node.setVisible(visible);
      node.setManaged(visible);
    }
  }

  public StandardCheckBox getSelect() {
    return select;
  }
}