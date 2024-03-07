package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

class InventoryListItem {
  private final InventoryItem mainItem;
  private final InventoryItem[] subItems;

  public InventoryListItem(InventoryItem mainItem, InventoryItem... items) {
    this.mainItem = mainItem;
    this.subItems = items;
  }

  private @NotNull TextFlow createExpiryDate(@NotNull Date date) {
    TextFlow text = new TextFlow();
    text.getStyleClass().add("expiry-date");

    int daysUntilExpired = (int) (
        (date.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)
    );

    if (daysUntilExpired < 0) {
      Text expiryText = new Text(
          "Expired " +
              -daysUntilExpired
              + " day"
              + (daysUntilExpired == -1 ? "" : "s")
              + " ago"
      );
      expiryText.getStyleClass().add("expired");

      text.getChildren().add(expiryText);
      return text;
    }

    if (daysUntilExpired == 0) {
      Text expiryText = new Text("today");
      expiryText.getStyleClass().add("expired");

      text.getChildren().addAll(
          new Text("Expires "),
          expiryText
      );
      return text;
    }

    if (daysUntilExpired <= 2) {
      Text expiryText = new Text(daysUntilExpired + "");
      expiryText.getStyleClass().add("expired");

      text.getChildren().addAll(
          new Text("Expires in "),
          expiryText,
          new Text(" day" + (daysUntilExpired == 1 ? "" : "s"))
      );
      return text;
    }

    text.getChildren().add(
        new Text("Expires in " + daysUntilExpired + " days")
    );
    return text;
  }

  public @NotNull Node @NotNull [] createMainRow() {
    Label icon = new Label(mainItem.getType());
    Label name = new Label(mainItem.getName());
    name.getStyleClass().add("vertical-padding");
    TextFlow expiryDate = createExpiryDate(mainItem.getExpiryDate());
    Label category = new Label(mainItem.getCategory());
    Label quantity = new Label(mainItem.getQuantity());
    quantity.getStyleClass().add("center");
    Label unit = new Label(mainItem.getUnit());
    unit.getStyleClass().add("center");
    Label edit = new Label("e");
    edit.getStyleClass().add("center");
    Label select = new Label("s");
    select.getStyleClass().add("center");

    return new Node[]{
        icon,
        name,
        expiryDate,
        category,
        quantity,
        unit,
        edit,
        select
    };
  }

  private @NotNull Node @NotNull [] createSubRow(@NotNull InventoryItem item) {
    Label background = new Label(); // Empty
    GridPane.setColumnSpan(background, 7);
    background.getStyleClass().add("sub-item-background");

    TextFlow expiryDate = createExpiryDate(item.getExpiryDate());
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

    return new Node[]{
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

  public @NotNull Node [] @NotNull [] createSubRows() {
    int rowLength = 8;
    Node[][] rows = new Node[subItems.length][rowLength];

    for (int i = 0; i < subItems.length; i++) {
      rows[i] = createSubRow(subItems[i]);
    }

    return rows;
  }
}
