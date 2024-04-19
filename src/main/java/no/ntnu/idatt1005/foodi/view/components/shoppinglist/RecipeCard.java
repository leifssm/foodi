package no.ntnu.idatt1005.foodi.view.components.shoppinglist;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.PartiallyRemovedAmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.Recipe;
import no.ntnu.idatt1005.foodi.model.objects.RecipeWithPartiallyRemovedIngredients;
import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * A component for displaying a summary of a recipe. Used by {@link AddedRecipes}.
 *
 * @author Leif Mørstad
 * @version 1.0
 * @see AddedRecipes
 * @see Recipe
 */
public class RecipeCard extends BorderPane implements ComponentUtils {

  /**
   * Constructor for the RecipeCard class.
   *
   * @param recipe The recipe to display
   */
  public RecipeCard(@NotNull RecipeWithPartiallyRemovedIngredients recipe) {
    super();
    addStylesheet("components/shopping-list/recipe-card");
    addClass("recipe-card");
    Label title = new Label(recipe.getName());
    title.getStyleClass().add("recipe-title");
    title.setWrapText(true);

    setCenter(title);
    setRight(
        new HBox(
            new RecipeServings(recipe.getPortions()),
            new StandardButton(
                "X",
                () -> System.out.println("Remove recipe"),
                StandardButton.Style.ERROR
            )
        )
    );

    BorderPane ingredientsWrapper = new BorderPane();
    ingredientsWrapper.getStyleClass().add("ingredients-wrapper");

    VBox ingredientsColor = new VBox();
    ingredientsColor.getStyleClass().add("ingredients-color");
    Insets ingredientsColorMargin = new Insets(0, 10, 0, 0);
    BorderPane.setMargin(ingredientsColor, ingredientsColorMargin);
    ingredientsWrapper.setLeft(ingredientsColor);

    VBox ingredients = new VBox();
    ingredients.getStyleClass().add("ingredients");

    for (PartiallyRemovedAmountedIngredient item : recipe.getIngredients()) {
      String name = item.getCategory().getIcon() + " " + item.getName();
      if (item.getRemainingAmount() > 0) {
        ingredients.getChildren().add(
            new KeptPartOfIngredientItem(
                name,
                item.getRemainingAmount() + " " + item.getUnit()
            )
        );
      }
      if (item.getRemovedAmount() > 0) {
        ingredients.getChildren().add(
            new RemovedPartOfIngredientItem(
                name,
                item.getRemovedAmount() + " " + item.getUnit()
            )
        );
      }
    }

    ingredientsWrapper.setCenter(ingredients);
    setBottom(ingredientsWrapper);
  }

  private static class RecipeServings extends Label implements ComponentUtils {

    public RecipeServings(int servings) {
      super();
      addClass("recipe-servings");
      setText(String.valueOf(servings));
      HBox.setMargin(this, new Insets(0, 5, 0, 5));
    }
  }

  private static class KeptPartOfIngredientItem extends BorderPane implements ComponentUtils {

    public KeptPartOfIngredientItem(@NotNull String name, @NotNull String amount) {
      super();
      addClasses("ingredient");

      setLeft(new Label(name));

      Label amountLabel = new Label(amount);
      amountLabel.getStyleClass().add("ingredient-amount");
      setRight(amountLabel);
    }
  }

  private static class RemovedPartOfIngredientItem extends StackPane implements ComponentUtils {

    public RemovedPartOfIngredientItem(@NotNull String name, @NotNull String amount) {
      super();
      addClasses("ingredient");

      BorderPane content = new KeptPartOfIngredientItem(name, amount);

      HBox crossedOutLine = new HBox();
      crossedOutLine.getStyleClass().add("crossed-out-line");

      getChildren().addAll(content, crossedOutLine);
      setOnMouseClicked(event -> toggleClass("marked"));
    }
  }
}
