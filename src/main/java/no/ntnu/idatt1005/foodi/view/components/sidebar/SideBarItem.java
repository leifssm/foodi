package no.ntnu.idatt1005.foodi.view.components.sidebar;

import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import org.jetbrains.annotations.NotNull;

class SideBarItem extends BorderPane {

  public SideBarItem(@NotNull String text, @NotNull String location) {
    super();
    StandardButton button = new StandardButton(text, LocationHandler.createSetter(location));
    button.addClass("sidebar-button");
    setCenter(button);
  }
}
