package no.ntnu.idatt1002.view.old.components;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * A styled button
 *
 * @version 1.1
 * @since 2024-04-15
 * @see JButton
 * @author Leif Mørstad
 */
public class Button extends JButton {
  public enum Style {
    PRIMARY(new Color(0x16992b), new Color(0xffffff)),
    SECONDARY(new Color(0x1c1c1c), new Color(0x1c1c1c)),
    SUCCESS(new Color(0x2c2c2c), new Color(0x2c2c2c)),
    ERROR(new Color(0x3c3c3c), new Color(0x3c3c3c)),
    WARNING(new Color(0x4c4c4c), new Color(0x4c4c4c)),
    INFO(new Color(0x5c5c5c), new Color(0x5c5c5c)),
    DISABLED(new Color(0xb4b4b4), new Color(0x6c6c6c));

    public final Color main;
    public final Color accentColor;

    Style(Color main, Color accentColor) {
      this.main = main;
      this.accentColor = accentColor;
    }
  }
  public enum Type {
    FILLED,
    OUTLINE,
    PLAIN,
  }
  public enum Padding {
    NORMAL(10, 10, 10, 10);

    public final Insets insets;

    Padding(int top, int right, int bottom, int left) {
      this.insets = new Insets(top, right, bottom, left);
    }
  }
  private Type type = Type.FILLED;
  private Style style = Style.PRIMARY;

  private final Padding padding = Padding.NORMAL;

  public Button(
      @NotNull String label,
      @NotNull Runnable action,
      @NotNull Style style,
      @NotNull Type type
  ) {
    super(label);

    setFont(ViewUtils.DEFAULT_FONT);
    setFocusable(false);
    addActionListener(e -> action.run());

    getModel().addChangeListener(e -> {
      ButtonModel button = (ButtonModel) e.getSource();
      boolean isInverted = button.isArmed() || button.isPressed();
      updateDisplay(isInverted);
    });

    setStyle(style);
    setType(type);
  }
  public void setStyle(@NotNull Style style) {
    this.style = style;
    updateDisplay(false);
  }

  public void setType(@NotNull Type type) {
    this.type = type;
    updateDisplay(false);
  }

  private void updateDisplay(boolean invertColors) {
    Color mainColor = !invertColors ? this.style.main : this.style.accentColor;
    Color accentColor = !invertColors ? this.style.accentColor : this.style.main;
    switch (this.type) {
      case FILLED -> {
        setOpaque(true);
        setBorderPainted(false);

        setBorder(new EmptyBorder(padding.insets));

        setForeground(accentColor);
        setBackground(mainColor);
      }
      case OUTLINE -> {
        setOpaque(false);
        setBorderPainted(true);

        final int borderWidth = 2;
        setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(mainColor, borderWidth),
                new EmptyBorder(
                    padding.insets.top - borderWidth,
                    padding.insets.left - borderWidth,
                    padding.insets.bottom - borderWidth,
                    padding.insets.right - borderWidth
                )
            )
        );
        setMargin(new Insets(10, 0, 10, 30));

        setForeground(mainColor);
        setBackground(accentColor);
      }
      case PLAIN -> {
        setOpaque(false);
        setBorderPainted(false);
        setForeground(mainColor);
        setBorder(new EmptyBorder(padding.insets));
      }
    }
  }
}
