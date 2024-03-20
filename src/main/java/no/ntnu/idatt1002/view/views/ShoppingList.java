package no.ntnu.idatt1002.view.views;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import no.ntnu.idatt1002.view.components.TitledPage;
import no.ntnu.idatt1002.view.components.shoppinglist.AddedRecipes;
import no.ntnu.idatt1002.view.testclasses.InventoryItem;
import no.ntnu.idatt1002.view.components.shoppinglist.ShoppingListWrapper;
import no.ntnu.idatt1002.view.testclasses.Recipe;
import no.ntnu.idatt1002.view.utils.ComponentUtils;

public class ShoppingList extends TitledPage implements ComponentUtils {
  public ShoppingList() {
    super(
        "Shopping List",
        "Foodi™ intelligently only adds the ingredients you need to your shopping list"
    );
    addStylesheet("components/shopping-list/shopping-list");
    addClass("shopping-list");

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
