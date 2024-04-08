package no.ntnu.idatt1005.foodi.view.components.inventorylist;

import java.util.Date;
import javafx.scene.control.ProgressBar;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A class for displaying the progress of an inventory item.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
class InventoryListProgressBar extends ProgressBar implements CssUtils {
  private boolean isFrozen = false;

  /**
   * Constructor for the InventoryListProgressBar class.
   */
  public InventoryListProgressBar() {
    super();
    addStylesheet("components/inventory/inventory-list-progress-bar");
    addClass("inventory-list-progress-bar");

    updateClasses();
  }

  /**
   * Constructor for the InventoryListProgressBar class.
   *
   * @param expiry The expiration date of the inventory item
   */
  public InventoryListProgressBar(@NotNull Date expiry) {
    this();
    setExpiry(expiry);
  }

  /**
   * Sets the expiration date of the inventory item and updates its color.
   *
   * @param expiry The expiration date of the inventory item
   */
  public void setExpiry(@NotNull Date expiry) {
    double maxProgressInDays = 14;
    double daysUntilExpired = 1 + (
        (double) (expiry.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)
    );

    double progress = Math.max(0, Math.min(daysUntilExpired / maxProgressInDays, 1));

    setProgress(progress);
    updateClasses();
  }

  /**
   * Sets whether the inventory item is frozen and updates its color.
   *
   * @param isFrozen Whether the inventory item is frozen
   */
  public void setIsFrozen(boolean isFrozen) {
    this.isFrozen = isFrozen;
    updateClasses();
  }

  /**
   * Updates the color of the progress bar based on its progress and whether it is frozen.
   */
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
