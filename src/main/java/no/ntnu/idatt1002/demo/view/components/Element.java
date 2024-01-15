package no.ntnu.idatt1002.demo.view.components;

import javax.swing.*;
import java.awt.*;

public class Element extends JPanel {
  private static final int SIZE = 100;
  public Element(String name) {
    super();
    setBackground(new Color(0, 0, 200, 100));
    add(new JLabel(name));
    setPreferredSize(new Dimension(SIZE, SIZE));
  }
}
