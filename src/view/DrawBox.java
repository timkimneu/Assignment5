package view;

import model.DaysOfTheWeek;
import model.IEvent;
import model.IReadOnlyPlannerModel;
import model.ISchedule;
import model.ITime;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DrawBox extends JPanel implements IDrawBox {

  protected final IReadOnlyPlannerModel<DaysOfTheWeek> model;

  protected String id;

  protected EventFrame eventFrame;

  private final Map<ArrayList<Double>, IEvent<DaysOfTheWeek>> eventCoords;
  private int width;
  private int height;

  public DrawBox(IReadOnlyPlannerModel<DaysOfTheWeek> model, String id, int width, int height, Map<ArrayList<Double>, IEvent<DaysOfTheWeek>> eventCoords) {
    this.model = model;
    this.id = id;
    this.eventCoords = eventCoords;
    this.width = width;
    this.height = height;
  }

  @Override
  public void drawScheduleState(Graphics2D g2d) {
    List<ISchedule<DaysOfTheWeek>> listSch = model.schedules();

    for (ISchedule<DaysOfTheWeek> currSch : listSch) {
      if (Objects.equals(currSch.scheduleID(), this.id)) {
        List<IEvent<DaysOfTheWeek>> listEvents = currSch.events();
        for (IEvent<DaysOfTheWeek> currEvent : listEvents) {
          ITime<DaysOfTheWeek> currTime = currEvent.time();
          fillSquares(g2d, currTime.startTime(), currTime.endTime(), currTime.startDay(),
              currTime.endDay(), currEvent, Color.pink);
        }
      }
    }
  }

  // searches through the start days and calls helper methods in order
  protected void fillSquares(Graphics2D g2d, String startTime, String endTime,
                             DaysOfTheWeek startDay, DaysOfTheWeek endDay, IEvent<DaysOfTheWeek> event, Color color) {
    g2d.setColor(color);
    switch (startDay) {
      case SUNDAY:
        fillRemainingDays(g2d, 0, startTime, endTime, startDay, endDay, event, color);
        break;
      case MONDAY:
        fillRemainingDays(g2d, 1, startTime, endTime, startDay, endDay, event, color);
        break;
      case TUESDAY:
        fillRemainingDays(g2d, 2, startTime, endTime, startDay, endDay, event, color);
        break;
      case WEDNESDAY:
        fillRemainingDays(g2d, 3, startTime, endTime, startDay, endDay, event, color);
        break;
      case THURSDAY:
        fillRemainingDays(g2d, 4, startTime, endTime, startDay, endDay, event, color);
        break;
      case FRIDAY:
        fillRemainingDays(g2d, 5, startTime, endTime, startDay, endDay, event, color);
        break;
      case SATURDAY:
        fillRemainingDays(g2d, 6, startTime, endTime, startDay, endDay, event, color);
        break;
      default:
        throw new IllegalArgumentException("Day does not exist");
    }
  }

  protected void fillRectSameDay(Graphics2D g2d, int col, String startTime, String endTime,
                                 IEvent<DaysOfTheWeek> event) {
    int startHour = Integer.parseInt(startTime) / 100;
    int startMin = Integer.parseInt(startTime) % 100;
    double mult = startHour + (startMin / 60.0);

    int endHour = Integer.parseInt(endTime) / 100;
    int endMin = Integer.parseInt(endTime) % 100;
    double endMult = endHour + (endMin / 60.0);
    double height = endMult - mult;

    double xCoord = (double) (col * this.width) / 7;
    double yCoord = mult * this.height / 24;
    double width = (double) this.width / 7;
    double heights = (double) this.height / 24 * height;
    Rectangle2D rect = new Rectangle2D.Double(xCoord, yCoord,
        width, heights);
    ArrayList<Double> coords = new ArrayList<>(List.of(xCoord, xCoord + width,
        yCoord, yCoord + heights));
    eventCoords.put(coords, event);

    g2d.fill(rect);
  }

  protected void fillRemainingDays(Graphics2D g2d, int col, String startTime, String endTime,
                                   DaysOfTheWeek startDay, DaysOfTheWeek endDay, IEvent<DaysOfTheWeek> event, Color color) {
    if (startDay == endDay) {
      fillRectSameDay(g2d, col, startTime, endTime, event);
      return;
    }
    List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday");
    g2d.setColor(color);
    int startInd = days.indexOf(startDay.observeDay());
    int endInd = days.indexOf(endDay.observeDay());
    if (endInd < startInd) {
      endInd = 7;
      endTime = "1159";
    }

    for (int day = startInd; day < endInd; day++) {
      if (day == startInd) {
        int startHour = Integer.parseInt(startTime) / 100;
        int startMin = Integer.parseInt(startTime) % 100;
        double mult = startHour + (startMin / 60.0);
        double height = (this.height - (mult * this.height / 24));

        double xCoord = (double) (col * this.width) / 7;
        double yCoord = ((int) mult * this.height) / 24 - 1;
        double width = (double) this.width / 7;
        double heights = (double) height;
        Rectangle2D rect = new Rectangle2D.Double(xCoord, yCoord, width, heights);
        ArrayList<Double> coords = new ArrayList<>(List.of(xCoord, xCoord + width,
            yCoord, yCoord + heights));
        eventCoords.put(coords, event);
        g2d.fill(rect);
      } else {
        double xCoord = (double) ((day % 7) * this.width) / 7;
        double yCoord = 0;
        double width = (double) this.width / 7;
        double heights = (double) this.height;
        Rectangle2D rect2 = new Rectangle2D.Double(xCoord, yCoord, width, heights);
        ArrayList<Double> coords = new ArrayList<>(List.of(xCoord, xCoord + width,
            yCoord, yCoord + heights));
        eventCoords.put(coords, event);
        g2d.fill(rect2);
      }
    }
    int endHour = Integer.parseInt(endTime) / 100;
    int endMin = Integer.parseInt(endTime) % 100;
    double mult = endHour + (endMin / 60.0);

    double xCoord = (double) (endInd * this.width) / 7;
    double yCoord = 0;
    double width = (double) this.width / 7;
    double heights = (double) this.height / 24 * mult;
    Rectangle2D rect = new Rectangle2D.Double(xCoord, yCoord, width, heights);
    ArrayList<Double> coords = new ArrayList<>(List.of(xCoord, xCoord + width,
        yCoord, yCoord + heights));
    eventCoords.put(coords, event);
    g2d.fill(rect);
  }

  public Map<ArrayList<Double>, IEvent<DaysOfTheWeek>> eventCoords() {
    return this.eventCoords;
  }

}
