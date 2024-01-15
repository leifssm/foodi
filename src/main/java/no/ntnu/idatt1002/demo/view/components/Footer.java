package no.ntnu.idatt1002.demo.view.components;

import javax.swing.*;
import java.awt.*;

/**
 * A styled footer component
 *
 * @version 1.0
 * @since 2024-04-15
 * @see JPanel
 * @author Leif Mørstad
 */
public class Footer extends JPanel {
  /**
   * The height of the footer
   */
  private static final int HEIGHT = 100;
  public Footer() {
    super();
    setBackground(Color.GREEN);
    setPreferredSize(new Dimension(0, HEIGHT));
  }
}
