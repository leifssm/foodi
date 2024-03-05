package no.ntnu.idatt1002.view.components.Sidebar;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.Utils;

public class SideBar extends VBox {
  public SideBar() {
    super();
    getStylesheets().add(Utils.getStylesheet("sidebar"));
    getStyleClass().add("sidebar");

    String image = Utils.getImage("icon.png");
    if (image != null) {
      ImageView logo = new ImageView(image);
      logo.setFitWidth(138);
      logo.setFitHeight(50);
      getChildren().add(logo);
    }

    getChildren().addAll(
      new SideBarItem(
          "Inventory",
          "inventory",
          new SideBarSubItem("Add item", "inventory/add"),
          new SideBarSubItem("Add item", "inventory/add")
      ),
      new SideBarItem(
          "Cookbook",
          "cookbook",
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
