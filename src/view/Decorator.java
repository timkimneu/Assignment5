package view;

import model.DaysOfTheWeek;
import model.IEvent;
import model.IReadOnlyPlannerModel;
import model.IReadOnlyPlannerModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Decorator implements IDrawBox {

  private IDrawBox drawBox;
  private boolean isHost;
  private final IReadOnlyPlannerModel model;
  protected Map<ArrayList<Double>,IEvent<DaysOfTheWeek>> eventCoords;

  private final DrawHostBox drawHostBox;

  private final DrawBox drawNonToggleBox;

  private final String id;
  private EventFrame eventFrame;


  public Decorator(IDrawBox drawBox, boolean isHost, IReadOnlyPlannerModel model, String id, Map<ArrayList<Double>, IEvent<DaysOfTheWeek>> eventCoords, int width, int height) {
    this.drawBox = drawBox;
    this.isHost = isHost;
    this.model = model;
    //this.eventCoords = eventCoords;
    this.drawHostBox = new DrawHostBox(model, id, width, height, eventCoords);
    this.drawNonToggleBox = new DrawBox(model, id, width, height, eventCoords);
    this.id = id;
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
