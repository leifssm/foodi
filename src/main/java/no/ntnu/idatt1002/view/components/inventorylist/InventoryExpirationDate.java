package no.ntnu.idatt1002.view.components.inventorylist;

import java.util.Date;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.ntnu.idatt1002.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A class for displaying the expiration date of an inventory item.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
class InventoryExpirationDate extends TextFlow implements ComponentUtils {
  /**
   * Constructor for the InventoryExpirationDate class.
   *
   * @param date The expiration date of the inventory item
   */
  public InventoryExpirationDate(@NotNull Date date) {
    addClass("expiry-date");

    int daysUntilExpired = (int) (
        (date.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)
    );

    if (daysUntilExpired < 0) {
      Text expiryText = new Text(
          String.format("Expired %d day%s ago",
              -daysUntilExpired,
              daysUntilExpired == -1 ? "" : "s"
          )
      );
      expiryText.getStyleClass().add("expired");

      getChildren().add(expiryText);
      return;
    }

    if (daysUntilExpired == 0) {
      Text expiryText = new Text("today");
      expiryText.getStyleClass().add("expired");

      getChildren().addAll(
          new Text("Expires "),
          expiryText
      );
      return;
    }

    if (daysUntilExpired <= 2) {
      Text expiryText = new Text(daysUntilExpired + "");
      expiryText.getStyleClass().add("expired");

      getChildren().addAll(
          new Text("Expires in "),
          expiryText,
          new Text(" day" + (daysUntilExpired == 1 ? "" : "s"))
      );
      return;
    }

    getChildren().add(
        new Text("Expires in " + daysUntilExpired + " days")
    );
  }
}
