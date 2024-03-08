package no.ntnu.idatt1002.view.components.inventorylist;

import javafx.scene.control.ProgressBar;
import no.ntnu.idatt1002.view.utils.CssUtils;

import java.util.Date;

public class InventoryListProgressBar extends ProgressBar implements CssUtils {
  private boolean isFrozen = false;

  public InventoryListProgressBar() {
    super();
    addStylesheet("components/inventory/inventory-list-progress-bar");
    addClass("inventory-list-progress-bar");

    updateClasses();
  }

  public InventoryListProgressBar(Date expiry) {
    this();
    setExpiry(expiry);
  }

  public void setExpiry(Date expiry) {
    double maxProgressInDays = 14;
    double daysUntilExpired = 1 + (
        (double) (expiry.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)
    );

    double progress = Math.max(0, Math.min(daysUntilExpired / maxProgressInDays, 1));

    setProgress(progress);
    updateClasses();
  }

  public void setIsFrozen(boolean isFrozen) {
    this.isFrozen = isFrozen;
  }

  private void updateClasses() {
    getStyleClass().removeAll("red", "yellow", "blue", "green");

    double progress = getProgress();

    if (isFrozen) {
      getStyleClass().add("blue");
    } else if (progress < 0.25) {
      getStyleClass().add("red");
    } else if (progress < 0.5) {
      getStyleClass().add("yellow");
    } else {
      getStyleClass().add("green");
    }
  }
}
