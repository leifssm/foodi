package no.ntnu.idatt1005.foodi.view.utils;

import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

/**
 * Utils class that can be used with {@code implements} to add shorthands for easier css handling.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public interface CssUtils {

  /**
   * Adds a stylesheet dependency to the element.
   *
   * @param path the path from the resources/view/styles/*
   * @see LoadUtils#getStylesheet(String)
   */
  default void addStylesheet(@NotNull String path) {
    String url = LoadUtils.getStylesheet(path);
    if (url == null) {
      System.out.println("Could not load stylesheet " + path);
      return;
    }
    getStylesheets().add(url);
  }

  ObservableList<String> getStylesheets();

  /**
   * Adds a class name to the elements' css class list.
   *
   * @param className the classname to add
   */
  default void addClass(@NotNull String className) {
    getStyleClass().add(className);
  }

  ObservableList<String> getStyleClass();

  /**
   * Adds a class name to the elements' css class list.
   *
   * @param className the classname to add
   */
  default void removeClass(@NotNull String className) {
    getStyleClass().remove(className);
  }

  /**
   * Adds all class names to the elements' css class list.
   *
   * @param className the classnames to add
   */
  default void addClasses(@NotNull String... className) {
    getStyleClass().addAll(className);
  }
}
