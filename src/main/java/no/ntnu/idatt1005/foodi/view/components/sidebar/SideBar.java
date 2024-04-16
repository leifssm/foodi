package no.ntnu.idatt1005.foodi.view.components.sidebar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;
import no.ntnu.idatt1005.foodi.view.utils.LoadUtils;

/**
 * A simple sidebar component.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class SideBar extends VBox implements CssUtils {


  /**
   * The constructor of the sidebar component.
   */
  public SideBar(SimpleObjectProperty<User> currentUserProperty) {
    super();
    addStylesheet("components/sidebar");
    addClass("sidebar");

    render(currentUserProperty.get().name());

    attachUsernameListener(currentUserProperty);
  }

  /**
   * Method for rendering the sidebar.
   *
   * @param currentUsername The username of the currently logged in user
   */
  public void render(String currentUsername) {
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
            "inventory",
            new SideBarSubItem("Add item", "inventory/add"),
            new SideBarSubItem("Add item", "inventory/add")
        ),
        new SideBarItem(
            "Cookbook",
            "cookbook-grid",
            new SideBarSubItem("Add item", "cookbook/add")
        ),
        new SideBarItem(
            "Shopping List",
            "shopping-list",
            new SideBarSubItem("Add item", "shopping-list/add")
        ),
        new SideBarItem(
            "About",
            "about"
        )
    );
  }

  private void attachUsernameListener(SimpleObjectProperty<User> currentUserProperty) {
    currentUserProperty.subscribe(newUser -> {
      getChildren().clear();
      render(newUser.name());
    });
  }
}
