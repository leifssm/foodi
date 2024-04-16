package no.ntnu.idatt1005.foodi.view.views;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import no.ntnu.idatt1005.foodi.view.components.TitledPage;
import no.ntnu.idatt1005.foodi.view.utils.CssUtils;

public class About extends TitledPage implements CssUtils {
  public About() {
    super("Your vision, our mission");
    addStylesheet("components/about/about");
    addClass("about");

    Label aboutText = new Label("As a dynamic and innovative team, we excel due to our unique blend of skills and perspectives. Our diversity in expertise, from computer science to design, allows us to approach problems from multiple angles, ensuring comprehensive solutions. Our proficiency in Java, coupled with design tools like Figma, enhances our technical versatility. Our eagerness to embrace new technologies, like Rust and Java UI libraries, keeps us at the forefront of cutting-edge developments. Our collaboration is not just about skill; it's about a shared passion for excellence and continuous learning, making us an unstoppable force in any project. 🍽");
    aboutText.getStyleClass().add("about-text");
    BorderPane content = new BorderPane();
    content.setTop(aboutText);

    setCenter(content);
  }
}
