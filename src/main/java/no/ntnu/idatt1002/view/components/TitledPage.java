package no.ntnu.idatt1002.view.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class TitledPage extends BorderPane implements CssUtils {
  public TitledPage(String title, String subtitle) {
    super();
    addClass("titled-page");
    addStylesheet("titled-page");

    VBox wrapper = new VBox();
    wrapper.getChildren().add(new Label(title));
    if (subtitle != null) {
      wrapper.getChildren().add(new Text(subtitle));
    }
    setTop(wrapper);
  }

  public TitledPage(String title) {
    this(title, null);
  }

  public void setContent(Node content) {
    setCenter(content);
  }
}
