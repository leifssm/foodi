package no.ntnu.idatt1005.foodi.view.components.sidebar;

import no.ntnu.idatt1005.foodi.view.components.button.StandardButton;
import no.ntnu.idatt1005.foodi.view.location.LocationHandler;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;
import org.jetbrains.annotations.NotNull;

class SideBarSubItem extends StandardButton implements CssUtils {
  public SideBarSubItem(@NotNull String text, @NotNull String location) {
    super(text, LocationHandler.createSetter(location));
    addClass("sidebar-button");
  }
}
