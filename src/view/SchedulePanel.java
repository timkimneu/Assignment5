package view;

import controller.ScheduleSystem;
import model.DaysOfTheWeek;
import model.IEvent;
import model.IReadOnlyPlannerModel;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
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
public class SchedulePanel extends JPanel implements SchPanel<DaysOfTheWeek> {
  private final IReadOnlyPlannerModel<DaysOfTheWeek> model;
  private boolean userSelected;
  protected String id;
  private boolean host;
  private final EventFrame eventFrame;
  private Map<ArrayList<Double>, IEvent<DaysOfTheWeek>> eventCoords;

  /**
   * Initializes the GUI and paints the screen with lines to organize the schedule into
   * 7 days of the week represented by 7 columns and 24 hours represented by 24 boxes
   * for each day as 24 rectangles.
   *
   * @param model takes in a ReadOnlyPlannerModel to have access to a user's schedule.
   */
  public SchedulePanel(IReadOnlyPlannerModel<DaysOfTheWeek> model, EventFrame eventFrame) {
    super();
    this.model = model;
    this.eventFrame = eventFrame;
    this.userSelected = false;
    this.eventCoords = new HashMap<>();
    IDrawBox drawEvent = new DrawBox(model, this.id, this.getWidth(), this.getHeight(),
            eventCoords);
    Decorator decorator = new Decorator(drawEvent, host, this.model, this.id, eventCoords,
            this.getWidth(), this.getHeight());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    DrawBox drawBox = new DrawBox(model, this.id, this.getWidth(), this.getHeight(), eventCoords);
    Decorator decorator = new Decorator(drawBox, host, model, this.id, eventCoords,
            this.getWidth(), this.getHeight());

    if (this.userSelected) {
      //drawScheduleState(g2d);
      decorator.drawScheduleState(g2d);
    }

    this.eventCoords = decorator.eventCoords();
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

  @Override
  public void drawScheduleState(Graphics2D g2d) {
    // moved to decorator pattern
  }

  @Override
  public void drawDates(String user, boolean host) {
    this.userSelected = true;
    this.id = user;
    this.host = host;
    repaint();
  }

  @Override
  public void addListener(ScheduleSystem<DaysOfTheWeek> controller) {
    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        int row = e.getX();
        int col = e.getY();
        for (Map.Entry<ArrayList<Double>, IEvent<DaysOfTheWeek>> entry : eventCoords.entrySet()) {
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
