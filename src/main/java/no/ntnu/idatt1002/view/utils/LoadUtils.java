package no.ntnu.idatt1002.view.utils;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for loading resources and views.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public class LoadUtils {
  /**
   * Gets a valid a resource url from the resources folder, or returns null and prints an error
   * message if the resource is not found.
   *
   * @param path the path to the resource
   * @param errorMessage the error message to print if the resource is not found
   * @return the resource url, or null if the resource is not found
   */
  private static @Nullable URL getResource(@NotNull String path, @NotNull String errorMessage) {
    try {
      return Objects.requireNonNull(
          LoadUtils.class.getResource("../" + path),
          "Could not find resource: " + path
      );
    } catch (NullPointerException e) {
      System.out.println(errorMessage + ": " + e.getMessage());
      return null;
    }
  }

  /**
   * Gets the url of an image from the resources folder, or returns null and prints an error message
   * if the image is not found.
   *
   * @param path the path to the image from view/images/*, must include the file extension
   * @return the string url to the image, or null if the image is not found
   */
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

  /**
   * Gets the url of a stylesheet from the resources folder, or returns null and prints an error
   * message if the stylesheet is not found.
   *
   * @param path the path to the stylesheet from view/styles/* without the .css file extension
   * @return the string url to the stylesheet, or null if the stylesheet is not found
   */
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

  /**
   * Loads a fxml file from the resources folder, or returns null and prints an error message if the
   * fxml file is not found.
   *
   * @param path the path to the fxml file from view/views/* without the .fxml file extension
   * @return the parent node of the fxml file, or null if the fxml file is not found
   */
  public static @Nullable Parent loadFxml(@NotNull String path) {
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
