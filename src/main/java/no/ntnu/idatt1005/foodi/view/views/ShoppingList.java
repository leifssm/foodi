package no.ntnu.idatt1005.foodi.view.views;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.view.components.TitledPage;
import no.ntnu.idatt1005.foodi.view.components.shoppinglist.ShoppingListWrapper;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;

/**
 * Class for displaying the shopping list page.
 *
 * @author Leif Mørstad
 * @version 1.0
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
    ShoppingListWrapper shoppingList = new ShoppingListWrapper();

    // TODO: add controller support
//    AddedRecipes addedRecipes = new AddedRecipes();

    BorderPane wrapper = new BorderPane();
    wrapper.getStyleClass().add("shopping-list-content-wrapper");
    wrapper.setLeft(shoppingList);
//    wrapper.setRight(addedRecipes);

    setContent(wrapper, true);
  }
}
