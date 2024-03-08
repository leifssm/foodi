package no.ntnu.idatt1002.view.components.sidebar;

import no.ntnu.idatt1002.view.components.button.StandardButton;
import no.ntnu.idatt1002.view.location.LocationHandler;
import no.ntnu.idatt1002.view.utils.CssUtils;
import org.jetbrains.annotations.NotNull;

class SideBarSubItem extends StandardButton implements CssUtils {
  public SideBarSubItem(@NotNull String text, @NotNull String location) {
    super(text, LocationHandler.createSetter(location));
    addClass("sidebar-button");
  }
}
