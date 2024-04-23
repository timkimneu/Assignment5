package view;

import java.awt.Graphics2D;

/**
 * Class that fills boxes with a color to indicate the presence of an event.
 */
public interface IDrawBox {

  /**
   * Draws the current schedule state.
   *
   * @param g2d Java swing graphics object.
   */
  void drawScheduleState(Graphics2D g2d);
}
