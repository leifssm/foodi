package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
  public ShoppingListCategoryItem(@NotNull AmountedIngredient item) {
    super();
    addStylesheet("components/shopping-list/shopping-list-category-item");
    addClass("shopping-list-category-item");

    Label name = new Label(item.getName());
    Label amount = new Label(item.getUnitedAmount());
    BorderPane text = new BorderPane();
    text.setLeft(name);
    text.setRight(amount);
    text.getStyleClass().add("shopping-list-category-item-text");

    setCenter(text);
  }
}
