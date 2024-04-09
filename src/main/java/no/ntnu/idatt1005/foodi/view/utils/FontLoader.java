package no.ntnu.idatt1005.foodi.view.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.logging.Logger;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for loading fonts. Utilises {@link LoadUtils} to get the path to the font
 * directory.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public final class FontLoader {

  private static final Logger LOGGER = Logger.getLogger(FontLoader.class.getName());

  private static final int DEFAULT_FONT_SIZE = 12;

  private FontLoader() {
    throw new AssertionError("Utility class");
  }

  /**
   * Loads fonts from a directory.
   *
   * @param fontDirectoryName the name of the directory containing the fonts
   */
  public static void loadFontsFromDirectory(@NotNull String fontDirectoryName) {
    URI directoryPath = LoadUtils.getFontDirectoryUri(fontDirectoryName);
    File[] fontFiles = getFiles(directoryPath);

    for (File fontFile : fontFiles) {
      try {
        loadFont(fontFile);
      } catch (Exception e) {
        LOGGER.severe("Error loading font: " + e.getMessage());
      }
    }
  }

  /**
   * Gets the files in a directory.
   *
   * @param directoryUri the URI of the directory containing the fonts
   * @return the files in the directory
   * @throws IllegalArgumentException if the directory is not a directory or is empty
   */
  @NotNull
  private static File[] getFiles(URI directoryUri) {
    String fontDirectoryName = directoryUri.getPath();

    File dir = new File(directoryUri);
    if (!dir.isDirectory()) {
      throw new IllegalArgumentException("Font directory is not a directory: " + fontDirectoryName);
    }

    File[] fontFiles = dir.listFiles();

    if (fontFiles == null) {
      throw new IllegalArgumentException("Font directory is empty: " + fontDirectoryName);
    }
    return fontFiles;
  }

  /**
   * Loads a font from a file.
   *
   * @param fontFile the file to load the font from
   */
  private static void loadFont(@NotNull File fontFile) throws FileNotFoundException {
    if (!fontFile.isFile()) {
      throw new FileNotFoundException("Font file is not a file: " + fontFile.getName());
    }

    if (!fontFile.getName().endsWith(".ttf") && !fontFile.getName().endsWith(".otf")) {
      throw new FileNotFoundException(
          "Font file is neither a .ttf or .otf file: " + fontFile.getName());
    }

    Font.loadFont(fontFile.toURI().toString(), DEFAULT_FONT_SIZE);
  }


}
