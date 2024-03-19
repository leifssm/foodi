package no.ntnu.idatt1002.view.utils;

/**
 * @author Henrik Kvamme
 * <p>
 * Class for generating random colors
 */
public class ColorUtils {
  private static final java.util.Random random = new java.util.Random();

  private ColorUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Method for generating a random color
   *
   * @return a random color as a string hex code
   */
  public static String getRandomColor() {
    int randomInt = random.nextInt(0xffffff + 1);
    return String.format("#%06x", randomInt);
  }
}
