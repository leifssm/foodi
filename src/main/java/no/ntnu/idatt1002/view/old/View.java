package no.ntnu.idatt1002.view.old;

import java.awt.*;
import javax.swing.*;

/**
 * @author Leif Mørstad
 */
public class View extends JFrame {
  public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0x1b1b1b);
  public static final ImageIcon DEFAULT_ICON = new ImageIcon("src/main/resources/icon.png");

  /**
   * Constructor for window
   *
   * @param title Title of the window
   * @see Image
   */
  public View(String title) {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000, 600);
    getContentPane().setBackground(DEFAULT_BACKGROUND_COLOR);
    setIconImage(DEFAULT_ICON.getImage());

    //setLayout(new GridLayout(1, 1, 1, 1));
  }
}
