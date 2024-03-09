package no.ntnu.idatt1002.view.components.sidebar;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import no.ntnu.idatt1002.view.components.button.DropdownButton;
import no.ntnu.idatt1002.view.components.button.StandardButton;
import no.ntnu.idatt1002.view.location.LocationHandler;
import org.jetbrains.annotations.NotNull;

class SideBarItem extends BorderPane {
  public SideBarItem(@NotNull String text, @NotNull String location) {
    super();
    StandardButton button = new StandardButton(text, LocationHandler.createSetter(location));
    button.addClass("sidebar-button");
    setCenter(button);
  }

  public SideBarItem(
      @NotNull String text,
      @NotNull String location,
      @NotNull StandardButton @NotNull ... subItems
  ) {
    this(text, location);

    VBox subItemsContainer = new VBox();
    subItemsContainer.getStyleClass().add("sidebar-dropdown");
    subItemsContainer.getChildren().addAll(subItems);
    setBottom(subItemsContainer);

    DropdownButton dropdownButton = new DropdownButton((expanded) -> {
      subItemsContainer.setVisible(expanded);
      subItemsContainer.setManaged(expanded);
    });
    dropdownButton.addClass("sidebar-dropdown-button");
    setRight(dropdownButton);
  }
}
