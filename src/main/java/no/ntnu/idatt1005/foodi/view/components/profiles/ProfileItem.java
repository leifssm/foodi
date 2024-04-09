package no.ntnu.idatt1002.view.components.profiles;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.utils.CssUtils;

/**
 * Class for creating a profile item. A profile item is a square button with a color and a label
 * with a name beneath.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class ProfileItem extends VBox implements CssUtils {

  /**
   * Constructor for the ProfileItem class.
   *
   * @param name    The name of the profile
   * @param color   The color of the profile
   * @param onClick The action to perform when the profile item is clicked
   */
  public ProfileItem(String name, String color, Runnable onClick) {
    super();
    addStylesheet("components/profiles/profile-item");
    addClass("profile-item");

    // square button with color and no text
    Button button = new Button();
    button.setStyle("-fx-background-color: " + color + ";");
    button.setOnAction(e -> onClick.run());

    // label with name
    Label label = new Label(name);

    // add button and label to the profile item
    getChildren().addAll(button, label);
  }
}
