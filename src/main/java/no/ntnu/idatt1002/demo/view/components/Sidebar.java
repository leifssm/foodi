package no.ntnu.idatt1002.demo.view.components;

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

    add(new Button("Filled", Button.Style.PRIMARY, Button.Type.FILLED, () -> System.out.println("Filled")));
    add(new Button("Outline", Button.Style.PRIMARY, Button.Type.OUTLINE, () -> System.out.println("Filled")));
    add(new Button("Plain", Button.Style.PRIMARY, Button.Type.PLAIN, () -> System.out.println("Filled")));
  }
}
