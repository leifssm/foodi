package no.ntnu.idatt1002.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
  @Override
  public void start(@NotNull Stage stage) throws IOException {
    Parent application = FXMLLoader.load(
      Objects.requireNonNull(
        getClass().getResource("application.fxml")
      )
    );
    Scene scene = new Scene(application);

    stage.setTitle("Foodi");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
