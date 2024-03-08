package no.ntnu.idatt1002.view.components.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.Arrays;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandardCheckBoxHandler {
  private @Nullable StandardCheckBox mainCheckBox = null;
  private @NotNull StandardCheckBox[] checkBoxes;
  public StandardCheckBoxHandler(StandardCheckBox... checkBoxes) {
    this.checkBoxes = checkBoxes;
  }
  public StandardCheckBoxHandler() {
    this(new StandardCheckBox[0]);
  }

  public void bindMainCheckBox(@NotNull StandardCheckBox mainCheckBox) {
    this.mainCheckBox = mainCheckBox;
    appendEvent(mainCheckBox, () -> setAll(mainCheckBox.isSelected()));
  }

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

  public void bindCheckBox(@NotNull StandardCheckBox checkBox) {
    appendEvent(checkBox, this::updateMain);

    StandardCheckBox[] newCheckBoxes = Arrays.copyOf(checkBoxes, checkBoxes.length + 1);
    newCheckBoxes[checkBoxes.length] = checkBox;

    checkBoxes = newCheckBoxes;
  }

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

  public void setAll(boolean selected) {
    for (StandardCheckBox checkBox : checkBoxes) {
      checkBox.setSelected(selected);
    }
    updateMain();
  }

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
    }
  }
}
