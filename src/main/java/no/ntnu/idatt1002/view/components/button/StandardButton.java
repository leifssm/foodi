package no.ntnu.idatt1002.view.components.button;

import javafx.scene.control.Button;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class StandardButton extends Button implements CssUtils {
  public StandardButton(String text) {
    super(text);
    addStylesheet("components/button/std-button");
    addClass("std-button");
  }

  public StandardButton(String text, Runnable action) {
    this(text);
    setOnAction(event -> action.run());
  }
}
