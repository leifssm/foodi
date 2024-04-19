package no.ntnu.idatt1005.foodi.view.components.button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class for handling a group of checkboxes, and a main checkbox that controls them.
 *
 * @param <DataT> The type of data the checkboxes represent
 * @author Leif Mørstad
 * @version 1.1
 * @see StandardCheckBox
 */
public class StandardCheckBoxHandler<DataT> {

  private final @NotNull HashMap<StandardCheckBox, DataT> checkBoxDataMap = new HashMap<>();
  private @Nullable StandardCheckBox mainCheckBox = null;

  /**
   * Constructor for the StandardCheckBoxHandler class.
   */
  public StandardCheckBoxHandler() {
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
   * @param event    The event to append
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
   * Sets all checkboxes to the given state.
   *
   * @param selected Whether the checkboxes should be selected
   */
  public void setAll(boolean selected) {
    for (var selectedData : checkBoxDataMap.entrySet()) {
      selectedData.getKey().setSelected(selected);
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

  /**
   * Returns the state of the main checkbox.
   *
   * @return Either "all", "some" or "none"
   */
  public @MagicConstant String getMainCheckBoxState() {
    if (checkBoxDataMap.isEmpty()) {
      return "none";
    }

    Boolean allSelected = null;
    for (var checkbox : checkBoxDataMap.keySet()) {
      if (allSelected == null) {
        allSelected = checkbox.isSelected();
        continue;
      }

      if (checkbox.isSelected() != allSelected) {
        return "some";
      }
    }
    return allSelected ? "all" : "none";
  }

  /**
   * Binds a checkbox to the handler.
   *
   * @param checkBox The checkbox to bind
   */
  public void bindCheckBox(@NotNull StandardCheckBox checkBox, DataT ingredient) {
    if (checkBoxDataMap.containsValue(ingredient)) {
      throw new IllegalArgumentException("The ingredient is already bound to a checkbox");
    }
    checkBoxDataMap.put(checkBox, ingredient);
    appendEvent(checkBox, this::updateMain);
  }

  /**
   * Returns a list of all data corresponding to the selected data.
   *
   * @return a list of selected data
   */
  public List<DataT> getSelectedData() {
    ArrayList<DataT> selectedData = new ArrayList<>();

    for (var entry : checkBoxDataMap.entrySet()) {
      if (entry.getKey().isSelected()) {
        selectedData.add(entry.getValue());
      }
    }

    return selectedData;
  }
}
