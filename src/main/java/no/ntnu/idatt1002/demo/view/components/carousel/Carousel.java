package no.ntnu.idatt1002.demo.view.components.carousel;

import static no.ntnu.idatt1002.demo.view.components.ComponentUtils.PADDING;

import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;
import no.ntnu.idatt1002.demo.view.components.Button;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Class for creating a carousel.
 *
 * @since 2024-01-12
 * @author Leif Mørstad
 * @version 1.0
 */
public class Carousel extends JLayeredPane {
  /**
   * Width of the panel in pixels.
   */
  private final int PANEL_WIDTH = 300;
  /**
   * Number of panels visible on each side of the current panel.
   */
  private final int VISIBLE_NEIGHBORS = 2;
  /**
   * Index of the current panel.
   */
  private int currentIndex = 0;
  /**
   * Array of panels.
   */
  private final CarouselItem[] panels;
  /**
   * Button for turning the carousel to the left.
   */
  private final Button leftButton = new Button("<", this::turnLeft);
  /**
   * Button for turning the carousel to the right.
   */
  private final Button rightButton = new Button(">", this::turnRight);
  /**
   * Creates a carousel with the given panels.
   *
   * @param panels Array of panels to cycle between
   * @throws IllegalArgumentException If given null
   */

  public Carousel(@NotNull JPanel @NotNull [] panels) throws IllegalArgumentException {
    super();

    this.panels = new CarouselItem[panels.length];

    for (int i = 0; i < panels.length; i++) {
      JPanel panel = panels[i];
      this.panels[i] = new CarouselItem(panel, i);
      add(panel, JLayeredPane.DEFAULT_LAYER);
    }

    add(leftButton, JLayeredPane.PALETTE_LAYER);
    add(rightButton, JLayeredPane.PALETTE_LAYER);

    setBackground(Color.RED);
    setPreferredSize(new Dimension(50, 50));
    updatePanelPositions();
  }

  /**
   * Creates a carousel with no panels.
   */
  public Carousel() {
    this(new JPanel[0]);
  }

  @Override
  public void doLayout() {
    super.doLayout();
    // Injects the updatePanelPositions method into the update function
    updatePanelPositions();
  }

  /**
   * Turns the carousel to the left.
   */
  public void turnLeft() {
    if (currentIndex <= 0) {
      currentIndex = panels.length - 1;
    } else {
      currentIndex--;
    }
    updatePanelPositions();
  }

  /**
   * Turns the carousel to the right.
   */
  public void turnRight() {
    if (currentIndex >= panels.length - 1) {
      currentIndex = 0;
    } else {
      currentIndex++;
    }
    updatePanelPositions();
  }

  /**
   * Gets the panel at the given index, allowing out of bounds indices. Returns null if the carousel
   * has no panels.
   *
   * @param index Index of the panel to get
   * @return Panel at the given index, or null if the carousel has no panels
   */
  private @Nullable CarouselItem getPanel(int index) {
    if (panels.length == 0) {
      return null;
    }
    if (index < 0) {
      return getPanel(index + panels.length);
    }
    if (index >= panels.length) {
      return panels[index % panels.length];
    }
    return panels[index];
  }

  /**
   * Returns an array of the current panel, and the panels on each side of it equal to
   * {@link #VISIBLE_NEIGHBORS}.
   *
   * @return Array of panels
   */
  private CarouselItem[] getCurrentSlice() {
    if (panels.length - VISIBLE_NEIGHBORS * 2 <= 1) {
      return panels;
    }

    int startIndex = currentIndex - VISIBLE_NEIGHBORS;
    if (startIndex < 0) {
      startIndex += panels.length;
    }
    final int endIndex = startIndex + VISIBLE_NEIGHBORS * 2;

    final int sliceLength = VISIBLE_NEIGHBORS * 2 + 1;
    final CarouselItem[] slice = new CarouselItem[sliceLength];

    int sliceIndex = 0;
    for (int i = startIndex; i <= endIndex; i++) {
      slice[sliceIndex] = getPanel(i);
      sliceIndex++;
    }
    return slice;
  }

  /**
   * Updates the positions of the buttons.
   */
  private void updateButtonPositions() {
    final int buttonSize = 50;
    leftButton.setAlignmentY(Component.CENTER_ALIGNMENT);
    final int yOffset = getHeight() / 2 - buttonSize / 2;
    leftButton.setBounds(
        PADDING * 2,
        yOffset,
        buttonSize,
        buttonSize
    );
    rightButton.setBounds(
        getWidth() - buttonSize - PADDING * 2,
        yOffset,
        buttonSize,
        buttonSize
    );
  }

  /**
   * Updates the positions of the panels.
   */
  public void updatePanelPositions() {
    updateButtonPositions();
    if (panels.length == 0) {
      return;
    }


    //Rectangle from = new Rectangle(size.width, (size.height - 50) / 2, 50, 50);
    //Rectangle to = new Rectangle((size.width - 50) / 2, (size.height - 50) / 2, 50, 50);

    for (CarouselItem item : panels) {
      item.getPanel().setVisible(false);
    }

    final CarouselItem[] slice = getCurrentSlice();

    float sizeScalar;
    int displacement;

    float panelWidth;
    float panelHeight;
    float centerX;
    double posX;
    float posY;

    for (CarouselItem item : slice) {
      JPanel panel = item.getPanel();

      displacement = item.getIndex() - currentIndex;

      if (displacement > VISIBLE_NEIGHBORS) {
        displacement -= panels.length;
      } else if (displacement < -VISIBLE_NEIGHBORS) {
        displacement += panels.length;
      }

      sizeScalar = 1 - Math.abs(displacement) * 0.05f;

      panelWidth = PANEL_WIDTH * sizeScalar;
      panelHeight = (getHeight() - PADDING) * sizeScalar;

      centerX = getWidth() / 2f - panelWidth / 2f;
      posX = 200 * Math.tanh(0.6f * displacement) + centerX;
      posY = (getHeight() - panelHeight) / 2f;

      panel.setBounds((int) posX, (int) posY, (int) panelWidth, (int) panelHeight);
      setLayer(
          panel,
          JLayeredPane.DEFAULT_LAYER + panels.length - Math.abs(displacement)
      );
      panel.setVisible(true);
    }
  }
}
