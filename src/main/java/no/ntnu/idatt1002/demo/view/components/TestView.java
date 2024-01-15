package no.ntnu.idatt1002.demo.view.components;

import no.ntnu.idatt1002.demo.view.View;
import java.awt.*;

public class TestView extends View {
  public TestView() {
    super("Test");
    add(new Footer(), BorderLayout.SOUTH);
    add(new Sidebar(), BorderLayout.WEST);
  }
}
