package no.ntnu.idatt1005.foodi.view.views;

import java.text.DecimalFormat;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithIngredients;
import no.ntnu.idatt1005.foodi.view.components.StatefulPage;
import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.components.button.StandardButton.Style;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Class for displaying a recipe page.
 */
public class RecipePage extends StatefulPage implements ComponentUtils {

  private int portions = 4;
  /**
   * Constructor for the RecipePage class.
   */
  public RecipePage() {
    addStylesheet("components/recipe/recipe-page");
    addClass("recipe");
  }

  public void render(@NotNull RecipeWithIngredients recipe, @NotNull RecipeAdder onAddRecipe) {
    // Render the recipe page
    // Load the image and create an ImageView for it
    portions = 4;
    String imagePath = recipe.getImagePath();
    String imageUrl = LoadUtils.getImage(imagePath);
    if (imageUrl == null) {
      throw new AssertionError("Image not found: " + imagePath);
    }
    ImageView recipeImage = new ImageView(imageUrl);
    recipeImage.getStyleClass().add("recipe-image");
    recipeImage.setPreserveRatio(true);
    recipeImage.setFitWidth(100);
    widthProperty().subscribe(w -> {
      recipeImage.setFitWidth(w.intValue());
      setPadding(new Insets(w.intValue() / 2.3, 0, 0, 0));
    });

    Label recipeTitle = new Label(recipe.getName());
    recipeTitle.getStyleClass().add("recipe-title");

    imageUrl = LoadUtils.getImage("difficulty/" + recipe.getDifficulty() + ".png");
    if (imageUrl == null) {
      throw new AssertionError("Image not found: " + imagePath);
    }
    ImageView difficultyImage = new ImageView(imageUrl);
    difficultyImage.getStyleClass().add("difficulty");
    difficultyImage.setPreserveRatio(true);
    difficultyImage.setFitHeight(30);

    HBox titleDifficultyBox = new HBox(recipeTitle, difficultyImage);
    titleDifficultyBox.getStyleClass().add("title-difficulty-box");

    Label recipeTime = new Label("⏱ " + recipe.getDuration() + " minutes");
    recipeTime.getStyleClass().add("recipe-time");

    BorderPane recipeHeader = new BorderPane();
    recipeHeader.setCenter(titleDifficultyBox);
    recipeHeader.setRight(recipeTime);
    recipeHeader.getStyleClass().add("recipe-header");

    Label recipeDescription = new Label(recipe.getDescription());
    recipeDescription.getStyleClass().add("recipe-description");

    Label instructionsTitle = new Label("Instructions");
    instructionsTitle.getStyleClass().add("instructions-title");

    Label instructions = new Label(recipe.getInstruction());
    instructions.getStyleClass().add("instructions");
    VBox instructionsBox = new VBox(instructionsTitle, instructions);

    Label ingredientsTitle = new Label("Ingredients");
    ingredientsTitle.getStyleClass().add("ingredients-title");

    HBox portionsBox = new HBox();
    Label portionsSymbol = new Label("\uD83D\uDC64");
    portionsSymbol.getStyleClass().add("portions-symbol");
    Button decreasePortions = new StandardButton("-").setType(Style.SUBTLE);
    decreasePortions.getStyleClass().add("portions-button");
    Label portionsValue = new Label(String.valueOf(portions));
    portionsValue.getStyleClass().add("portions-text");
    Button increasePortions = new StandardButton("+").setType(Style.SUBTLE);
    increasePortions.getStyleClass().add("portions-button");

    portionsBox.getChildren().addAll(ingredientsTitle, portionsSymbol, decreasePortions, portionsValue, increasePortions);

    StandardButton addIngredientsButton = new StandardButton(
        "Add to Shopping List",
        () -> onAddRecipe.addRecipe(portions)
    )
        .setType(Style.SUCCESS);
    addIngredientsButton.getStyleClass().add("add-ingredients-button");

    VBox ingredientsList = new VBox();
    ingredientsList.getChildren().add(ingredientsList(recipe));

    decreasePortions.setOnAction(e -> {
      if (portions > 1) {
        portions--;
        portionsValue.setText(String.valueOf(portions));
        ingredientsList.getChildren().clear();
        ingredientsList.getChildren().add(ingredientsList(recipe));
      }
    });
    increasePortions.setOnAction(e -> {
      if (portions < 9) {
        portions++;
        portionsValue.setText(String.valueOf(portions));
        ingredientsList.getChildren().clear();
        ingredientsList.getChildren().add(ingredientsList(recipe));
      }
    });

    VBox ingredientsBox = new VBox(portionsBox, ingredientsList, addIngredientsButton);
    ingredientsBox.getStyleClass().add("ingredients-box");

    BorderPane ingredientsInstructionsBox = new BorderPane();
    ingredientsInstructionsBox.setCenter(instructionsBox);
    ingredientsInstructionsBox.setRight(ingredientsBox);

    BorderPane recipeContent = new BorderPane();
    recipeContent.setLeft(recipeDescription);
    recipeContent.setBottom(ingredientsInstructionsBox);
    recipeContent.getStyleClass().add("recipe-content");

    getChildren().clear();
    getChildren().add(recipeImage);
    setTop(recipeHeader);
    setCenter(recipeContent);
  }

  private VBox ingredientsList(RecipeWithIngredients recipe) {
    VBox ingredientsList = new VBox();
    List<AmountedIngredient> recipeIngredients = recipe.getIngredients();
    for (AmountedIngredient ingredient : recipeIngredients) {
      BorderPane ingredientRow = new BorderPane();
      Label ingredientName = new Label(ingredient.getName());
      ingredientRow.setLeft(ingredientName);
      DecimalFormat decimalFormat = new DecimalFormat("0.#");
      Label ingredientAmount = new Label(decimalFormat.format(ingredient.getAmount() * portions) + " " + ingredient.getUnit().getName());
      ingredientRow.setRight(ingredientAmount);
      ingredientsList.getChildren().add(ingredientRow);
    }
    return ingredientsList;
  }

  /**
   * Interface for adding a recipe.
   */
  public interface RecipeAdder {

    /**
     * Method for adding a recipe.
     *
     * @param portions The number of portions to add, cannot be less than 1
     */
    void addRecipe(int portions);
  }
}
