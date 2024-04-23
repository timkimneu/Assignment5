package view;

import model.DaysOfTheWeek;
import model.IEvent;
import model.IReadOnlyPlannerModel;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Decorator to fill boxes with a color to indicate the presence of an event.
 */
public class Decorator implements IDrawBox {
  private boolean isHost;
  protected Map<ArrayList<Double>,IEvent<DaysOfTheWeek>> eventCoords;
  private final DrawHostBox drawHostBox;
  private final DrawBox drawNonToggleBox;
  private EventFrame eventFrame;

  /**
   * Decorator to fill boxes with a color to indicate the presence of an event.
   *
   * @param drawBox generic draw box class to draw boxes.
   * @param isHost boolean to indicate if selected user is host.
   * @param model model type for schedule structure.
   * @param id string id of selected user.
   * @param eventCoords event to be drawn.
   * @param width width of screen.
   * @param height height of screen.
   */
  public Decorator(IDrawBox drawBox, boolean isHost, IReadOnlyPlannerModel model, String id,
                   Map<ArrayList<Double>, IEvent<DaysOfTheWeek>> eventCoords, int width,
                   int height) {
    this.isHost = isHost;
    this.drawHostBox = new DrawHostBox(model, id, width, height, eventCoords);
    this.drawNonToggleBox = new DrawBox(model, id, width, height, eventCoords);
    this.eventCoords = new HashMap<>();
  }

  @Override
  public void drawScheduleState(Graphics2D g2d) {
    if (isHost) {
      drawHostBox.drawScheduleState(g2d);
      eventCoords = drawHostBox.eventCoords();
    }
    else {
      drawNonToggleBox.drawScheduleState(g2d);
      eventCoords = drawHostBox.eventCoords();
    }
  }

  public Map<ArrayList<Double>, IEvent<DaysOfTheWeek>> eventCoords() {
    return eventCoords;
  }
}
