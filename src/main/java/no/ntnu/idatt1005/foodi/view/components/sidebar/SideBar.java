package no.ntnu.idatt1005.foodi.view.components.sidebar;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import no.ntnu.idatt1005.foodi.view.utils.ColorUtils;
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

    render(currentUserProperty.get());

    currentUserProperty.subscribe(this::render);
    LocationHandler.subscribe(l -> render(currentUserProperty.get()));
  }

  /**
   * Method for rendering the sidebar.
   *
   * @param currentUser the current user
   */
  public void render(User currentUser) {
    String currentUsername = currentUser.getCapitalizedName();
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
        getProfilesButton(currentUsername),
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

  private HBox getProfilesButton(String currentUsername) {
    SideBarItem navButton = new SideBarItem(
        currentUsername,
        "profiles"
    );

    Rectangle profileImage = new Rectangle(12, 12);
    profileImage.setStyle("-fx-fill: " + ColorUtils.usernameToColor(currentUsername) + ";");

    HBox container = new HBox(profileImage, navButton);
    container.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
    container.setPadding(new Insets(0, 0, 0, 8));

    return container;
  }
}
