package no.ntnu.idatt1002.view.components.sidebar;

import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.utils.CssUtils;
import no.ntnu.idatt1002.view.utils.LoadUtils;

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
  public SideBar() {
    super();
    addStylesheet("components/sidebar");
    addClass("sidebar");

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
            "Profiles",
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
}
