package no.ntnu.idatt1002.view.views;

import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.components.CenteredPage;
import no.ntnu.idatt1002.view.components.profiles.ProfileItems;
import no.ntnu.idatt1002.view.utils.CssUtils;

import java.util.List;

/**
 * Class for displaying the inventory page.
 *
 * @author Leif Mørstad
 * @version 1.2
 */
public class Profiles extends CenteredPage implements CssUtils {
    /**
     * Constructor for the Profiles class.
     */
    public Profiles() {
        super();
        addStylesheet("components/profiles/profiles");
        addClass("profiles");

        List<String> profileNames = List.of("Leif", "Henrik", "Kevin", "Markus");
        ProfileItems profileItems = new ProfileItems(profileNames);
        VBox content = new VBox(profileItems);

        setContent(content);
    }
}