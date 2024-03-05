package no.ntnu.idatt1002.view.utils;

import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

/**
 * Utils class that can be used with {@code implements} to add shorthands for easier css handling.
 *
 * @author Leif Mørstad
 * @version 1.0
 */
public interface CssUtils {
  ObservableList<String> getStylesheets();

  ObservableList<String> getStyleClass();

  /**
   * Adds a stylesheet dependency to the element.
   *
   * @param path the path from the resources/view/styles/*
   * @see LoadUtils#getStylesheet(String)
   */
  default void addStylesheet(@NotNull String path) {
    getStylesheets().add(LoadUtils.getStylesheet(path));
  }

  /**
   * Adds a class name to the elements css class list.
   *
   * @param className the classname to add
   */
  default void addClass(@NotNull String className) {
    getStyleClass().add(className);
  }

}
