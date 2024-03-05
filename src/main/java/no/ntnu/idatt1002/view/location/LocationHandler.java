package no.ntnu.idatt1002.view.location;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocationHandler {
  private static final SimpleStringProperty location = new SimpleStringProperty("inventory/test/hello");

  public static String getLocation() {
    return LocationHandler.location.get();
  }

  public static void setLocation(@NotNull String location) {
    System.out.println("Setting location to " + location);
    LocationHandler.location.set(location);
  }

  public static Runnable createSetter(@NotNull String location) {
    return () -> LocationHandler.setLocation(location);
  }

  public interface LocationListener {
    void locationChanged(@NotNull String location);
  }
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

  public static void addendLocation(@NotNull String location) {
    LocationHandler.setLocation(LocationHandler.getLocation() + '/' + location);
  }

  public static boolean popLocation() {
    Pattern pattern = Pattern.compile("(.*)/\\w+");
    Matcher matcher = pattern.matcher(LocationHandler.getLocation());
    if (matcher.matches()) {
      LocationHandler.setLocation(matcher.group(1));
    }
    return matcher.matches();
  }

  public static boolean isLocationExact(@NotNull String location) {
    return LocationHandler.getLocation().equals(location);
  }

  public static boolean isLocationFuzzy(@NotNull String location) {
    return LocationHandler.getLocation().startsWith(location);
  }
}
