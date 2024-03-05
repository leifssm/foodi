package no.ntnu.idatt1002.view.components.sidebar;

import javafx.scene.control.Button;
import no.ntnu.idatt1002.view.location.LocationHandler;
import org.jetbrains.annotations.NotNull;

class SideBarSubItem extends Button {
  public SideBarSubItem(@NotNull String text, @NotNull String location) {
    super(text);
    getStyleClass().addAll("sidebar-button");
    setOnAction(event -> LocationHandler.setLocation(location));
  }
}
