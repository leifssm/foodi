package no.ntnu.idatt1005.foodi.view.utils;

import java.awt.Color;
import java.util.Random;

/**
 * Class for generating random colors.
 *
 * @author Henrik Kvamme
 */
public class ColorUtils {

  private static final Random random = new java.util.Random();
  private static final float BRIGHTNESS = 0.9f;
  private static final float SATURATION = 0.8f;

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

  /**
   * Method for generating a color based on a username.
   *
   * @param username the username to generate a color from
   * @return a color as a string hex code
   */
  public static String usernameToColor(String username) {
    int hash = username.hashCode();
    float hue = (Math.abs(hash) % 360) / 360.0f;

    Color color = Color.getHSBColor(hue, SATURATION, BRIGHTNESS);
    String hex = String.format("%06X", (color.getRGB() & 0xFFFFFF));
    return "#" + hex;
  }
}
