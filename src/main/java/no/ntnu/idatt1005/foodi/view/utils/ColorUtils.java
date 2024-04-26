package no.ntnu.idatt1005.foodi.view.utils;

import java.awt.Color;

/**
 * Utility class for generating colors based on usernames.
 *
 * @author Henrik Kvamme
 */
public class ColorUtils {

  private static final float BRIGHTNESS = 0.9f;
  private static final float SATURATION = 0.8f;

  private ColorUtils() {
    throw new IllegalStateException("Utility class");
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
