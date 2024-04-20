package no.ntnu.idatt1005.foodi.view.components.profiles;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.scene.layout.HBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;

/**
 * Class for creating a horizontal list of {@link ProfileItem}. Extends {@link HBox} and implements
 * {@link ComponentUtils}.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class ProfileItems extends HBox implements ComponentUtils {

  /**
   * Constructor for the ProfileItems class.
   *
   * @param profileNames List of profile names
   */
  public ProfileItems(List<User> profileNames, Consumer<User> changeUser,
      Function<String, Boolean> addUser) {
    super();
    addStylesheet("components/profiles/profile-items");
    addClass("profile-items");

    for (User user : profileNames) {
      // Init ProfileItem
      final Runnable onClick = () -> changeUser.accept(user);
      ProfileItem profileItem = new ProfileItem(user.getCapitalizedName(), onClick);
      getChildren().add(profileItem);
    }

    NewUserDialog newUserDialog = new NewUserDialog(addUser);
    final Runnable onClick = newUserDialog::showAndWait;
    getChildren().add(new AddProfileButton(onClick));
  }
}
