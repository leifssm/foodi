package no.ntnu.idatt1002.view.components.cookbook;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.utils.CssUtils;

/**
 * A row of recipe cards.
 *
 * <p>This class is a custom component for displaying a row of recipe cards.
 * It is used in the cookbook grid view.</p>
 */
public class RecipeCardRow extends VBox implements CssUtils {

  /**
   * The constructor of the recipe card row component.
   *
   * @param title The title of the row
   * @param recipes The recipes to display in the row
   */
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
