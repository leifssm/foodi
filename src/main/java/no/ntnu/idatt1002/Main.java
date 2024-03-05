package no.ntnu.idatt1002;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.idatt1002.view.Root;
import org.jetbrains.annotations.NotNull;

/**
 * Use this class to start the application.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class Main extends Application {
  @Override
  public void start(@NotNull Stage stage) {
    Root root = new Root();
    Scene scene = new Scene(root, 800, 600);

    stage.setTitle("Foodi");
    stage.setScene(scene);
    stage.show();
  }

  /**
   * Main method for the application. Launches the application
   */
  public static void main(String[] args) {
    launch();
  }
}
