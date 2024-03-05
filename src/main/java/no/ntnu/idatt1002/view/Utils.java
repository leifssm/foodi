package no.ntnu.idatt1002.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Utils {
  private static @Nullable URL getResource(@NotNull String path, @NotNull String errorMessage) {
    try {
      return Objects.requireNonNull(
          Utils.class.getResource(path),
          "Could not find resource: " + path
      );
    } catch (NullPointerException e) {
      System.out.println(errorMessage + ": " + e.getMessage());
      return null;
    }
  }

  public static @Nullable String getImage(@NotNull String path) {
    URL resource = getResource(
        "images/" + path,
        "Error loading image"
    );

    if (resource == null) {
      return null;
    }

    return resource.toExternalForm();
  }
  public static @Nullable String getStylesheet(@NotNull String path) {
    URL resource = getResource(
        "styles/" + path + ".css",
        "Error loading stylesheet"
    );

    if (resource == null) {
      return null;
    }

    return resource.toExternalForm();
  }

  public static @Nullable Parent loadFXML(@NotNull String path) {
    URL resource = getResource(
        "views/" + path + ".fxml",
        "Error finding fxml file"
    );
    if (resource == null) {
      return null;
    }
    try {
      return FXMLLoader.load(resource);
    } catch (NullPointerException | IOException e) {
      System.out.println("Error loading view: " + e.getMessage());
      return null;
    }
  }

}
