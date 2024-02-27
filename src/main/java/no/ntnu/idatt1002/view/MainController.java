package no.ntnu.idatt1002.view;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import org.jetbrains.annotations.NotNull;

public class MainController {

  @FXML
  private BorderPane mainWrapper;

  @FXML
  public void gotoInventory() {
    loadView("inventory");
  }

  @FXML
  public void gotoCookbook() {
    loadView("cookbook");
  }

  private void loadView(@NotNull String url) {
    mainWrapper.setCenter(ViewUtils.load(url));
  }
}
