package view;

import model.DaysOfTheWeek;
import model.Event;
import model.ReadOnlyPlannerModel;
import model.SchedulePlanner;
import model.Time;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class SchedulePanel extends JPanel implements ItemListener, ListSelectionListener {

  private final ReadOnlyPlannerModel model;

  private boolean userSelected;

  private String id;


  /**
   *
   *
   * @param model
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
//        Line2D line2D = new Line2D.Double(0, (double) this.getWidth() / 24 * line,
//            this.getWidth(), (double) this.getWidth() / 24 * line);
        g2d.drawLine(0, this.getWidth() / 24 * line,
            this.getWidth(), this.getWidth() / 24 * line);
//        g2d.draw(line2D);
      }
      else {
        g2d.setStroke(new BasicStroke(1));
//        Line2D line2D = new Line2D.Double(0, (double) this.getWidth() / 24 * line,
//            this.getWidth(), (double) this.getWidth() / 24 * line);
//        g2d.draw(line2D);
        g2d.drawLine(0, this.getWidth() / 24 * line,
            this.getWidth(), this.getWidth() / 24 * line);
      }
    }

    g2d.setStroke(new BasicStroke(1));
    for (int col = 0; col < 7; col++) {
      Line2D line2D = new Line2D.Double((double) this.getWidth() / 7 * col, 0,
          (double) this.getWidth() / 7 * col, this.getWidth());
      g2d.draw(line2D);
//      g2d.drawLine(this.getWidth() / 7 * col, 0,
//          this.getWidth() / 7 * col, this.getWidth());
    }
  }

  private void drawScheduleState(Graphics2D g2d) {
    java.util.List<SchedulePlanner> listSch = model.schedules();

    for (int sch = 0; sch < listSch.size(); sch++) {
      SchedulePlanner currSch = listSch.get(sch);
      if (currSch.scheduleID() == this.id) {
        System.out.println("HIII" + this.id);
        List<model.Event> listEvents = currSch.events();
        for (int event = 0; event < listEvents.size(); event++) {
          Event currEvent = listEvents.get(event);
          Time currTime = currEvent.time();
          System.out.println("STAETTTT" + currTime.startDay());
          fillSquares(g2d, currTime.startTime(), currTime.endTime(), currTime.startDay(), currTime.endDay());
        }
      }
    }
  }

  private void fillSquares(Graphics2D g2d, String startTime, String endTime, DaysOfTheWeek startDay, DaysOfTheWeek endDay) {
    g2d.setColor(Color.red);
    switch(startDay) {
      case SUNDAY:
        if (startDay == endDay) {
          fillRectSameDay(g2d, 0, startTime, endTime);
        }
        else {
          fillRemainingDays(g2d, 0, startTime, endTime, startDay, endDay);
        }
        break;
      case MONDAY:
        if (startDay == endDay) {
          fillRectSameDay(g2d, 1, startTime, endTime);
        }
        else {
          fillRemainingDays(g2d, 1, startTime, endTime, startDay, endDay);
        }
        break;
      case TUESDAY:
        if (startDay == endDay) {
          fillRectSameDay(g2d, 2, startTime, endTime);
        }
        else {
          fillRemainingDays(g2d, 2, startTime, endTime, startDay, endDay);
        }
        break;
      case WEDNESDAY:
        if (startDay == endDay) {
          fillRectSameDay(g2d, 3, startTime, endTime);
        }
        else {
          fillRemainingDays(g2d, 3, startTime, endTime, startDay, endDay);
        }
        break;
      case THURSDAY:
        if (startDay == endDay) {
          fillRectSameDay(g2d, 4, startTime, endTime);
        }
        else {
          fillRemainingDays(g2d, 4, startTime, endTime, startDay, endDay);
        }
        break;
      case FRIDAY:
        if (startDay == endDay) {
          fillRectSameDay(g2d, 5, startTime, endTime);
        }
        else {
          fillRemainingDays(g2d, 5, startTime, endTime, startDay, endDay);
        }
        break;
      case SATURDAY:
        if (startDay == endDay) {
          fillRectSameDay(g2d, 6, startTime, endTime);
        }
        else {
          fillRemainingDays(g2d, 6, startTime, endTime, startDay, endDay);
        }
        break;
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
    Rectangle2D rect = new Rectangle2D.Double((double) (col * this.getWidth()) / 7, mult * this.getHeight() / 24,
        (double) this.getWidth() / 7, (double) this.getHeight() / 24 * height);
    g2d.fill(rect);
  }

  private void fillRemainingDays(Graphics2D g2d, int col, String startTime, String endTime, DaysOfTheWeek startDay, DaysOfTheWeek endDay) {
    List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
    g2d.setColor(Color.red);
    int startInd = days.indexOf(startDay.observeDay());
    int endInd = days.indexOf(endDay.observeDay());
    if (endInd < startInd) {
      endInd = 7;
      endTime = "1159";
    }

    for (int day = startInd; day < endInd; day++) {
      if (day == startInd) {
        System.out.println("START TIME" + Integer.parseInt(startTime) / 100);
        int startHour = Integer.parseInt(startTime) / 100;
        int startMin = Integer.parseInt(startTime) % 100;
        double mult = startHour + (startMin / 60.0);
        double height = (this.getHeight() - (mult * this.getHeight() / 24));
        System.out.println("HIEHGTT" + (double) (col * this.getWidth()) / 7);
        System.out.println("YYYY" + mult * this.getHeight() / 24);

        Rectangle2D rect = new Rectangle2D.Double((double) (col * this.getWidth()) / 7, ((int) mult * this.getHeight()) / 24 - 1,
            (double) this.getWidth() / 7, (double) height);
        g2d.fill(rect);
      }
      else {
        Rectangle2D rect2 = new Rectangle2D.Double((double) ((day % 7) * this.getWidth()) / 7, 0,
            (double) this.getWidth() / 7, (double) this.getHeight());
        g2d.fill(rect2);
      }
    }
    //if (endInd != 7) {
      System.out.println("SHOW HEREEEEEE");
      int endHour = Integer.parseInt(endTime) / 100;
      int endMin = Integer.parseInt(endTime) % 100;
      System.out.println("ENDDDD" + endTime);
      double mult = endHour + (endMin / 60.0);

    Rectangle2D rect = new Rectangle2D.Double((double) (endInd * this.getWidth()) / 7, 0,
        (double) this.getWidth() / 7, (double) this.getHeight() / 24 * mult);
      g2d.fill(rect);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }

  public void drawDates(String user) {
    this.userSelected = true;
    this.id = user;
    repaint();
  }
}
