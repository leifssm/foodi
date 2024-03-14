package no.ntnu.idatt1002.view.utils;

import javafx.scene.text.Font;

import java.io.File;
import java.net.MalformedURLException;

public class FontLoader {

    public static void loadFontsFromDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        if (dir.isDirectory()) {
            File[] fontFiles = dir.listFiles();
            System.out.println("Loading fonts from directory: " + directoryPath);
            if (fontFiles != null) {
                for (File fontFile : fontFiles) {
                    System.out.println("Loading font: " + fontFile.getName());
                    try {
                        String url = fontFile.toURI().toURL().toExternalForm();
                        Font.loadFont(url, 10); // Load font with a default size
                        System.out.println("Loaded font: " + url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
