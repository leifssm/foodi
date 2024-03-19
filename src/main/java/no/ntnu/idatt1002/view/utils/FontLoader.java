package no.ntnu.idatt1002.view.utils;

import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;

/**
 * Utility class for loading fonts.
 * Utilises {@link LoadUtils} to get the path to the font directory.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public final class FontLoader {

    private static final int DEFAULT_FONT_SIZE = 12;

    private FontLoader() {
        throw new AssertionError("Utility class");
    }

    /**
     * Loads fonts from a directory.
     *
     * @param fontDirectoryName the name of the directory containing the fonts
     */
    public static void loadFontsFromDirectory(String fontDirectoryName) {
        URI directoryPath = LoadUtils.getFontDirectoryURI(fontDirectoryName);
        File[] fontFiles = getFiles(directoryPath);

        for (File fontFile : fontFiles) {
            try {
                loadFont(fontFile);
            } catch (Exception e) {
                System.out.println("Error loading font: " + e.getMessage());
            }
        }
    }

    /**
     * Loads a font from a file.
     *
     * @param fontFile the file to load the font from
     */
    private static void loadFont(File fontFile) {
        if (fontFile == null) {
            throw new IllegalArgumentException("Font file is null");
        }

        if (!fontFile.isFile()) {
            throw new IllegalArgumentException("Font file is not a file: " + fontFile.getName());
        }

        if (!fontFile.getName().endsWith(".ttf") && !fontFile.getName().endsWith(".otf")) {
            throw new IllegalArgumentException("Font file is neither a .ttf or .otf file: " + fontFile.getName());
        }

        Font.loadFont(fontFile.toURI().toString(), DEFAULT_FONT_SIZE);
    }

    /**
     * Gets the files in a directory.
     *
     * @param fontDirectoryName the name of the directory containing the fonts
     * @param directoryURI      the URI of the directory containing the fonts
     * @return the files in the directory
     */
    @NotNull
    private static File[] getFiles(URI directoryURI) {
        String fontDirectoryName = directoryURI.getPath();

        File dir = new File(directoryURI);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("Font directory is not a directory: " + fontDirectoryName);
        }

        File[] fontFiles = dir.listFiles();

        if (fontFiles == null) {
            throw new IllegalArgumentException("Font directory is empty: " + fontDirectoryName);
        }
        return fontFiles;
    }
}
