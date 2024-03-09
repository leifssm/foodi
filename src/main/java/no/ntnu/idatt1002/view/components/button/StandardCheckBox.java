package no.ntnu.idatt1002.view.components.button;

import javafx.scene.control.CheckBox;
import no.ntnu.idatt1002.view.utils.CssUtils;

/**
 * Class for creating a standard styled checkbox.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class StandardCheckBox extends CheckBox implements CssUtils {
  /**
   * Constructor for the StandardCheckBox class.
   *
   * @param active Whether the checkbox should be selected or not at first
   */
  public StandardCheckBox(boolean active) {
    super();
    setSelected(active);
    addStylesheet("components/button/std-checkbox");
    addClass("std-checkbox");
  }

  /**
   * Constructor for the StandardCheckBox class. Defaults the checkbox to be unselected.
   */
  public StandardCheckBox() {
    this(false);
  }

  /**
   * Sets the scale of the checkbox.
   *
   * @param scale The scale to set
   */
  public void setScale(double scale) {
    setScaleX(scale);
    setScaleY(scale);
  }
}
