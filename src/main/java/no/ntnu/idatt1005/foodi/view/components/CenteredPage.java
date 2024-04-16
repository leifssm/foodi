package no.ntnu.idatt1005.foodi.view.components;

import javafx.scene.Node;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;

/**
 * Extension of BorderPane that centers the content of the page.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class CenteredPage extends StatefulPage implements CssUtils {

  /**
   * Constructor for the CenteredPage class.
   */
  public CenteredPage() {
    super();
    addStylesheet("components/centered-page");
    addClass("centered-page");
  }

  /**
   * Sets the content of the page. The content will be centered.
   *
   * @param content The content to set.
   */
  public void setContent(Node content) {
    setCenter(content);
  }
}
