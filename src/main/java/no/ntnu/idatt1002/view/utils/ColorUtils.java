package no.ntnu.idatt1002.view.utils;

import java.util.Random;

/**
 * Class for generating random colors.
 *
 * @author Henrik Kvamme
 */
public class ColorUtils {

  private static final Random random = new java.util.Random();

  private ColorUtils() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Method for generating a random color.
   *
   * @return a random color as a string hex code
   */
  public static String getRandomColor() {
    int randomInt = random.nextInt(0xffffff + 1);
    return String.format("#%06x", randomInt);
  }
}
