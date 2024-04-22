package view;

import controller.ScheduleSystem;
import model.IEvent;
import model.IReadOnlyPlannerModel;
import model.ISchedule;
import model.ITime;
import model.SatDOTW;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SatSchedulePanel extends JPanel implements SchPanel<SatDOTW> {
  private final IReadOnlyPlannerModel<SatDOTW> model;
  private boolean userSelected;
  protected String id;
  private final EventFrame eventFrame;
  private final Map<ArrayList<Double>, IEvent<SatDOTW>> eventCoords;

  public SatSchedulePanel(IReadOnlyPlannerModel<SatDOTW> model,
                          EventFrame eventFrame) {
    super();
    this.model = model;
    this.eventFrame = eventFrame;
    this.userSelected = false;
    this.eventCoords = new HashMap<>();
  }

  @Override
  public void drawScheduleState(Graphics2D g2d) {
    List<ISchedule<SatDOTW>> listSch = model.schedules();

    for (ISchedule<SatDOTW> currSch : listSch) {
      if (Objects.equals(currSch.scheduleID(), this.id)) {
        List<IEvent<SatDOTW>> listEvents = currSch.events();
        for (IEvent<SatDOTW> currEvent : listEvents) {
          ITime<SatDOTW> currTime = currEvent.time();
          fillSquares(g2d, currTime.startTime(), currTime.endTime(), currTime.startDay(),
              currTime.endDay(), currEvent, Color.pink);
        }
      }
    }
  }

  @Override
  public void drawDates(String user) {
    System.out.println("Schedule draw");
    this.userSelected = true;
    this.id = user;
    repaint();
  }

  private void fillSquares(Graphics2D g2d, String startTime, String endTime, SatDOTW startDay,
                          SatDOTW endDay, IEvent<SatDOTW> event, Color color) {
    g2d.setColor(color);
    switch (startDay) {
      case SUNDAY:
        fillRemainingDays(g2d, 1, startTime, endTime, startDay, endDay, event, color);
        break;
      case MONDAY:
        fillRemainingDays(g2d, 2, startTime, endTime, startDay, endDay, event, color);
        break;
      case TUESDAY:
        fillRemainingDays(g2d, 3, startTime, endTime, startDay, endDay, event, color);
        break;
      case WEDNESDAY:
        fillRemainingDays(g2d, 4, startTime, endTime, startDay, endDay, event, color);
        break;
      case THURSDAY:
        fillRemainingDays(g2d, 5, startTime, endTime, startDay, endDay, event, color);
        break;
      case FRIDAY:
        fillRemainingDays(g2d, 6, startTime, endTime, startDay, endDay, event, color);
        break;
      case SATURDAY:
        fillRemainingDays(g2d, 0, startTime, endTime, startDay, endDay, event, color);
        break;
      default:
        throw new IllegalArgumentException("Day does not exist");
    }
  }

  private void fillRectSameDay(Graphics2D g2d, int col, String startTime, String endTime,
                               IEvent<SatDOTW> event) {
    int startHour = Integer.parseInt(startTime) / 100;
    int startMin = Integer.parseInt(startTime) % 100;
    double mult = startHour + (startMin / 60.0);

    int endHour = Integer.parseInt(endTime) / 100;
    int endMin = Integer.parseInt(endTime) % 100;
    double endMult = endHour + (endMin / 60.0);
    double height = endMult - mult;

    double xCoord = (double) (col * this.getWidth()) / 7;
    double yCoord = mult * this.getHeight() / 24;
    double width = (double) this.getWidth() / 7;
    double heights = (double) this.getHeight() / 24 * height;
    Rectangle2D rect = new Rectangle2D.Double(xCoord, yCoord,
        width, heights);
    ArrayList<Double> coords = new ArrayList<>(List.of(xCoord, xCoord + width,
        yCoord, yCoord + heights));
    eventCoords.put(coords, event);
    g2d.fill(rect);
  }

  private void fillRemainingDays(Graphics2D g2d, int col, String startTime, String endTime,
                                 SatDOTW startDay, SatDOTW endDay, IEvent<SatDOTW> event,
                                 Color color) {
    if (startDay == endDay) {
      fillRectSameDay(g2d, col, startTime, endTime, event);
      return;
    }
    List<String> days = Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday");
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
        double height = (this.getHeight() - (mult * this.getHeight() / 24));

        double xCoord = (double) (col * this.getWidth()) / 7;
        double yCoord = ((int) mult * this.getHeight()) / 24 - 1;
        double width = (double) this.getWidth() / 7;
        double heights = (double) height;
        Rectangle2D rect = new Rectangle2D.Double(xCoord, yCoord, width, heights);
        ArrayList<Double> coords = new ArrayList<>(List.of(xCoord, xCoord + width,
            yCoord, yCoord + heights));
        eventCoords.put(coords, event);
        g2d.fill(rect);
      } else {
        double xCoord = (double) ((day % 7) * this.getWidth()) / 7;
        double yCoord = 0;
        double width = (double) this.getWidth() / 7;
        double heights = (double) this.getHeight();
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

    double xCoord = (double) (endInd * this.getWidth()) / 7;
    double yCoord = 0;
    double width = (double) this.getWidth() / 7;
    double heights = (double) this.getHeight() / 24 * mult;
    Rectangle2D rect = new Rectangle2D.Double(xCoord, yCoord, width, heights);
    ArrayList<Double> coords = new ArrayList<>(List.of(xCoord, xCoord + width,
        yCoord, yCoord + heights));
    eventCoords.put(coords, event);
    g2d.fill(rect);
  }

  @Override
  public void addListener(ScheduleSystem<SatDOTW> controller) {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int row = e.getX();
        int col = e.getY();
        for (Map.Entry<ArrayList<Double>, IEvent<SatDOTW>> entry : eventCoords.entrySet()) {
          ArrayList<Double> value = entry.getKey();
          if (row >= value.get(0) && row <= value.get(1)
              && col >= value.get(2) && col <= value.get(3)) {
//            eventFrame.addDefaultEvent(entry.getValue());
//            eventFrame.makeVisible();
//            eventFrame.getUnmodifiedEvent(entry.getValue());
            break;
          }
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        //ignore
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        //ignore
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        //ignore
      }

      @Override
      public void mouseExited(MouseEvent e) {
        //ignore
      }
    });
  }
}
