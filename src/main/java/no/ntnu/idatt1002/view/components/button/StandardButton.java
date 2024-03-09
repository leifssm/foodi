package no.ntnu.idatt1002.view.components.button;

import javafx.scene.control.Button;
import no.ntnu.idatt1002.view.utils.CssUtils;

/**
 * Class for creating a standard styled button
 *
 * @version 1.1
 * @author Leif Mørstad
 */
public class StandardButton extends Button implements CssUtils {
  public enum Style {
    NORMAL("normal"),
    PRIMARY("primary"),
    SECONDARY("secondary"),
    SUCCESS("success"),
    ERROR("error"),
    WARNING("warning"),
    INFO("info");

    private final String className;

    Style(String className) {
      this.className = className;
    }
  }
  private Style currentStyle = Style.NORMAL;

  public StandardButton(String text) {
    super(text);
    addStylesheet("components/button/std-button");
    addClasses("std-button", currentStyle.className);
  }

  public StandardButton(String text, Runnable action) {
    this(text);
    setOnAction(event -> action.run());
  }

  public StandardButton(String text, Runnable action, Style style) {
    this(text, action);
    addClass(style.className);
  }

  public StandardButton setType(Style type) {
    removeClass(currentStyle.className);
    addClass(type.className);
    currentStyle = type;
    return this;
  }
}
