package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
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
  public ShoppingListCategoryItem(@NotNull AmountedIngredient item, int[] colors) {
    super();
    addStylesheet("components/shopping-list/shopping-list-category-item");
    addClass("shopping-list-category-item");

    Label name = new Label(item.getName());
    Label amount = new Label(item.getUnitedAmount());
    BorderPane text = new BorderPane();
    text.setLeft(name);
    text.setRight(amount);
    text.getStyleClass().add("shopping-list-category-item-text");

    TilePane colorsNodes = new TilePane();
    colorsNodes.getStyleClass().add("shopping-list-category-item-colors");
    colorsNodes.prefWidthProperty().bind(heightProperty());
    colorsNodes.setHgap(1);
    colorsNodes.setVgap(1);

    for (int i : colors) {
      colorsNodes.getChildren().add(new ShoppingListCategoryItemColor(i));
    }

    setCenter(text);
    setRight(colorsNodes);
  }

  /**
   * A class to display a circle with a given color. Represents which recipes the item is used in.
   */
  private static class ShoppingListCategoryItemColor extends Pane {

    private static final String[] colors = {
        "#4182e4",
        "#e07553",
        "#9747ff",
        "#ec4c9a"
    };

    /**
     * Constructor for the ShoppingListCategoryItemColor class.
     *
     * @param colorIndex an index to use for the color
     */
    public ShoppingListCategoryItemColor(int colorIndex) {
      super();
      getStyleClass().add("shopping-list-category-item-color");
      setStyle("-fx-background-color: " + getColor(colorIndex));
    }

    /**
     * Returns a color based on color, with no overflow.
     *
     * @param i the color index to use
     * @return a color
     */
    private static @NotNull String getColor(int i) {
      return colors[i % colors.length];
    }
  }
}
