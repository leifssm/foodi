package no.ntnu.idatt1002.view.components.dialog;

import javafx.scene.control.Dialog;
import no.ntnu.idatt1002.view.utils.DialogCssUtils;

/**
 * Class for creating a dialog for adding a new user.
 *
 * @author Henrik Kvamme
 */
public class StandardDialog extends Dialog<Void> implements DialogCssUtils {

  /**
   * Constructor for the NewUserDialog class.
   */
  public StandardDialog() {
    super();
    addStylesheet("components/std-dialog");
    addClass("std-dialog");
  }
}
