package no.ntnu.idatt1002.view.components.profiles;

import javafx.scene.layout.HBox;
import no.ntnu.idatt1002.view.utils.CssUtils;
import no.ntnu.idatt1002.view.utils.RandomColor;

import java.util.List;

/**
 * Class for creating a horizontal list of {@link ProfileItem}.
 * Extends {@link HBox} and implements {@link CssUtils}.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class ProfileItems extends HBox implements CssUtils {
    // TODO: When integrating with the backend, the constructor should take a list of profile items
//    public ProfileItems(ArrayList<Profile> profileItems) {
//        super();
////        Assign color from Profile object
//    }

    /**
     * Constructor for the ProfileItems class.
     *
     * @param profileNames List of profile names
     */
    public ProfileItems(List<String> profileNames) {
        super();
        addStylesheet("components/profiles/profile-items");
        addClass("profile-items");

        for (String name : profileNames) {
            // Init ProfileItem
            ProfileItem profileItem = new ProfileItem(name, RandomColor.getRandomColor());
            getChildren().add(profileItem);
        }
    }
}
