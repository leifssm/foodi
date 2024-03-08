package no.ntnu.idatt1002.view.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class TitledPage extends BorderPane implements CssUtils {
  public TitledPage(String title, String subtitle) {
    super();
    addStylesheet("components/titled-page");
    addClass("titled-page");

    VBox wrapper = new VBox();

    Label titleLabel = new Label(title);
    titleLabel.getStyleClass().add("title");
    wrapper.getChildren().add(titleLabel);

    if (subtitle != null) {
      Label subtitleLabel = new Label(subtitle);
      subtitleLabel.getStyleClass().add("subtitle");
      wrapper.getChildren().add(subtitleLabel);
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
