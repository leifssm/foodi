package no.ntnu.idatt1002.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Objects;

public class ViewUtils {
  public static @Nullable Parent load(@NotNull String path) {
    try {
      return FXMLLoader.load(
        Objects.requireNonNull(
          ViewUtils.class.getResource("views/" + path + ".fxml")
        )
      );
    } catch (NullPointerException | IOException e) {
      System.out.println("Error loading view: " + e.getMessage());
      return null;
    }
  }
}
