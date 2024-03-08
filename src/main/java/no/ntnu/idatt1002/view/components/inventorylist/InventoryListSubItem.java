package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;

public class InventoryListSubItem {
  private final Node[] items;
  public InventoryListSubItem(InventoryItem item) {
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

    Button select = new Button("s");
    select.getStyleClass().add("center");

    items = new Node[]{
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

  public Node[] getItems() {
    return items;
  }

  public void setVisibility(boolean visible) {
    for (Node item : items) {
      item.setVisible(visible);
      item.setManaged(visible);
    }
  }
}