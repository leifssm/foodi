package no.ntnu.idatt1002.view.components.shoppinglist;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import no.ntnu.idatt1002.view.components.button.StandardButton;
import no.ntnu.idatt1002.view.testclasses.InventoryItem;
import no.ntnu.idatt1002.view.testclasses.Recipe;
import no.ntnu.idatt1002.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

public class RecipeCard extends BorderPane implements ComponentUtils {
  public RecipeCard(@NotNull Recipe recipe) {
    super();
    addStylesheet("components/shopping-list/recipe-card");
    addClass("recipe-card");
    Label title = new Label(recipe.getName());
    title.getStyleClass().add("recipe-title");
    title.setWrapText(true);

    setCenter(title);
    setRight(
        new HBox(
          new RecipeServings(recipe.getServings()),
          new StandardButton("X", () -> System.out.println("Remove recipe"), StandardButton.Style.ERROR)
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
    for (InventoryItem item : recipe.getIngredients()) {
      ingredients.getChildren().add(new Ingredient(item));
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

  private static class Ingredient extends StackPane implements ComponentUtils {
    public Ingredient(@NotNull InventoryItem item) {
      super();
      addClasses("ingredient");

      BorderPane content = new BorderPane();
      content.setLeft(
          // TODO: Should be improved if icons are added
          new Label(item.getType() + " " + item.getName())
      );

      Label amount = new Label(item.getQuantity() + " " + item.getUnit());
      amount.getStyleClass().add("ingredient-amount");
      content.setRight(amount);

      HBox crossedOutLine = new HBox();
      crossedOutLine.getStyleClass().add("crossed-out-line");

      getChildren().addAll(content, crossedOutLine);
      setOnMouseClicked(event -> toggleClass("marked"));
    }
  }
}
