package no.ntnu.idatt1002.view.components.cookbook;

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
import no.ntnu.idatt1002.view.location.LocationHandler;
import no.ntnu.idatt1002.view.utils.CssUtils;
import no.ntnu.idatt1002.view.utils.LoadUtils;

public class RecipeCard extends StackPane implements CssUtils {

  public RecipeCard(String title, String duration, String imagePath) {
    super();
    addStylesheet("components/cookbook-grid/recipe-card");
    addClass("recipe-card");

    // Load the image and create an ImageView for it
    String imageUrl = LoadUtils.getImage(imagePath);
    assert imageUrl != null;
    ImageView backgroundImage = new ImageView(imageUrl);
    backgroundImage.getStyleClass().add("recipe-card-image");
    backgroundImage.setPreserveRatio(true);
    backgroundImage.fitWidthProperty().bind(this.widthProperty());
    backgroundImage.fitHeightProperty().bind(this.heightProperty());

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
    Timeline clickedTimeline = new Timeline(
        new KeyFrame(Duration.seconds(0.03), new KeyValue(colorAdjust.brightnessProperty(), -0.6)),
        new KeyFrame(Duration.seconds(0.03), new KeyValue(this.scaleXProperty(), 0.97)),
        new KeyFrame(Duration.seconds(0.03), new KeyValue(this.scaleYProperty(), 0.97)));

    // Listener to trigger animation on hover
    this.hoverProperty().addListener((obs, wasHovered, isNowHovered) -> {
      if (isNowHovered) {
        fadeInTimeline.play();
      } else {
        fadeOutTimeline.play();
      }
    });

    // On mouse down, darken the image
    this.setOnMousePressed(event -> {
      clickedTimeline.play();
    });

    // On click, open the recipe
    this.setOnMouseClicked(event -> {
      LocationHandler.createSetter("inventory").run();
    });

    Rectangle clip = new Rectangle();
    clip.setArcWidth(5); // Corner radius
    clip.setArcHeight(5);
    clip.widthProperty().bind(this.widthProperty());
    clip.heightProperty().bind(this.heightProperty());
    this.setClip(clip);

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
    this.getChildren().addAll(backgroundImage, contentBox);
  }
}

