package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import java.util.List;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithPartiallyRemovedIngredients;
import no.ntnu.idatt1005.foodi.view.components.button.DoubleButton;
import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.components.shoppinglist.IngredientGrouper.IngredientCategoryGroup;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Class representing several groups of items in the shopping list, with buttons to either remove or
 * add the items to the inventory.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class ShoppingListWrapper extends VBox implements ComponentUtils {

  private final StandardButton addItemsButton;
  private final StandardButton clearItemsButton;

  /**
   * Constructor for the ShoppingListWrapper class.
   *
   * @param recipes The recipes to extract the data from
   */
  public ShoppingListWrapper(
      @NotNull List<@NotNull RecipeWithPartiallyRemovedIngredients> recipes
  ) {
    super();
    addStylesheet("components/shopping-list/shopping-list-wrapper");
    addClass("shopping-list-wrapper");
    List<AmountedIngredient> joinedRecipes = IngredientGrouper.joinIngredientsFromRecipes(recipes);
    List<IngredientCategoryGroup> groupItems = IngredientGrouper.groupByCategory(joinedRecipes);

    for (IngredientCategoryGroup group : groupItems) {
      ShoppingListCategoryGroup shoppingListGroup = new ShoppingListCategoryGroup(group);
      getChildren().add(shoppingListGroup);
    }

    addItemsButton = new StandardButton(
        "Add Items to Inventory",
        () -> System.out.println("Add items to inventory"),
        StandardButton.Style.SUCCESS
    );

    clearItemsButton = new StandardButton(
        "Clear",
        () -> System.out.println("Clear"),
        StandardButton.Style.ERROR
    );

    getChildren().add(
        new DoubleButton(addItemsButton, clearItemsButton)
    );
  }

  public StandardButton getAddItemsButton() {
    return addItemsButton;
  }

  public StandardButton getClearItemsButton() {
    return clearItemsButton;
  }
}
