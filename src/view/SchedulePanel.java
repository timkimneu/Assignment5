package view;

import model.DaysOfTheWeek;
import model.Event;
import model.ReadOnlyPlannerModel;
import model.SchedulePlanner;
import model.Time;
import model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class SchedulePanel extends JPanel implements ActionListener, ItemListener, ListSelectionListener {

  private final ReadOnlyPlannerModel model;

  private String startTime;
  private String endTime;
  private DaysOfTheWeek startDay;
  private DaysOfTheWeek endDay;
  private boolean userSelected;

  private String id;


  /**
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

    for (int line = 0; line < 24; line++) {
      if (line % 4 == 0) {
        g2d.setStroke(new BasicStroke(5));
//        Line2D line2D = new Line2D.Double(0, (double) this.getWidth() / 24 * line,
//            this.getWidth(), (double) this.getWidth() / 24 * line);
        g2d.drawLine(0, this.getWidth() / 24 * line,
            this.getWidth(), this.getWidth() / 24 * line);
        //g2d.draw(line2D);
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
    if (this.userSelected) {
      drawScheduleState(g2d);
    }
  }

  private void drawScheduleState(Graphics2D g2d) {
//    g2d.setPaint(Color.pink);
//    g2d.fillRect(1, 1, 100, 100);
    java.util.List<SchedulePlanner> listSch = model.schedules();

    for (int sch = 0; sch < listSch.size(); sch++) {
      SchedulePlanner currSch = listSch.get(sch);
      if (currSch.scheduleID() == this.id) {
        System.out.println("HIII" + this.id);
        List<model.Event> listEvents = currSch.events();
        //System.out.println("EVENT NUM" + listEvents.size());
        for (int event = 0; event < listEvents.size(); event++) {
          Event currEvent = listEvents.get(event);
          Time currTime = currEvent.time();
          //System.out.println(currTime.startDay());
          System.out.println("STAETTTT" + currTime.startDay());
          fillSquares(g2d, currTime.startTime(), currTime.endTime(), currTime.startDay(), currTime.endDay());
//          g2d.setColor(Color.pink);
//          g2d.fillRect(1, 1, 100, 100);
        }
      }
    }
  }

  private void fillSquares(Graphics2D g2d, String startTime, String endTime, DaysOfTheWeek startDay, DaysOfTheWeek endDay) {
    List<String> days = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
    g2d.setColor(Color.red);
    System.out.println("start" + startDay);
    switch(startDay) {
      case SUNDAY:
        fillRectSameDay(g2d, 0, startTime, endTime);
        //g2d.fillRect(0, 0, this.getWidth() / 7, this.getHeight() / 24);
      case MONDAY:
        //g2d.fillRect(1, 1, 100, 100);
      case TUESDAY:
        System.out.println(startTime);
        if (startDay == endDay) {
          fillRectSameDay(g2d, 2, startTime, endTime);
        }
        else {
          int startInd = days.indexOf(startDay.observeDay());
          int endInd = days.indexOf(endDay.observeDay());

          for (int day = startInd; day < endInd; day++) {
            if (day == startInd) {
              int startHour = Integer.parseInt(startTime) / 100;
              int startMin = Integer.parseInt(startTime) % 100;
              double mult = startHour + (startMin / 60.0);
              double height = (this.getHeight() - (mult * this.getHeight() / 24));
              System.out.println("HIEHGTT" + height);

              Rectangle2D rect = new Rectangle2D.Double((double) (2 * this.getWidth()) / 7, mult * this.getHeight() / 24,
                  (double) this.getWidth() / 7, (double) height);
              g2d.fill(rect);
            }
          }
        }

      case WEDNESDAY:
        //g2d.fillRect(1, 1, 100, 100);
      case THURSDAY:
        //g2d.fillRect(1, 1, 100, 100);
      case FRIDAY:
        if (startDay == endDay) {
          fillRectSameDay(g2d, 2, startTime, endTime);
        }
        else {
          System.out.println("HIIIII ELSE");
          int startInd = days.indexOf(startDay.observeDay());
          int endInd = days.indexOf(endDay.observeDay());
          if (endInd == 0) {
            endInd = 7;
          }
          System.out.println(startInd);
          System.out.println(endInd);

          for (int day = startInd; day < endInd; day++) {
            Rectangle2D rect2 = null;
            if (day == startInd) {
              int startHour = Integer.parseInt(startTime) / 100;
              int startMin = Integer.parseInt(startTime) % 100;
              double mult = startHour + (startMin / 60.0);
              double height = (this.getHeight() - (mult * this.getHeight() / 24));
              System.out.println("HIEHGTT" + (double) (5 * this.getWidth()) / 7);

              Rectangle2D rect = new Rectangle2D.Double((double) (5 * this.getWidth()) / 7, mult * this.getHeight() / 24,
                  (double) this.getWidth() / 7, (double) height);
              g2d.fill(rect);
            }
            else {
              System.out.println("ENDDDD" + day);
              rect2 = new Rectangle2D.Double((double) ((day % 7) * this.getWidth()) / 7, 0,
                  (double) this.getWidth() / 7, (double) this.getHeight());
              g2d.fill(rect2);
            }
          }
        }
        //g2d.fillRect(1, 1, 100, 100);
      case SATURDAY:
        //g2d.fillRect(1, 1, 100, 100);
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

  @Override
  public void actionPerformed(ActionEvent e) {

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
