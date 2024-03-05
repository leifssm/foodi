package no.ntnu.idatt1002;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.view.App;
import org.jetbrains.annotations.NotNull;

/**
 * Use this class to start the application
 *
 * @author nilstes
 */
public class Main extends Application {
  @Override
  public void start(@NotNull Stage stage) {
    App root = new App();
    Scene scene = new Scene(root, 800, 600);

    stage.setTitle("Foodi");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Main method for my application
   */
  public static void main(String[] args) {
    launch();
  }
}
