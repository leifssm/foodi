package no.ntnu.idatt1005.foodi.view.views;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.view.components.TitledPage;
import no.ntnu.idatt1005.foodi.view.components.shoppinglist.AddedRecipes;
import no.ntnu.idatt1005.foodi.view.components.shoppinglist.ShoppingListWrapper;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;

/**
 * Class for displaying the shopping list page.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class ShoppingList extends TitledPage implements ComponentUtils {
  /**
   * Constructor for the ShoppingList class.
   */
  public ShoppingList() {
    super(
        "Shopping List",
        "Foodi™ intelligently only adds the ingredients you need to your shopping list"
    );
    addStylesheet("components/shopping-list/shopping-list");
    addClass("shopping-list");

    // Temp test data
    ShoppingListWrapper shoppingList = new ShoppingListWrapper(
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem(),
        new InventoryItem()
    );

    AddedRecipes addedRecipes = new AddedRecipes(
        // Temp test data
        new Recipe[]{
            new Recipe("Pizza", 4, new InventoryItem[] {
                new InventoryItem(),
                new InventoryItem(),
                new InventoryItem()
            }),
            new Recipe("Pasta", 8, new InventoryItem[] {
                new InventoryItem(),
                new InventoryItem(),
                new InventoryItem()
            }),
        }
    );

    BorderPane wrapper = new BorderPane();
    wrapper.getStyleClass().add("shopping-list-content-wrapper");
    wrapper.setLeft(shoppingList);
    wrapper.setRight(addedRecipes);

    setContent(wrapper, true);
  }
}
