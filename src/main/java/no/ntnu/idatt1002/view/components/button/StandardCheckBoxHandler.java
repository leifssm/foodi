package no.ntnu.idatt1002.view.components.button;

import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class for handling a group of checkboxes, and a main checkbox that controls them.
 *
 * @version 1.0
 * @author Leif Mørstad
 * @see StandardCheckBox
 */
public class StandardCheckBoxHandler {
  private @Nullable StandardCheckBox mainCheckBox = null;
  private @NotNull StandardCheckBox[] checkBoxes;

  /**
   * Constructor for the StandardCheckBoxHandler class.
   *
   * @param checkBoxes The checkboxes to handle
   */
  public StandardCheckBoxHandler(StandardCheckBox... checkBoxes) {
    this.checkBoxes = checkBoxes;
  }

  /**
   * Constructor for the StandardCheckBoxHandler class.
   */
  public StandardCheckBoxHandler() {
    this(new StandardCheckBox[0]);
  }

  /**
   * Binds the main checkbox to the handler.
   *
   * @param mainCheckBox The checkbox to bind as a main controller
   */
  public void bindMainCheckBox(@NotNull StandardCheckBox mainCheckBox) {
    this.mainCheckBox = mainCheckBox;
    appendEvent(mainCheckBox, () -> setAll(mainCheckBox.isSelected()));
  }

  /**
   * Appends an event to a checkbox.
   *
   * @param checkBox The checkbox to append the event to
   * @param event The event to append
   */
  private static void appendEvent(@NotNull StandardCheckBox checkBox, @NotNull Runnable event) {
    EventHandler<ActionEvent> oldEvent = checkBox.getOnAction();
    if (oldEvent == null) {
      checkBox.setOnAction(e -> event.run());
    } else {
      checkBox.setOnAction(e -> {
        oldEvent.handle(e);
        event.run();
      });
    }
  }

  /**
   * Binds a checkbox to the handler.
   *
   * @param checkBox The checkbox to bind
   */
  public void bindCheckBox(@NotNull StandardCheckBox checkBox) {
    appendEvent(checkBox, this::updateMain);

    StandardCheckBox[] newCheckBoxes = Arrays.copyOf(checkBoxes, checkBoxes.length + 1);
    newCheckBoxes[checkBoxes.length] = checkBox;

    checkBoxes = newCheckBoxes;
  }

  /**
   * Returns the state of the main checkbox.
   *
   * @return Either "all", "some" or "none"
   */
  public @MagicConstant String getMainCheckBoxState() {
    if (checkBoxes.length == 0) {
      return "none";
    }

    boolean allSelected = checkBoxes[0].isSelected();
    boolean state;
    for (int i = 1; i < checkBoxes.length; i++) {
      state = checkBoxes[i].isSelected();

      if (state != allSelected) {
        return "some";
      }
    }
    return allSelected ? "all" : "none";
  }

  /**
   * Sets all checkboxes to the given state.
   *
   * @param selected Whether the checkboxes should be selected
   */
  public void setAll(boolean selected) {
    for (StandardCheckBox checkBox : checkBoxes) {
      checkBox.setSelected(selected);
    }
    updateMain();
  }

  /**
   * Updates the main checkbox.
   */
  private void updateMain() {
    if (mainCheckBox == null) {
      return;
    }
    String state = getMainCheckBoxState();
    switch (state) {
      case "all":
        mainCheckBox.setSelected(true);
        mainCheckBox.setIndeterminate(false);
        break;
      case "some":
        mainCheckBox.setSelected(false);
        mainCheckBox.setIndeterminate(true);
        break;
      case "none":
        mainCheckBox.setSelected(false);
        mainCheckBox.setIndeterminate(false);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + state);
    }
  }
}
