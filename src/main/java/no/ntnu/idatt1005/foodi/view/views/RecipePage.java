package no.ntnu.idatt1005.foodi.view.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.view.components.StatefulPage;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;


/**
 * Class for displaying a recipe page.
 */
public class RecipePage extends StatefulPage implements CssUtils {

  private final String imagePath = "chicken-soup.jpg";
  private final String title = "Chicken Soup";
  private final int time = 30;
  private final String difficulty = "easy";
  private final String description = "A delicious chicken soup that is perfect for a cold winter day. It is easy to make and only requires a few ingredients. It is also very healthy and nutritious. Enjoy! 🍲";
  private final String instructions = "1. Boil the chicken in a pot with water for 30 minutes.\n2. Add the vegetables and spices.\n3. Let it simmer for 10 minutes.\n4. Serve hot with bread.";

  /**
   * Constructor for the RecipePage class.
   */
  public RecipePage() {
    addStylesheet("components/recipe/recipe-page");
    addClass("recipe");

    // Load the image and create an ImageView for it
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

    Label recipeTitle = new Label(title);
    recipeTitle.getStyleClass().add("recipe-title");

    imageUrl = LoadUtils.getImage("difficulty/" + difficulty + ".png");
    if (imageUrl == null) {
      throw new AssertionError("Image not found: " + imagePath);
    }
    ImageView difficultyImage = new ImageView(imageUrl);
    difficultyImage.getStyleClass().add("difficulty");
    difficultyImage.setPreserveRatio(true);
    difficultyImage.setFitHeight(30);

    HBox titleDifficultyBox = new HBox(recipeTitle, difficultyImage);
    titleDifficultyBox.getStyleClass().add("title-difficulty-box");

    Label recipeTime = new Label("⏱ " + time + " minutes");
    recipeTime.getStyleClass().add("recipe-time");

    BorderPane recipeHeader = new BorderPane();
    recipeHeader.setCenter(titleDifficultyBox);
    recipeHeader.setRight(recipeTime);
    recipeHeader.getStyleClass().add("recipe-header");

    Label recipeDescription = new Label(description);
    recipeDescription.getStyleClass().add("recipe-description");

    Label instructionsTitle = new Label("Instructions");
    instructionsTitle.getStyleClass().add("instructions-title");
    Label instructions = new Label(this.instructions);
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

    getChildren().add(recipeImage);
    setTop(recipeHeader);
    setCenter(recipeContent);
  }
}
