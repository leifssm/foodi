package no.ntnu.idatt1002.view.components.button;

import javafx.scene.control.CheckBox;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class StandardCheckBox extends CheckBox implements CssUtils {
  public StandardCheckBox(boolean active) {
    super();
    setSelected(active);
    addStylesheet("components/button/std-checkbox");
    addClass("std-checkbox");
  }

  public StandardCheckBox() {
    this(false);
  }

  public void setScale(double scale) {
    setScaleX(scale);
    setScaleY(scale);
  }
}
