package no.ntnu.idatt1005.foodi.view.components.button;

import javafx.scene.control.Button;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Class for creating a standard styled button.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class StandardButton extends Button implements ComponentUtils {

  private Style currentStyle = Style.NORMAL;

  /**
   * Constructor for the StandardButton class.
   *
   * @param text   The text to be displayed on the button
   * @param action The function to run when the button is clicked
   * @param style  The style of the button
   */
  public StandardButton(String text, @NotNull Runnable action, @NotNull Style style) {
    this(text, action);
    setType(style);
  }

  /**
   * Constructor for the StandardButton class. Defaults the style to {@link Style#NORMAL}
   *
   * @param text   The text to be displayed on the button
   * @param action The function to run when the button is clicked
   */
  public StandardButton(String text, @NotNull Runnable action) {
    this(text);
    setOnAction(event -> action.run());
  }

  /**
   * Sets the style of the button.
   *
   * @param type The style of the button
   * @return The button
   */
  public StandardButton setType(@NotNull Style type) {
    removeClass(currentStyle.className);
    addClass(type.className);
    currentStyle = type;
    return this;
  }

  /**
   * Constructor for the StandardButton class. Defaults the style to {@link Style#NORMAL}
   *
   * @param text The text to be displayed on the button
   */
  public StandardButton(String text) {
    super(text);
    addStylesheet("components/button/std-button");
    addClasses("std-button", currentStyle.className);
  }

  /**
   * Enum for the different button styles.
   */
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
}
