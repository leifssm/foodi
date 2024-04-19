package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A class for displaying the expiration date of an inventory item.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
class InventoryExpirationDate extends TextFlow implements ComponentUtils {

  /**
   * Constructor for the InventoryExpirationDate class.
   *
   * @param date The expiration date of the inventory item
   */
  public InventoryExpirationDate(@NotNull LocalDate date) {
    addClass("expiry-date");

    long daysUntilExpired = DAYS.between(LocalDate.now(), date);

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
