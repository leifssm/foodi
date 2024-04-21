package no.ntnu.idatt1005.foodi.view.views;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithPartiallyRemovedIngredients;
import no.ntnu.idatt1005.foodi.view.components.TitledPage;
import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.components.shoppinglist.AddedRecipes;
import no.ntnu.idatt1005.foodi.view.components.shoppinglist.ShoppingListWrapper;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Class for displaying the shopping list page.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ShoppingList extends TitledPage implements ComponentUtils {

  private BorderPane wrapper = new BorderPane();
  private StandardButton addItemsButton;
  private StandardButton clearItemsButton;
  private Runnable onAddItems = () -> {
  };
  private Runnable onClearItems = () -> {
  };

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

    wrapper.getStyleClass().add("shopping-list-content-wrapper");
    setContent(wrapper, true);

    render(new ArrayList<>());
  }

  public void render(@NotNull List<@NotNull RecipeWithPartiallyRemovedIngredients> recipes) {
    ShoppingListWrapper shoppingList = new ShoppingListWrapper(recipes);
    addItemsButton = shoppingList.getAddItemsButton();
    clearItemsButton = shoppingList.getClearItemsButton();
    bindButtonActions();

    wrapper.setLeft(shoppingList);
    wrapper.setRight(new AddedRecipes(recipes));

  }

  private void bindButtonActions() {
    addItemsButton.setOnAction(e -> onAddItems.run());
    clearItemsButton.setOnAction(e -> onClearItems.run());
  }

  public void setOnAddItems(Runnable onAddItems) {
    this.onAddItems = onAddItems;
    bindButtonActions();
  }

  public void setOnClearItems(Runnable onClearItems) {
    this.onClearItems = onClearItems;
    bindButtonActions();
  }
}
