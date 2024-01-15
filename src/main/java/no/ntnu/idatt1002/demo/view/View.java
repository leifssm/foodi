package no.ntnu.idatt1002.demo.view;

import java.awt.*;
import javax.swing.*;
import no.ntnu.idatt1002.demo.repo.MyEntityRepo;
import no.ntnu.idatt1002.demo.data.MyEntity;

/**
 * Main window for my application!
 *
 * @author nilstes
 */
public class View extends JFrame {
  public static final Color DEFAULT_BACKGROUND_COLOR = new Color(0xF88787);
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
    setSize(600, 600);
    getContentPane().setBackground(DEFAULT_BACKGROUND_COLOR);
    setIconImage(DEFAULT_ICON.getImage());

    //setLayout(new GridLayout(1, 1, 1, 1));
  }
}
