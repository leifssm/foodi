package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import no.ntnu.idatt1002.view.utils.CssUtils;

import java.util.Date;

public class InventoryExpirationDate extends TextFlow implements CssUtils {
  public InventoryExpirationDate(Date date) {
    addClass("expiry-date");

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
