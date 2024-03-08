package no.ntnu.idatt1002.view.components.button;

public class DropdownButton extends StandardButton {
  public interface DropdownToggle {
    void toggle(boolean expanded);
  }
  private boolean expanded = false;
  private final DropdownToggle action;

  public DropdownButton(String text, DropdownToggle action) {
    super(text);
    this.action = action;
    setOnAction(e -> {
      expanded = !expanded;
      this.action.toggle(expanded);
    });
    action.toggle(expanded);
  }

  public DropdownButton(DropdownToggle action) {
    this("▼", action);
  }

  public void setIsExpanded(boolean state) {
    expanded = state;
    action.toggle(expanded);
  }

  public boolean getIsExpanded() {
    return expanded;
  }
}
