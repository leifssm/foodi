package no.ntnu.idatt1002.demo.view.views;

import no.ntnu.idatt1002.demo.view.View;
import no.ntnu.idatt1002.demo.view.components.Carousel;
import no.ntnu.idatt1002.demo.view.components.Element;
import no.ntnu.idatt1002.demo.view.components.Footer;
import no.ntnu.idatt1002.demo.view.components.Sidebar;

import javax.swing.*;
import java.awt.*;

public class TestView extends View {
  public TestView() {
    super("Test");
    add(new Footer(), BorderLayout.SOUTH);
    JPanel[] panels = new JPanel[15];
    for (int i = 0; i < panels.length; i++) {
      panels[i] = new Element("Element " + (i + 1));
    }
    Carousel carousel = new Carousel(panels);
    add(new Sidebar(), BorderLayout.WEST);

    //var b = new Button("Hello", () -> System.out.println("Hello"));
    ////a.addLayoutComponent(b, BorderLayout.WEST);
    //b.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
    add(carousel, BorderLayout.CENTER);
  }
}
