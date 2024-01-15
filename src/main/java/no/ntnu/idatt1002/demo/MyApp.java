package no.ntnu.idatt1002.demo;

import no.ntnu.idatt1002.demo.view.views.TestView;
import no.ntnu.idatt1002.demo.view.View;

/**
 * Use this class to start the application
 *
 * @author nilstes
 */
public class MyApp {

  /**
   * Main method for my application
   */
  public static void main(String[] args) throws Exception {
    View window = new TestView();
    window.setVisible(true);
  }
}
