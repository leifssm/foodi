package no.ntnu.idatt1002.view.components.button;

/**
 * A standard dropdown button
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public class DropdownButton extends StandardButton {
  /**
   * Functional interface for the action of the dropdown button which takes one boolean parameter
   */
  public interface DropdownToggle {
    void toggle(boolean expanded);
  }
  private String expandedText = "▲";
  private String collapsedText = "▼";
  private boolean expanded = false;

  private final DropdownToggle mainAction;

  /**
   * Constructor for the dropdown button
   *
   * @param action The action to be performed when the button is clicked
   */
  public DropdownButton(DropdownToggle action) {
    super("");
    this.mainAction = action;
    setOnAction(e -> setIsExpanded(!expanded));
    setIsExpanded(expanded);
  }

  /**
   * Constructor for the dropdown button, with the option to set the text shown when the dropdown
   * is expanded or collapsed.
   *
   * @param expandedText The text to be shown when the dropdown is expanded
   * @param collapsedText The text to be shown when the dropdown is collapsed
   * @param action The action to be performed when the button is clicked
   */
  public DropdownButton(String expandedText, String collapsedText, DropdownToggle action) {
    this(action);
    this.expandedText = expandedText;
    this.collapsedText = collapsedText;
    updateText();
  }

  /**
   * @param expandedText the text to be shown when the dropdown is expanded
   */
  public void setExpandedText(String expandedText) {
    this.expandedText = expandedText;
    updateText();
  }

  /**
   * @param collapsedText the text to be shown when the dropdown is collapsed
   */
  public void setCollapsedText(String collapsedText) {
    this.collapsedText = collapsedText;
    updateText();
  }

  public String getExpandedText() {
    return expandedText;
  }

  public String getCollapsedText() {
    return collapsedText;
  }

  public void setIsExpanded(boolean state) {
    expanded = state;
    mainAction.toggle(expanded);
    updateText();
  }

  private void updateText() {
    setText(expanded ? expandedText : collapsedText);
  }

  public boolean getIsExpanded() {
    return expanded;
  }
}
