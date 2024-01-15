package no.ntnu.idatt1002.demo.view.components.carousel;

import javax.swing.*;

class CarouselItem {
  private final int index;
  private final JPanel panel;

  public CarouselItem(JPanel panel, int index) {
    this.panel = panel;
    this.index = index;
  }

  public JPanel getPanel() {
    return panel;
  }

  public int getIndex() {
    return index;
  }
}
