package no.ntnu.idatt1005.foodi.view.components;

import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;


/**
 * Input field with a name above it.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class NamedInputField extends VBox implements CssUtils {


  /**
   * Constructor for the NamedInputField class.
   *
   * @param inputField The input field
   * @param name       The name of the input field
   */
  public NamedInputField(Control inputField, String name) {
    super();
    Label nameLabel = new Label(name);
    setSpacing(6);
    getChildren().addAll(nameLabel, inputField);
  }
}
