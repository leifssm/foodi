package no.ntnu.idatt1002.view.components.button;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 * A component used to display a main button and a secondary button, where the main button is given
 * priority when expanding the element.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
public class DoubleButton extends BorderPane {
  /**
   * Constructor for the DoubleButton class.
   *
   * @param mainButton The main button which is given expanding priority when the DoubleButton is
   *                   stretched.
   * @param secondaryButton The secondary button
   */
  public DoubleButton(Button mainButton, Node secondaryButton) {
    super();
    Insets padding = new Insets(0, 10, 0, 0);
    BorderPane.setMargin(mainButton, padding);

    setCenter(mainButton);
    mainButton.setMaxWidth(Double.POSITIVE_INFINITY);
    setRight(secondaryButton);
  }
}
