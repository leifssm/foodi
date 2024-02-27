package no.ntnu.idatt1002.view.old.components;

import javax.swing.*;
import java.awt.*;

public class Element extends JPanel {
  private static final int SIZE = 100;
  public Element(String name) {
    super();
    setBackground(new Color(0x1c1c1c));
    add(new JLabel(name));
    add(new JLabel(new ImageIcon("src/main/resources/icon.png")));
    setPreferredSize(new Dimension(SIZE, SIZE));
  }
}
