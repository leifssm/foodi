package no.ntnu.idatt1002.view.old.components;

import javax.swing.*;
import java.awt.*;

/**
 * A styled sidebar component
 *
 * @version 1.0
 * @since 2024-04-15
 * @see JPanel
 * @author Leif Mørstad
 */
public class Sidebar extends JPanel {
  /**
   * The width of the sidebar
   */
  private static final int WIDTH = 200;
  private static final Color BACKGROUND_COLOR = new Color(0x1c1c1c);
  public Sidebar() {
    super();
    setBackground(BACKGROUND_COLOR);
    setPreferredSize(new Dimension(WIDTH, 0));

    final no.ntnu.idatt1002.view.old.components.Button[] buttons = new no.ntnu.idatt1002.view.old.components.Button[]{
        new no.ntnu.idatt1002.view.old.components.Button("Inventory", () -> System.out.println("Filled"), no.ntnu.idatt1002.view.old.components.Button.Style.PRIMARY, no.ntnu.idatt1002.view.old.components.Button.Type.PLAIN),
        new no.ntnu.idatt1002.view.old.components.Button("Cookbook", () -> System.out.println("Filled"), no.ntnu.idatt1002.view.old.components.Button.Style.PRIMARY, no.ntnu.idatt1002.view.old.components.Button.Type.PLAIN),
        new no.ntnu.idatt1002.view.old.components.Button("Shopping List", () -> System.out.println("Filled"), no.ntnu.idatt1002.view.old.components.Button.Style.PRIMARY, no.ntnu.idatt1002.view.old.components.Button.Type.PLAIN),
        new no.ntnu.idatt1002.view.old.components.Button("About", () -> System.out.println("Filled"), no.ntnu.idatt1002.view.old.components.Button.Style.PRIMARY, no.ntnu.idatt1002.view.old.components.Button.Type.PLAIN)

    };

    final GridLayout layout = new GridLayout(buttons.length, 1);
    setAlignmentX(CENTER_ALIGNMENT);
    setAlignmentY(CENTER_ALIGNMENT);
    setLayout(layout);

    for (Button button : buttons) {
      add(button);
    }
  }
}
