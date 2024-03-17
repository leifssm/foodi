package no.ntnu.idatt1002.view.components.shoppinglist;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.components.button.StandardButton;
import no.ntnu.idatt1002.view.testclasses.Recipe;
import no.ntnu.idatt1002.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

public class RecipeCard extends VBox implements ComponentUtils {
  public RecipeCard(@NotNull Recipe recipe) {
    super();
    Label title = new Label(recipe.getName());
    title.setWrapText(true);

    HBox headerBar = new HBox(
        title,
        new RecipeServings(recipe.getServings()),
        new StandardButton("X", () -> System.out.println("Remove recipe"), StandardButton.Style.ERROR)
    );
    getChildren().add(headerBar);
  }



  private static class RecipeServings extends Label implements ComponentUtils {
    public RecipeServings(int servings) {
      super();
      addClass("recipe-servings");
      setText(String.valueOf(servings));
    }
  }
}
