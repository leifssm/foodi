package no.ntnu.idatt1002.view.components.Sidebar;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.location.LocationHandler;

class SideBarItem extends BorderPane {
  private boolean expanded = false;

  public SideBarItem(String text, String location) {
    super();
    Button mainButton = new Button(text);
    mainButton.setOnAction(event -> LocationHandler.setLocation(location));
    mainButton.getStyleClass().add("sidebar-button");
    setCenter(mainButton);
  }

  public SideBarItem(String text, String location, SideBarSubItem... subItems) {
    this(text, location);

    Button dropdownButton = new Button("▼");
    dropdownButton.setOnAction(event -> toggleExpanded());
    dropdownButton.getStyleClass().addAll("sidebar-button", "sidebar-dropdown-button");
    setRight(dropdownButton);

    VBox subItemsContainer = new VBox();
    subItemsContainer.getStyleClass().add("sidebar-dropdown");
    subItemsContainer.getChildren().addAll(subItems);
    setBottom(subItemsContainer);


    setExpanded(expanded);
  }

  public void setExpanded(boolean state) {
    expanded = state;
    Node content = getBottom();
    content.setVisible(expanded);
    content.setManaged(expanded);
  }

  public boolean getExpanded() {
    return expanded;
  }

  public void toggleExpanded() {
    setExpanded(!expanded);
  }
}
