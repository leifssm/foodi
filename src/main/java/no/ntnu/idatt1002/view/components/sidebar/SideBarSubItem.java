package no.ntnu.idatt1002.view.components.sidebar;

import javafx.scene.control.Button;
import no.ntnu.idatt1002.view.location.LocationHandler;
import no.ntnu.idatt1002.view.utils.CssUtils;
import org.jetbrains.annotations.NotNull;

class SideBarSubItem extends Button implements CssUtils {
  public SideBarSubItem(@NotNull String text, @NotNull String location) {
    super(text);
    addClass("sidebar-button");
    setOnAction(event -> LocationHandler.setLocation(location));
  }
}
