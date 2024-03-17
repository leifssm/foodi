package no.ntnu.idatt1002.view.components.button;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class DoubleButton extends BorderPane {
  public DoubleButton(Button mainButton, Node secondaryButton) {
    super();
    Insets padding = new Insets(0, 10, 0, 0);
    BorderPane.setMargin(mainButton, padding);

    setCenter(mainButton);
    mainButton.setMaxWidth(Double.POSITIVE_INFINITY);
    setRight(secondaryButton);
  }
}
