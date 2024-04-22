package no.ntnu.idatt1005.foodi.view.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithIngredients;
import no.ntnu.idatt1005.foodi.view.components.StatefulPage;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;
import org.jetbrains.annotations.NotNull;


/**
 * Class for displaying a recipe page.
 */
public class RecipePage extends StatefulPage implements ComponentUtils {

  /**
   * Constructor for the RecipePage class.
   */
  public RecipePage() {
    addStylesheet("components/recipe/recipe-page");
    addClass("recipe");
  }

  public void render (@NotNull RecipeWithIngredients recipe) {
    // Render the recipe page
    // Load the image and create an ImageView for it
    String imagePath = recipe.getImagePath();
    String imageUrl = LoadUtils.getImage(imagePath);
    if (imageUrl == null) {
      throw new AssertionError("Image not found: " + imagePath);
    }
    ImageView recipeImage = new ImageView(imageUrl);
    recipeImage.getStyleClass().add("recipe-image");
    recipeImage.setPreserveRatio(true);
    recipeImage.setFitWidth(100);
    widthProperty().addListener(w -> {
      recipeImage.setFitWidth(getWidth());
      setPadding(new Insets(getWidth() / 2.2, 0, 0, 0));
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
    Button addIngredientsButton = new Button("Add to Shopping List");
    addIngredientsButton.getStyleClass().add("add-ingredients-button");
    VBox ingredientsList = new VBox(new Label("Chicken"), new Label("Vegetables"),
        new Label("Spices"));
    VBox ingredientsBox = new VBox(ingredientsTitle, ingredientsList, addIngredientsButton);
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
}
