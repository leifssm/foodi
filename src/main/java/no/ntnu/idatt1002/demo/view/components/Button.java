package no.ntnu.idatt1002.demo.view.components;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class Button extends JButton {
  public Button(@NotNull String label, @NotNull Runnable action) {
    super(label);
    setFocusable(false);
    addActionListener(e -> action.run());
  }
}
