package no.ntnu.idatt1005.foodi.view.components.cookbook;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;

/**
 * A recipe card component.
 *
 * <p>This class is a custom component for displaying a recipe card.
 * It is used in the cookbook grid view. </p>
 */

public class RecipeCard extends StackPane implements ComponentUtils {


  /**
   * The constructor of the recipe card component.
   *
   * @param title     The title of the recipe
   * @param duration  The duration of the recipe
   * @param imagePath The path to the image of the recipe
   */
  public RecipeCard(String title, String duration, String imagePath) {
    super();
    addStylesheet("components/cookbook-grid/recipe-card");
    addClass("recipe-card");

    cursorProperty().setValue(javafx.scene.Cursor.HAND);

    // Load the image and create an ImageView for it
    String imageUrl = LoadUtils.getImage(imagePath);
    if (imageUrl == null) {
      throw new AssertionError("Image not found: " + imagePath);
    }

    ImageView backgroundImage = new ImageView(imageUrl);
    backgroundImage.setPreserveRatio(true);
    backgroundImage.fitWidthProperty().bind(widthProperty());
    backgroundImage.fitHeightProperty().bind(heightProperty());

    // Initial ColorAdjust effect with no brightness adjustment
    ColorAdjust colorAdjust = new ColorAdjust();
    colorAdjust.setBrightness(-0.5);
    backgroundImage.setEffect(colorAdjust);

    // Timeline for animating brightness and scale change on hover
    Timeline fadeInTimeline = new Timeline(
        new KeyFrame(Duration.seconds(0.07), new KeyValue(colorAdjust.brightnessProperty(), -0.3)),
        new KeyFrame(Duration.seconds(0.1), new KeyValue(this.scaleXProperty(), 1.03)),
        new KeyFrame(Duration.seconds(0.1), new KeyValue(this.scaleYProperty(), 1.03)));
    Timeline fadeOutTimeline = new Timeline(
        new KeyFrame(Duration.seconds(0.07), new KeyValue(colorAdjust.brightnessProperty(), -0.5)),
        new KeyFrame(Duration.seconds(0.07), new KeyValue(this.scaleXProperty(), 1)),
        new KeyFrame(Duration.seconds(0.07), new KeyValue(this.scaleYProperty(), 1)));

    // Timeline for animating brightness and scale change on click
    Timeline clickedTimeline = new Timeline(
        new KeyFrame(Duration.seconds(0.03), new KeyValue(colorAdjust.brightnessProperty(), -0.6)),
        new KeyFrame(Duration.seconds(0.03), new KeyValue(this.scaleXProperty(), 0.97)),
        new KeyFrame(Duration.seconds(0.03), new KeyValue(this.scaleYProperty(), 0.97)));

    // Listener to trigger animation on hover
    hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
      if (isNowHovered) {
        fadeInTimeline.play();
      } else {
        fadeOutTimeline.play();
      }
    });

    // On mouse down
    setOnMousePressed(event -> clickedTimeline.play());

    // On click
    this.setOnMouseClicked(event -> LocationHandler.setLocation("inventory"));

    // Create a clip for the rounded corners
    Rectangle clip = new Rectangle();
    clip.setArcWidth(5);
    clip.setArcHeight(5);
    clip.widthProperty().bind(this.widthProperty());
    clip.heightProperty().bind(this.heightProperty());
    setClip(clip);

    // Create a container for the text and other content
    VBox contentBox = new VBox();
    contentBox.getStyleClass().add("recipe-card-content");

    Label recipeTitle = new Label(title);
    recipeTitle.getStyleClass().add("recipe-card-title");
    contentBox.getChildren().add(recipeTitle);

    Label recipeDuration = new Label(duration);
    recipeDuration.getStyleClass().add("recipe-card-duration");
    contentBox.getChildren().add(recipeDuration);

    // First, add the image to the stack, then the content on top
    getChildren().addAll(backgroundImage, contentBox);
  }
}

