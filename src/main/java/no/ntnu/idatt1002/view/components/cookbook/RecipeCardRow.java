package no.ntnu.idatt1002.view.components.cookbook;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class RecipeCardRow extends VBox implements CssUtils {

  public RecipeCardRow(String title, RecipeCard[] recipes) {
    super();
    addStylesheet("components/cookbook-grid/recipe-card-row");
    addClass("recipe-card-row");

    Label recipeRowTitle = new Label(title);
    recipeRowTitle.getStyleClass().add("recipe-card-row-title");
    getChildren().add(recipeRowTitle);

    HBox recipeRowContent = new HBox();
    recipeRowContent.getStyleClass().add("recipe-card-row-content");
    for (RecipeCard recipe : recipes) {
      recipeRowContent.getChildren().add(recipe);
    }
    getChildren().add(recipeRowContent);
  }
}
