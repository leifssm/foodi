package no.ntnu.idatt1002.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import no.ntnu.idatt1002.view.testclasses.InventoryItem;
import no.ntnu.idatt1002.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A class to display a single item in a category in the shopping list. To be used in the
 * {@link ShoppingListCategoryGroup}.
 */
class ShoppingListCategoryItem extends BorderPane implements ComponentUtils {
  /**
   * Constructor for the ShoppingListCategoryItem class.
   *
   * @param item The item to display
   */
  public ShoppingListCategoryItem(@NotNull InventoryItem item) {
    super();
    addStylesheet("components/shopping-list/shopping-list-category-item");
    addClass("shopping-list-category-item");

    Label name = new Label(item.getName());
    Label amount = new Label(item.getQuantity() + " " + item.getUnit());
    BorderPane text = new BorderPane();
    text.setLeft(name);
    text.setRight(amount);
    text.getStyleClass().add("shopping-list-category-item-text");

    TilePane colorsNodes = new TilePane();
    colorsNodes.getStyleClass().add("shopping-list-category-item-colors");
    colorsNodes.prefWidthProperty().bind(heightProperty());
    colorsNodes.setHgap(1);
    colorsNodes.setVgap(1);

    colorsNodes.getChildren().addAll(
        new ShoppingListCategoryItemColor("#440000"),
        //new ShoppingListCategoryItemColor("#00FF00"),
        //new ShoppingListCategoryItemColor("#00FF00"),
        //new ShoppingListCategoryItemColor("#00FF00"),
        new ShoppingListCategoryItemColor("#0000FF")
    );

    setCenter(text);
    setRight(colorsNodes);
  }

  /**
   * A class to display a circle with a given color. Represents which recipes the item is used in.
   */
  private static class ShoppingListCategoryItemColor extends Pane {
    /**
     * Constructor for the ShoppingListCategoryItemColor class.
     *
     * @param color a hex color code to display, e.g. "#FF0000"
     */
    public ShoppingListCategoryItemColor(String color) {
      super();
      getStyleClass().add("shopping-list-category-item-color");
      setStyle("-fx-background-color: " + color);
    }
  }
}
