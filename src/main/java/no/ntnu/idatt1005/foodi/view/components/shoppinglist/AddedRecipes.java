package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithPartiallyRemovedIngredients;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A component for displaying all the different added recipes vertically.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class AddedRecipes extends VBox implements ComponentUtils {

  /**
   * Constructor for the AddedRecipes class.
   *
   * @param recipes An array of recipes to display
   */
  public AddedRecipes(
      @NotNull List<@NotNull RecipeWithPartiallyRemovedIngredients> recipes,
      List<Runnable> onRemoveMethods
  ) {
    super();
    addStylesheet("components/shopping-list/added-recipes");
    addClass("added-recipes");
    Label title = new Label("Added Recipes");
    getChildren().add(title);

    for (int i = 0; i < recipes.size(); i++) {
      RecipeWithPartiallyRemovedIngredients recipe = recipes.get(i);
      Runnable onRemove = onRemoveMethods.get(i);
      RecipeCard recipeCard = new RecipeCard(recipe, onRemove);
      getChildren().add(recipeCard);
    }
  }
}
