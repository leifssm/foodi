package no.ntnu.idatt1002.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import no.ntnu.idatt1002.view.testclasses.InventoryItem;
import no.ntnu.idatt1002.view.utils.ComponentUtils;

class ShoppingListCategoryItem extends BorderPane implements ComponentUtils {
  public ShoppingListCategoryItem(InventoryItem item) {
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

  private static class ShoppingListCategoryItemColor extends Pane {
    public ShoppingListCategoryItemColor(String color) {
      super();
      getStyleClass().add("shopping-list-category-item-color");
      setStyle("-fx-background-color: " + color);
    }
  }
}
