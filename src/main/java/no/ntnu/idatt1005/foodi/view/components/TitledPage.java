package no.ntnu.idatt1005.foodi.view.components;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Class for creating a standard titled page.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class TitledPage extends BorderPane implements CssUtils {

  /**
   * Constructor for the TitledPage class.
   *
   * @param title The title of the page
   */
  public TitledPage(@NotNull String title) {
    this(title, null);
  }

  /**
   * Constructor for the TitledPage class.
   *
   * @param title    The title of the page
   * @param subtitle The subtitle of the page
   */
  public TitledPage(@NotNull String title, String subtitle) {
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

  /**
   * Sets the content of the page.
   *
   * @param content The content to set
   */
  public void setContent(Node content) {
    setCenter(content);
  }
}
