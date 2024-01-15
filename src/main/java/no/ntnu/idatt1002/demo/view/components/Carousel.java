package no.ntnu.idatt1002.demo.view.components;

import static no.ntnu.idatt1002.demo.view.components.ComponentUtils.PADDING;

import java.awt.*;
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
  private final int VISIBLE_NEIGHBORS = 3;
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
  private final Button leftButton = new Button(
      "<",
      Button.Style.PRIMARY,
      Button.Type.FILLED,
      this::turnLeft
  );
  /**
   * Button for turning the carousel to the right.
   */
  private final Button rightButton = new Button(
      ">",
      Button.Style.PRIMARY,
      Button.Type.FILLED,
      this::turnRight
  );
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
    updatePanelPositions(false);
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
    updatePanelPositions(true);
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
    updatePanelPositions(true);
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

  private void animateItem(@NotNull CarouselItem item, @NotNull Rectangle to) {
    if (item.getTimer() != null) {
      item.getTimer().stop();
    }

    final JPanel panel = item.getPanel();
    final int FPS = 40;
    final int durationMs = 500;

    final int originalX = panel.getX();
    final int originalY = panel.getY();
    final int originalWidth = panel.getWidth();
    final int originalHeight = panel.getHeight();

    final float posXDiff = to.x - panel.getX();
    final float posYDiff = to.y - panel.getY();
    final float widthDiff = to.width - panel.getWidth();
    final float heightDiff = to.height - panel.getHeight();

    final long startMoment = System.currentTimeMillis();

    Timer timer = new Timer(1000 / FPS, action -> {
      final int timeElapsed = (int) (action.getWhen() - startMoment);

      if (timeElapsed >= durationMs) {
        Timer parentTimer = (Timer) action.getSource();
        panel.setBounds(to);
        parentTimer.stop();
        return;
      }

      final float percentageDone = (float) timeElapsed / durationMs;

      panel.setBounds(
          (int) (originalX + posXDiff * percentageDone),
          (int) (originalY + posYDiff * percentageDone),
          (int) (originalWidth + widthDiff * percentageDone),
          (int) (originalHeight + heightDiff * percentageDone)
      );
    }) {
      @Override
      public void stop() {
        super.stop();
        item.setTimer(null);
      }
    };
    item.setTimer(timer);
    timer.start();
  }

  /**
   * Updates the positions of the panels.
   */
  public void updatePanelPositions(boolean smooth) {
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

      final JPanel panel = item.getPanel();
      setLayer(
          panel,
          JLayeredPane.DEFAULT_LAYER + panels.length - Math.abs(displacement)
      );

      final Rectangle newBounds = new Rectangle(
          (int) posX,
          (int) posY,
          (int) panelWidth,
          (int) panelHeight
      );

      if (smooth && (Math.abs(displacement) < VISIBLE_NEIGHBORS)) {
        animateItem(item, newBounds);
      } else {
        panel.setBounds(newBounds);
      }
      panel.setVisible(true);
    }
  }

  static private class CarouselItem {
    private final int index;
    private final JPanel panel;
    private Timer timer;

    public CarouselItem(JPanel panel, int index) {
      this.panel = panel;
      this.index = index;
    }

    public JPanel getPanel() {
      return panel;
    }

    public int getIndex() {
      return index;
    }

    public void setTimer(@Nullable Timer timer) {
      this.timer = timer;
    }

    public Timer getTimer() {
      return timer;
    }
  }
}
