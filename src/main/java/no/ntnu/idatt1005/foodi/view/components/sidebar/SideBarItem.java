package no.ntnu.idatt1005.foodi.view.components.sidebar;

import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import org.jetbrains.annotations.NotNull;

class SideBarItem extends StandardButton {

  public SideBarItem(@NotNull String text, @NotNull String location) {
    super(text, LocationHandler.createSetter(location));
    addClass("sidebar-button");
    if (LocationHandler.isLocationFuzzy(location)) {
      addClass("selected");
    }
  }
}
