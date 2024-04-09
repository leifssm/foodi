package no.ntnu.idatt1005.foodi.view.components.button;

import org.jetbrains.annotations.NotNull;

/**
 * A standard dropdown button.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class DropdownButton extends StandardButton {

  private final DropdownToggle mainAction;
  private String expandedText = "▲";

  private String collapsedText = "▼";

  private boolean expanded = false;

  /**
   * Constructor for the dropdown button, with the option to set the text shown when the dropdown is
   * expanded or collapsed.
   *
   * @param expandedText  The text to be shown when the dropdown is expanded
   * @param collapsedText The text to be shown when the dropdown is collapsed
   * @param action        The action to be performed when the button is clicked
   */
  public DropdownButton(String expandedText, String collapsedText, @NotNull DropdownToggle action) {
    this(action);
    this.expandedText = expandedText;
    this.collapsedText = collapsedText;
    updateText();
  }

  /**
   * Constructor for the dropdown button.
   *
   * @param action The action to be performed when the button is clicked
   */
  public DropdownButton(@NotNull DropdownToggle action) {
    super("");
    this.mainAction = action;
    setOnAction(e -> setIsExpanded(!expanded));
    action.toggle(expanded);
    setIsExpanded(expanded);
    updateText();
  }

  /**
   * Updates the text of the button to match the current state.
   */
  private void updateText() {
    setText(expanded ? expandedText : collapsedText);
  }

  public String getExpandedText() {
    return expandedText;
  }

  /**
   * Sets the text to be shown when the dropdown is expanded.
   */
  public void setExpandedText(String expandedText) {
    this.expandedText = expandedText;
    updateText();
  }

  public String getCollapsedText() {
    return collapsedText;
  }

  /**
   * Sets the text to be shown when the dropdown is collapsed.
   */
  public void setCollapsedText(String collapsedText) {
    this.collapsedText = collapsedText;
    updateText();
  }

  /**
   * Returns whether the dropdown button is expanded or not.
   */
  public boolean getIsExpanded() {
    return expanded;
  }

  /**
   * Sets whether the dropdown button is expanded or not, and runs the on click action of the state
   * changed.
   *
   * @param state The state to set the dropdown button to
   */
  public void setIsExpanded(boolean state) {
    if (expanded == state) {
      return;
    }
    expanded = state;
    mainAction.toggle(expanded);
    updateText();
  }

  /**
   * Functional interface for the action of the dropdown button which takes one boolean parameter.
   */
  public interface DropdownToggle {

    void toggle(boolean expanded);
  }
}
