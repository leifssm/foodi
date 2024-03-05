package no.ntnu.idatt1002.view.views;

import javafx.scene.control.Button;
import no.ntnu.idatt1002.view.components.TitledPage;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class Inventory extends TitledPage implements CssUtils {
  public Inventory() {
    super("Inventory");
    //addClass("inventory-page");
    //addStylesheet("inventory-page");

    setContent(new Button("Hello, Inventory!"));
  }
}