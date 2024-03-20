package no.ntnu.idatt1002.view.utils;

import javafx.collections.ObservableList;
import org.jetbrains.annotations.NotNull;

/**
 * Utils class that can be used with {@code implements} to add shorthands for easier css handling.
 *
 * @author Leif Mørstad
 * @version 1.1
 */
public interface ComponentUtils {
  ObservableList<String> getStylesheets();
  ObservableList<String> getStyleClass();

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

  /**
   * Adds a class name to the elements' css class list.
   *
   * @param className the classname to add
   */
  default void addClass(@NotNull String className) {
    getStyleClass().add(className);
  }

  /**
   * Adds all class names to the elements' css class list.
   *
   * @param className the classnames to add
   */
  default void addClasses(@NotNull String... className) {
    getStyleClass().addAll(className);
  }

  /**
   * Removes a class name to the elements' css class list.
   *
   * @param className the classname to remove
   */
  default void removeClass(@NotNull String className) {
    getStyleClass().remove(className);
  }

  /**
   * Removes class names to the elements' css class list.
   *
   * @param classNames the classnames to remove
   */
  default void removeClasses(@NotNull String... classNames) {
    getStyleClass().removeAll(classNames);
  }

  /**
   * Returns the elements' css class list.
   *
   * @return the elements' css class list
   */
  default ObservableList<String> getClasses() {
    return getStyleClass();
  }

  /**
   * Adds a class name to the elements' css class list if it is not present, otherwise it removes
   * it.
   *
   * @param className the classname to toggle
   * @return whether the class exists after the operation
   */
  default boolean toggleClass(String className) {
    if (getClasses().contains(className)) {
      removeClass(className);
      return false;
    } else {
      addClass(className);
      return true;
    }
  }

  /**
   * Adds a class name to the elements' css class list if the condition is true, otherwise it
   * removes it.
   *
   * @param className the classname to toggle
   * @param condition whether to add or remove the class
   * @return the condition
   */
  default boolean toggleClass(String className, boolean condition) {
    if (condition) {
      addClass(className);
      return true;
    } else {
      removeClass(className);
      return false;
    }
  }
}
