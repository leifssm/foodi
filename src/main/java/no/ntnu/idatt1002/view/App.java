package no.ntnu.idatt1002.view;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1002.view.components.Sidebar.SideBar;
import no.ntnu.idatt1002.view.location.Router;

public class App extends BorderPane {
  public App() {
    super();
    getStylesheets().add(Utils.getStylesheet("root"));
    getStyleClass().add("main");
    setLeft(new SideBar());

    Router router = new Router();
    router.addRoute("inventory", new Button("hello"));

    setCenter(router);
  }
}
