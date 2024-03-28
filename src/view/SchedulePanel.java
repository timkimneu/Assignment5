package view;

import model.DaysOfTheWeek;
import model.Event;
import model.ReadOnlyPlannerModel;
import model.SchedulePlanner;
import model.Time;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

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

  private final ReadOnlyPlannerModel model;

  private boolean userSelected;

  private String id;


  /**
   * Initializes the GUI and paints the screen with lines to organize the schedule into
   * 7 days of the week represented by 7 columns and 24 hours represented by 24 boxes
   * for each day as 24 rectangles.
   *
   * @param model takes in a ReadOnlyPlannerModel to have access to a user's schedule.
   */
  public SchedulePanel(ReadOnlyPlannerModel model) {
    super();
    this.model = model;
    this.userSelected = false;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D)g;

    if (this.userSelected) {
      drawScheduleState(g2d);
    }

    g2d.setPaint(Color.black);
    for (int line = 0; line < 24; line++) {
      if (line % 4 == 0) {
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(0, this.getWidth() / 24 * line,
            this.getWidth(), this.getWidth() / 24 * line);
      }
      else {
        g2d.setStroke(new BasicStroke(1));
        g2d.drawLine(0, this.getWidth() / 24 * line,
            this.getWidth(), this.getWidth() / 24 * line);
      }
    }

    g2d.setStroke(new BasicStroke(1));
    for (int col = 0; col < 7; col++) {
      Line2D line2D = new Line2D.Double((double) this.getWidth() / 7 * col, 0,
          (double) this.getWidth() / 7 * col, this.getWidth());
      g2d.draw(line2D);
    }
  }

  private void drawScheduleState(Graphics2D g2d) {
    java.util.List<SchedulePlanner> listSch = model.schedules();

    for (int sch = 0; sch < listSch.size(); sch++) {
      SchedulePlanner currSch = listSch.get(sch);
      if (currSch.scheduleID() == this.id) {
        List<model.Event> listEvents = currSch.events();
        for (int event = 0; event < listEvents.size(); event++) {
          Event currEvent = listEvents.get(event);
          Time currTime = currEvent.time();
          fillSquares(g2d, currTime.startTime(), currTime.endTime(), currTime.startDay(),
              currTime.endDay());
        }
      }
    }
  }

  // searches through the start days and calls helper methods in order
  private void fillSquares(Graphics2D g2d, String startTime, String endTime,
                           DaysOfTheWeek startDay, DaysOfTheWeek endDay) {
    g2d.setColor(Color.red);
    switch (startDay) {
      case SUNDAY:
        fillRemainingDays(g2d, 0, startTime, endTime, startDay, endDay);
        break;
      case MONDAY:
        fillRemainingDays(g2d, 1, startTime, endTime, startDay, endDay);
        break;
      case TUESDAY:
        fillRemainingDays(g2d, 2, startTime, endTime, startDay, endDay);
        break;
      case WEDNESDAY:
        fillRemainingDays(g2d, 3, startTime, endTime, startDay, endDay);
        break;
      case THURSDAY:
        fillRemainingDays(g2d, 4, startTime, endTime, startDay, endDay);
        break;
      case FRIDAY:
        fillRemainingDays(g2d, 5, startTime, endTime, startDay, endDay);
        break;
      case SATURDAY:
        fillRemainingDays(g2d, 6, startTime, endTime, startDay, endDay);
        break;
      default:
        throw new IllegalArgumentException("Day does not exist");
    }
  }

  private void fillRectSameDay(Graphics2D g2d, int col, String startTime, String endTime) {
    int startHour = Integer.parseInt(startTime) / 100;
    int startMin = Integer.parseInt(startTime) % 100;
    double mult = startHour + (startMin / 60.0);

    int endHour = Integer.parseInt(endTime) / 100;
    int endMin = Integer.parseInt(endTime) % 100;
    double endMult = endHour + (endMin / 60.0);
    double height = endMult - mult;
    Rectangle2D rect = new Rectangle2D.Double((double) (col * this.getWidth()) / 7,
        mult * this.getHeight() / 24,
        (double) this.getWidth() / 7, (double) this.getHeight() / 24 * height);
    g2d.fill(rect);
  }

  private void fillRemainingDays(Graphics2D g2d, int col, String startTime, String endTime,
                                 DaysOfTheWeek startDay, DaysOfTheWeek endDay) {
    if (startDay == endDay) {
      fillRectSameDay(g2d, col, startTime, endTime);
      return;
    }
    List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
        "Friday", "Saturday");
    g2d.setColor(Color.red);
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

        Rectangle2D rect = new Rectangle2D.Double((double) (col * this.getWidth()) / 7,
            ((int) mult * this.getHeight()) / 24 - 1,
            (double) this.getWidth() / 7, (double) height);
        g2d.fill(rect);
      }
      else {
        Rectangle2D rect2 = new Rectangle2D.Double((double) ((day % 7) * this.getWidth()) / 7,
            0, (double) this.getWidth() / 7, (double) this.getHeight());
        g2d.fill(rect2);
      }
    }
    int endHour = Integer.parseInt(endTime) / 100;
    int endMin = Integer.parseInt(endTime) % 100;
    double mult = endHour + (endMin / 60.0);

    Rectangle2D rect = new Rectangle2D.Double((double) (endInd * this.getWidth()) / 7,
        0, (double) this.getWidth() / 7, (double) this.getHeight() / 24 * mult);
    g2d.fill(rect);
  }

  @Override
  public void drawDates(String user) {
    this.userSelected = true;
    this.id = user;
    repaint();
  }
}
