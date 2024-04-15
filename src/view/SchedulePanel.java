package view;

import controller.ScheduleSystem;
import model.DaysOfTheWeek;
import model.EventImpl;
import model.IReadOnlyPlannerModel;
import model.SchedulePlanner;
import model.TimeImpl;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creates a GUI to visualize the individual schedules planner model and users'
 * schedules with a grid with each column representing a day of the week starting
 * with Sunday and each box representing an hour, starting from 12am or 0000.
 * Allows for the selection of an individual user's schedule from the available
 * list of users in the model and displays the user's schedule in the GUI/grid.
 * Empty/blank/white spaces represent the absence of events while red/filled
 * rectangles represent a time in which an Event occupies.
 */
public class SchedulePanel extends JPanel implements SchPanel {

  private final IReadOnlyPlannerModel model;

  private boolean userSelected;

  private String id;

  private EventFrame eventFrame;

  private final Map<ArrayList<Double>, EventImpl> eventCoords;


  /**
   * Initializes the GUI and paints the screen with lines to organize the schedule into
   * 7 days of the week represented by 7 columns and 24 hours represented by 24 boxes
   * for each day as 24 rectangles.
   *
   * @param model takes in a ReadOnlyPlannerModel to have access to a user's schedule.
   */
  public SchedulePanel(IReadOnlyPlannerModel model, EventFrame eventFrame) {
    super();
    this.model = model;
    this.eventFrame = eventFrame;
    this.userSelected = false;
    this.eventCoords = new HashMap<>();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    if (this.userSelected) {
      drawScheduleState(g2d);
    }

    g2d.setPaint(Color.black);
    for (int line = 0; line < 24; line++) {
      if (line % 4 == 0) {
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(0, (int) (this.getHeight() / 24.0 * line),
                this.getWidth(), (int) (this.getHeight() / 24.0 * line));
      } else {
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0, (int) (this.getHeight() / 24.0 * line),
                this.getWidth(), (int) (this.getHeight() / 24.0 * line));
      }
    }

    g2d.setStroke(new BasicStroke(1));
    for (int col = 0; col < 7; col++) {
      Line2D line2D = new Line2D.Double((double) this.getWidth() / 7.0 * col, 0,
              (double) this.getWidth() / 7.0 * col, this.getHeight());
      g2d.draw(line2D);
    }
  }

  private void drawScheduleState(Graphics2D g2d) {
    java.util.List<SchedulePlanner> listSch = model.schedules();

    for (int sch = 0; sch < listSch.size(); sch++) {
      SchedulePlanner currSch = listSch.get(sch);
      if (currSch.scheduleID() == this.id) {
        List<EventImpl> listEvents = currSch.events();
        for (int event = 0; event < listEvents.size(); event++) {
          EventImpl currEvent = listEvents.get(event);
          TimeImpl currTime = currEvent.time();
          fillSquares(g2d, currTime.startTime(), currTime.endTime(), currTime.startDay(),
                  currTime.endDay(), currEvent);
        }
      }
    }
  }

  // searches through the start days and calls helper methods in order
  private void fillSquares(Graphics2D g2d, String startTime, String endTime,
                           DaysOfTheWeek startDay, DaysOfTheWeek endDay, EventImpl event) {
    g2d.setColor(Color.pink);
    switch (startDay) {
      case SUNDAY:
        fillRemainingDays(g2d, 0, startTime, endTime, startDay, endDay, event);
        break;
      case MONDAY:
        fillRemainingDays(g2d, 1, startTime, endTime, startDay, endDay, event);
        break;
      case TUESDAY:
        fillRemainingDays(g2d, 2, startTime, endTime, startDay, endDay, event);
        break;
      case WEDNESDAY:
        fillRemainingDays(g2d, 3, startTime, endTime, startDay, endDay, event);
        break;
      case THURSDAY:
        fillRemainingDays(g2d, 4, startTime, endTime, startDay, endDay, event);
        break;
      case FRIDAY:
        fillRemainingDays(g2d, 5, startTime, endTime, startDay, endDay, event);
        break;
      case SATURDAY:
        fillRemainingDays(g2d, 6, startTime, endTime, startDay, endDay, event);
        break;
      default:
        throw new IllegalArgumentException("Day does not exist");
    }
  }

  private void fillRectSameDay(Graphics2D g2d, int col, String startTime, String endTime,
                               EventImpl event) {
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
                                 DaysOfTheWeek startDay, DaysOfTheWeek endDay, EventImpl event) {
    if (startDay == endDay) {
      fillRectSameDay(g2d, col, startTime, endTime, event);
      return;
    }
    List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday", "Saturday");
    g2d.setColor(Color.pink);
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
  public void drawDates(String user) {
    this.userSelected = true;
    this.id = user;
    repaint();
  }

  @Override
  public void addListener(ScheduleSystem controller) {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int row = e.getX();
        int col = e.getY();
        for (Map.Entry<ArrayList<Double>, EventImpl> entry : eventCoords.entrySet()) {
          ArrayList<Double> value = entry.getKey();
          if (row >= value.get(0) && row <= value.get(1)
                  && col >= value.get(2) && col <= value.get(3)) {
            eventFrame.addDefaultEvent(entry.getValue());
            eventFrame.makeVisible();
            eventFrame.getUnmodifiedEvent(entry.getValue());
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
