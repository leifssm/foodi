package no.ntnu.idatt1002.view.components;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1002.view.utils.CssUtils;

public class CenteredPage extends BorderPane implements CssUtils {
    /**
     * Constructor for the CenteredPage class.
     */
    public CenteredPage() {
        super();
        addStylesheet("components/centered-page");
        addClass("centered-page");
    }

    public void setContent(Node content) {
        setCenter(content);
    }
}
