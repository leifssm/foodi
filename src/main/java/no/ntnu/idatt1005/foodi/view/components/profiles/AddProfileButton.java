package no.ntnu.idatt1005.foodi.view.components.profiles;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;

/**
 * Class for creating a profile item. A profile item is a square button with a color and a label
 * with a name beneath.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class AddProfileButton extends VBox implements CssUtils {

  /**
   * Constructor for AddProfileButton.
   *
   * @param onClick Runnable to be executed when the button is clicked
   */
  public AddProfileButton(Runnable onClick) {
    super();

    addStylesheet("components/profiles/profile-item");
    addStylesheet("components/profiles/add-profile-button");
    addClass("profile-item");
    addClass("add-profile-button");

    Button button = new Button();

    Label plusIcon = new Label("+");
    button.setGraphic(plusIcon);

    button.setOnAction(e -> onClick.run());

    getChildren().addAll(button, new Label(" "));
  }


}
