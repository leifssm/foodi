package no.ntnu.idatt1002.view.components.profiles;

import javafx.scene.layout.HBox;
import no.ntnu.idatt1002.view.utils.CssUtils;

import java.util.List;
import java.util.Random;

public class ProfileItems extends HBox implements CssUtils {
    // TODO: When integrating with the backend, the constructor should take a list of profile items
    //    public ProfileItems(ArrayList<Profile> profileItems) {
    //        super();
    // Assign color from Profile object
    //    }

    public ProfileItems(List<String> profileNames) {
        super();
        addStylesheet("components/profiles/profile-items");
        addClass("profile-items");

        setSpacing(24);

        Random random = new Random();
        for (String name : profileNames) {
            // Generate random color
            int randomInt = random.nextInt(0xffffff + 1);
            String randomColor = String.format("#%06x", randomInt);

            // Init PofileItem
            ProfileItem profileItem = new ProfileItem(name, randomColor);
            getChildren().add(profileItem);
        }
    }
}
