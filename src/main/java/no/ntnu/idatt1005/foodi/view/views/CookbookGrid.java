package no.ntnu.idatt1005.foodi.view.views;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Recipe;
import no.ntnu.idatt1005.foodi.view.components.TitledPage;
import no.ntnu.idatt1005.foodi.view.components.cookbook.RecipeCard;
import no.ntnu.idatt1005.foodi.view.components.cookbook.RecipeCardRow;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * The cookbook grid view.
 *
 * <p> This class is a custom component for displaying the cookbook in grid view. </p>
 */
public class CookbookGrid extends TitledPage implements ComponentUtils {

  /**
   * The constructor of the cookbook grid view.
   */
  public CookbookGrid() {
    super("Cookbook");
    addStylesheet("components/cookbook-grid/cookbook-grid");
    addClass("cookbook");

    RecipeCard[] twentyMinuteRecipes = {
        new RecipeCard("Pizza", "30 minutes", "chicken-soup.jpg"),
        new RecipeCard("Pasta", "20 minutes", "chicken-soup.jpg"),
        new RecipeCard("Salad", "10 minutes", "chicken-soup.jpg")
    };

    RecipeCard[] popularRecipes = {
        new RecipeCard("Pasta", "20 minutes", "chicken-soup.jpg"),
        new RecipeCard("Pizza", "30 minutes", "chicken-soup.jpg"),
        new RecipeCard("Salad", "10 minutes", "chicken-soup.jpg")
    };

    RecipeCard[] vegetarianRecipes = {
        new RecipeCard("Pasta", "20 minutes", "chicken-soup.jpg"),
        new RecipeCard("Pizza", "30 minutes", "chicken-soup.jpg"),
        new RecipeCard("Salad", "10 minutes", "chicken-soup.jpg")
    };

    RecipeCard[] cheapestRecipes = {
        new RecipeCard("Pasta", "20 minutes", "chicken-soup.jpg"),
        new RecipeCard("Pizza", "30 minutes", "chicken-soup.jpg"),
        new RecipeCard("Salad", "10 minutes", "chicken-soup.jpg")
    };

    VBox recipeCardColumn = new VBox();
    recipeCardColumn.getChildren().addAll(
        new RecipeCardRow("20 minute meals", twentyMinuteRecipes),
        new RecipeCardRow("Popular", popularRecipes),
        new RecipeCardRow("Vegetarian", vegetarianRecipes),
        new RecipeCardRow("Cheapest", cheapestRecipes)
    );

    // Wrap the VBox in a ScrollPane
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(recipeCardColumn);
    scrollPane.getStyleClass().add("cookbook-grid-scroll");
    scrollPane.setFitToWidth(true);

    setContent(scrollPane, true); // TODO: Figure out if it should stretch or not (prolly yes)
  }

  public void render(RecipeCardRow... recipeCardRows) {
    VBox recipeCardColumn = new VBox();
    for (RecipeCardRow recipeCardRow : recipeCardRows) {
      recipeCardColumn.getChildren().add(recipeCardRow);
    }
    // Wrap the VBox in a ScrollPane
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(recipeCardColumn);
    scrollPane.getStyleClass().add("cookbook-grid-scroll");
    scrollPane.setFitToWidth(true);

    setContent(scrollPane, true); // TODO: Figure out if it should stretch or not (prolly yes)
  }
}
