package no.ntnu.idatt1005.foodi.view.components.button;

import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    this(text, true);
  }

  /**
   * Constructor for the StandardButton class.
   *
   * @param text          The text to be displayed on the button
   * @param standardStyle Whether to use the standard style
   */
  public StandardButton(String text, boolean standardStyle) {
    super(text);
    addStylesheet("components/button/std-button");
    addClass(currentStyle.className);

    if (standardStyle) {
      addClass("std-button");
    }

    enableKeyboardNavigation();
  }

  /**
   * Enables keyboard navigation for the button.
   */
  private void enableKeyboardNavigation() {
    addClass("keyboard-navigable");
    setFocusTraversable(true);
    addEventHandler(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.ENTER) {
        // Perform the action as if the button was clicked
        fire();
        event.consume(); // Consume the event so it doesn't propagate further
      }
    });
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
    INFO("info"),
    SUBTLE("subtle");

    private final String className;

    Style(String className) {
      this.className = className;
    }
  }
}
