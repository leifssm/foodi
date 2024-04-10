package no.ntnu.idatt1005.foodi.view.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.DialogPane;

/**
 * Utils class that can be used with {@code implements} to add shorthands for easier css handling.
 * This interface is used for classes that extend {@link javafx.scene.control.Dialog}.
 *
 * @author Henrik Kvamme & Leif Mørstad
 * @version 1.0
 */
public interface DialogCssUtils extends CssUtils {

  @Override
  default ObservableList<String> getStylesheets() {
    return getDialogPane().getStylesheets();
  }

  @Override
  default ObservableList<String> getStyleClass() {
    return getDialogPane().getStyleClass();
  }

  DialogPane getDialogPane();
}
