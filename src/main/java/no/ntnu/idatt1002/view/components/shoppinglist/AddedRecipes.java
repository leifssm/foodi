package no.ntnu.idatt1002.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.testclasses.Recipe;
import no.ntnu.idatt1002.view.utils.ComponentUtils;

public class AddedRecipes extends VBox implements ComponentUtils {
  public AddedRecipes(Recipe[] recipes) {
    super();
    addStylesheet("components/shopping-list/added-recipes");
    addClass("added-recipes");
    Label title = new Label("Added Recipes");
    getChildren().add(title);

    for (Recipe recipe : recipes) {
      RecipeCard recipeCard = new RecipeCard(recipe);
      getChildren().add(recipeCard);
    }
  }
}
