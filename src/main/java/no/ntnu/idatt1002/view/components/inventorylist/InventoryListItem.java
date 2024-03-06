package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.Node;
import javafx.scene.control.Label;
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

  private @NotNull Node @NotNull [] createRow() {
    Label icon = new Label(mainItem.getType());
    Label name = new Label(mainItem.getName());
    TextFlow expiryDate = createExpiryDate(mainItem.getExpiryDate());
    Label category = new Label(mainItem.getCategory());
    Label quantity = new Label(mainItem.getQuantity());
    Label unit = new Label(mainItem.getUnit());
    Label edit = new Label("e");
    Label select = new Label("s");

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
    Label icon = new Label(); // Empty

    TextFlow expiryDate = createExpiryDate(item.getExpiryDate());
    expiryDate.getStyleClass().addAll("sub-item", "sub-item-expiration-date");

    InventoryListProgressBar progressBar = new InventoryListProgressBar(item.getExpiryDate());
    Label category = new Label(); // Empty

    InventoryListInput quantity = new InventoryListInput();

    return new Node[]{
        icon,
        expiryDate,
        progressBar,
        category,
        quantity
    };
  }

  public @NotNull Node [] @NotNull [] createCells() {
    int rowLength = 8;
    Node[][] rows = new Node[subItems.length + 1][rowLength];
    rows[0] = createRow();

    for (int i = 0; i < subItems.length; i++) {
      Node[] row = createSubRow(subItems[i]);
      rows[i + 1] = row;
    }

    return rows;
  }
}
