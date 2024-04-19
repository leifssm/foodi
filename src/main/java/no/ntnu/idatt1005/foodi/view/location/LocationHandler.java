package no.ntnu.idatt1005.foodi.view.location;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class that handles the navigation of the application, which other classes can either change or
 * subscribe to.
 *
 * @author Leif Mørstad
 * @version 1.0
 * @see Router
 */
public class LocationHandler {

  /**
   * The current location of the application.
   */
  private static final SimpleStringProperty location = new SimpleStringProperty(
      "shopping-list"
  );

  /**
   * A shorthand for creating a setter for changing location.
   *
   * @param location the new location to create a setter for
   * @return a runnable that changes the location
   */
  public static Runnable createSetter(@NotNull String location) {
    return () -> LocationHandler.setLocation(location);
  }

  /**
   * Subscribes to changes in the location.
   *
   * @param listener a listener which takes a string as an argument
   * @return a runnable that unsubscribes the listener
   */
  public static Runnable subscribe(@NotNull LocationListener listener) {
    ChangeListener<String> a = (observable, oldValue, newValue) -> {
      if (oldValue.equals(newValue)) {
        return;
      }
      listener.locationChanged(newValue);
    };
    LocationHandler.location.addListener(a);
    return () -> LocationHandler.location.removeListener(a);
  }

  /**
   * Adds a string to the end of the current location, separated by a forward slash.
   *
   * @param location the sub location to navigate to
   */
  public static void appendLocation(@NotNull String location) {
    LocationHandler.setLocation(LocationHandler.getLocation() + '/' + location);
  }

  /**
   * Get the current location of the application.
   *
   * @return the current location
   */
  public static String getLocation() {
    return LocationHandler.location.get();
  }

  /**
   * Sets the location of the application and notifies all listeners. The syntax of the location
   * follows the format of a standard URL, e.g. "inventory/items/bread"
   *
   * @param location the new location
   */
  public static void setLocation(@NotNull String location) {
    System.out.println("Setting location to " + location);
    LocationHandler.location.set(location);
  }

  /**
   * Pops the last part of the location, if it exists.
   *
   * @return true if the location was popped, false otherwise
   */
  public static boolean popLocation() {
    Pattern pattern = Pattern.compile("(.*)/\\w+");
    Matcher matcher = pattern.matcher(LocationHandler.getLocation());
    if (matcher.matches()) {
      LocationHandler.setLocation(matcher.group(1));
    }
    return matcher.matches();
  }

  /**
   * Checks if the current location is exactly the same as the given location.
   *
   * @param location the location to check against
   * @return true if the locations are the same, false otherwise
   */
  public static boolean isLocationExact(@NotNull String location) {
    return LocationHandler.getLocation().equals(location);
  }

  /**
   * Checks if the current location starts with the given location.
   *
   * @param location the location to check against
   * @return true if the current location starts with the given location, false otherwise
   */
  public static boolean isLocationFuzzy(@NotNull String location) {
    return LocationHandler.getLocation().startsWith(location);
  }

  /**
   * Checks if a segment exists at the given index.
   *
   * @param index the index to query
   * @return true if the segment exists, false otherwise
   */
  public static boolean locationSegmentExists(int index) {
    return getLocationSegmentLength() > index;
  }

  /**
   * Gets the length of the location segments.
   *
   * @return the length of the location segments
   */
  public static int getLocationSegmentLength() {
    return getLocation().split("/").length;
  }

  /**
   * Gets a segment of the location.
   *
   * @param index the index to query
   * @return the segment at the given index, or null if it does not exist
   */
  public static @Nullable String getLocationSegment(int index) {
    String[] segments = getLocation().split("/");
    if (index < 0 || index >= segments.length) {
      return null;
    }
    return segments[index];
  }

  /**
   * A listener for changes in the location.
   */
  public interface LocationListener {

    void locationChanged(@NotNull String location);
  }
}
