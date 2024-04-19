package no.ntnu.idatt1005.foodi.view.components.sidebar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.User;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import no.ntnu.idatt1005.foodi.view.utils.ComponentUtils;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;

/**
 * A simple sidebar component.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class SideBar extends VBox implements ComponentUtils {

  /**
   * The constructor of the sidebar component.
   */
  public SideBar(SimpleObjectProperty<User> currentUserProperty) {
    super();
    addStylesheet("components/sidebar");
    addClass("sidebar");

    render(currentUserProperty.get().name());

    currentUserProperty.subscribe(newUser -> render(newUser.name()));
    LocationHandler.subscribe(l -> render(currentUserProperty.get().name()));
  }

  /**
   * Method for rendering the sidebar.
   *
   * @param currentUsername The username of the currently logged in user
   */
  public void render(String currentUsername) {
    getChildren().clear();

    String image = LoadUtils.getImage("foodi.png");
    if (image != null) {
      ImageView logo = new ImageView(image);
      logo.getStyleClass().add("sidebar-logo");
      logo.setPreserveRatio(true);
      logo.setFitWidth(100);

      // Set margin for the logo (impossible to do with CSS)
      VBox.setMargin(logo, new Insets(10, 0, 10, 0));

      getChildren().add(logo);
    }

    getChildren().addAll(
        new SideBarItem(
            currentUsername,
            "profiles"
        ),
        new SideBarItem(
            "Inventory",
            "inventory"
        ),
        new SideBarItem(
            "Cookbook",
            "cookbook-grid"
        ),
        new SideBarItem(
            "Shopping List",
            "shopping-list"
        ),
        new SideBarItem(
            "About us",
            "about"
        )
    );
  }
}
