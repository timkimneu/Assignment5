package view;

import model.DaysOfTheWeek;
import model.IEvent;
import model.IReadOnlyPlannerModel;
import model.ISchedule;
import model.ITime;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class that fills boxes with a color to indicate the presence of an event for a host.
 */
public class DrawHostBox extends DrawBox {

  /**
   * Class that fills boxes with a color to indicate the presence of an event for a host.
   *
   * @param model Model to structure calendar on.
   * @param id String id of owner of a schedule
   * @param width Width of screen.
   * @param height Height of screen.
   * @param eventCoords Event to be drawn.
   */
  public DrawHostBox(IReadOnlyPlannerModel<DaysOfTheWeek> model, String id, int width, int height,
                     Map<ArrayList<Double>, IEvent<DaysOfTheWeek>> eventCoords) {
    super(model, id, width, height, eventCoords);
  }

  @Override
  public void drawScheduleState(Graphics2D g2d) {
    java.util.List<ISchedule<DaysOfTheWeek>> listSch = model.schedules();

    for (ISchedule<DaysOfTheWeek> currSch : listSch) {
      if (Objects.equals(currSch.scheduleID(), this.id)) {
        List<IEvent<DaysOfTheWeek>> listEvents = currSch.events();
        for (IEvent<DaysOfTheWeek> currEvent : listEvents) {
          ITime<DaysOfTheWeek> currTime = currEvent.time();

          if (currEvent.host().name().replaceAll("\"", "")
                  .equals(this.id.replaceAll("\"", ""))) {
            fillSquares(g2d, currTime.startTime(), currTime.endTime(), currTime.startDay(),
                currTime.endDay(), currEvent, Color.BLUE);
          }
          else {
            fillSquares(g2d, currTime.startTime(), currTime.endTime(), currTime.startDay(),
                currTime.endDay(), currEvent, Color.PINK);
          }
        }
      }
    }
  }
}
