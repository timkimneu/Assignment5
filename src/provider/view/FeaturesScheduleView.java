package provider.view;

import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

import javax.swing.event.MouseInputAdapter;
import javax.swing.JPanel;

import provider.controller.Features;
import provider.model.Event;
import provider.model.ReadOnlySchedule;
import provider.model.WeekTime;
import provider.model.impl.LocalWeekTime;
import provider.model.impl.WeekDay;
import provider.view.SchedulePanel;

/**
 * A SchedulePanel implemented for JavaSwing. Dynamically draws events based on the current size
 * of the panel. When a mouse clicks on an event, a LocalScheduleModelPanel is capable of showing
 * the details of an event. This class delegates to a features class in order to perform its
 * functionality
 */
public class FeaturesScheduleView extends JPanel implements SchedulePanel {

  // Because this class cannot currently delegate to the features interface, it is necessary
  // to hold onto the schedule that is being currently represented in order to create an event
  // window to display the details of an event.
  private ReadOnlySchedule currentSchedule;

  // The logical width of a schedule panel.
  // 700 is a multiple of 7, making it easy to do math to translate a weekday to a
  // horizontal pixel position.
  private final int LOGICAL_WIDTH = 700;

  // The logical height of a schedule panel
  // 24*60 is the number of minutes in a day, making it easy to translate from a time to a
  // vertical pixel position.
  private final int LOGICAL_HEIGHT = 60 * 24;

  // features to delegate to to implement functionality
  private Features features;

  /**
   * Constructs a new ScheduleModelPanel that does not display a schedule to start with.
   */
  public FeaturesScheduleView() throws IllegalArgumentException {
    super();

    setBackground(Color.WHITE);

    // A new listener so that the client can interact with displayed events.
    MouseEventsListener listener = new MouseEventsListener();
    this.addMouseListener(listener);
  }

  /**
   * Draws lines that divide the canvas into 7 days with 24 hours per day. The first represented
   * day is Sunday, and the last is Saturday. The first hour is 0, and the last is 23. If
   * the Panel currently stores a schedule, draws the schedule too with a rectangle representing
   * the duration of an event.
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();

    // Enables g2d to draw using logical coordinates, making math more simple
    g2d.transform(transformLogicalToPhysical());

    // draw the current schedule and then the gridlines
    this.drawSchedule(g2d);
    this.drawScheduleGrid(g2d);
  }

  @Override
  public void displaySchedule(ReadOnlySchedule schedule) {
    if (schedule == null) {
      throw new IllegalArgumentException("Schedule cannot be null");
    }

    // repaint uses the currently stored schedule. Thus, to display a new schedule
    // we only need to change the reference we store and repaint the panel.
    this.currentSchedule = schedule;
    this.repaint();
  }

  @Override
  public void addFeatures(Features features) {
    this.features = features;
  }

  /**
   * Draws every event in the current schedule if it is not null.
   *
   * @param g2d a Graphics2D object that has already been transformed to draw take logical
   *            coordinates and give physical coordinates.
   */
  private void drawSchedule(Graphics2D g2d) {
    if (this.currentSchedule == null) {
      return;
    }
    for (Event event : this.currentSchedule.getEvents()) {
      this.drawEvent(event, g2d);
    }
  }

  /**
   * Draws an event in red on the schedule panel.
   *
   * @param event an event to draw
   * @param g2d   a Graphics2D object that has already been transformed to draw take logical
   *              coordinates and give physical coordinates.
   */
  private void drawEvent(Event event, Graphics2D g2d) {
    WeekTime startTime = event.getEventTime().getStartTime();

    // If the end time is before the start time, make end time the end of the week since we only
    // draw one week at a time. Otherwise, the events regular end time is it's visual end time.
    WeekTime endTime;
    if (event.getEventTime().getEndTime().isBefore(startTime)) {
      endTime = new LocalWeekTime(WeekDay.SATURDAY, 23, 59);
    } else {
      endTime = event.getEventTime().getEndTime();
    }

    // A day can be either a single day or span multiple days. Iterate across all days spanned by
    // the event (which could be 1) and draw a rectangle on each day corresponding to when the
    // start and end days are.
    for (int day = startTime.getWeekDay().getDayOrder();
         day <= endTime.getWeekDay().getDayOrder();
         day++) {
      drawDay(day, startTime, endTime, g2d);
    }
  }

  /**
   * Draws the day of an event. This ensures that multi-day and single-day events are drawn properly
   *
   * @param day       an integer representing the value of a day some time between the day of start
   *                  time
   *                  and end time.
   * @param startTime the start time of the event that is being drawn
   * @param endTime   the end time of the event that is being drawn
   * @param g2d       a Graphics2D object that has already been transformed to draw take logical
   *                  coordinates and give physical coordinates.
   */
  private void drawDay(int day, WeekTime startTime, WeekTime endTime, Graphics2D g2d) {
    // the day decides where the event will be drawn horizontally. If the day is sunday (day = 1),
    // the X value should be 0. If the day is monday (day = 2), the X value should be 100, or
    // LOGICAL_WIDTH/7
    int startX = (day - 1) * LOGICAL_WIDTH / 7;
    int drawWidth = LOGICAL_WIDTH / 7;
    int startY;
    int endY;

    // If the day that is being drawn is the start day, draw the beginning of the rectangle at
    // the startTime. Otherwise, the event spans from the previous day and starts at the top of
    // the page.
    if (day == startTime.getWeekDay().getDayOrder()) {
      startY = getYFromTime(startTime.getHour(), startTime.getMinute());
    } else {
      startY = 0;
    }

    // If the day that is being drawn is the end day, draw the end of the rectangle at
    // the endTime. Otherwise, the event spans to the next day and ends at the bottom of the page.
    if (day == endTime.getWeekDay().getDayOrder()) {
      endY = getYFromTime(endTime.getHour(), endTime.getMinute());
    } else {
      endY = LOGICAL_HEIGHT;
    }

    // Draw the rectangle corresponding to the correct day and time
    g2d.setColor(Color.decode("#f0706e"));
    g2d.fillRect(startX, startY, drawWidth, endY - startY);
  }

  /**
   * Draws the empty schedule grid with lines demarcating the day and the hour.
   *
   * @param g2d a Graphics2D object that has already been transformed to draw take logical
   *            coordinates and give physical coordinates.
   */
  private void drawScheduleGrid(Graphics2D g2d) {
    // Draw as if we are in logical size.
    Dimension logicalSize = this.getPreferredLogicalSize();

    g2d.setColor(Color.BLACK);
    // set the width for thin lines
    g2d.setStroke(new BasicStroke(2));

    // Draw a horizontal line at each specified y value. values are hard coded to our logical size.
    // These represent hour marks
    for (int y = 0; y <= logicalSize.height; y = y + logicalSize.height / 24) {
      g2d.drawLine(0, y, logicalSize.width, y);
    }

    // Draw lines to represent different days
    for (int x = 0; x <= logicalSize.height; x = x + logicalSize.width / 7) {
      g2d.drawLine(x, 0, x, logicalSize.height);
    }

    // set the width for thick lines
    g2d.setStroke(new BasicStroke(8));

    // Draw thick horizontal lines every four hours
    for (int y = 0; y <= logicalSize.height; y = y + logicalSize.height / 6) {
      g2d.drawLine(0, y, logicalSize.width, y);
    }
  }

  /**
   * Returns the dimensions of the logical size that make translating between time and pixel
   * coordinates easy.
   *
   * @return The preferred logical size for drawing output and reading input.
   */
  private Dimension getPreferredLogicalSize() {
    return new Dimension(LOGICAL_WIDTH, LOGICAL_HEIGHT);
  }

  /**
   * Translates the scale from logical coordinates to physical coordinates. This transformation
   * keeps the orientation of the axes and the location of the origin, but scales the
   * basis vectors.
   *
   * @return The scaled transformation
   */
  private AffineTransform transformLogicalToPhysical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(getWidth() / preferred.getWidth(), getHeight() / preferred.getHeight());
    return ret;
  }

  /**
   * Translates the scale from physical coordinates to logical coordinates. This transformation
   * keeps the orientation of the axes and the location of the origin, but scales the
   * basis vectors.
   *
   * @return The scaled transformation
   */
  private AffineTransform transformPhysicalToLogical() {
    AffineTransform ret = new AffineTransform();
    Dimension preferred = getPreferredLogicalSize();
    ret.scale(preferred.getWidth() / getWidth(), preferred.getHeight() / getHeight());
    return ret;
  }

  /**
   * Gets a WeekTime from a given pixel position. The given coordinates should be within the logical
   * dimensions.
   *
   * @param logicalX the logical x pixel position
   * @param logicalY the logical y pixel position
   * @return the corresponding pixel coordinate
   */
  private WeekTime getTimeFromLoc(int logicalX, int logicalY) {
    Dimension logicalDim = this.getPreferredLogicalSize();

    // Throw an error if the pixel position is outside the bounds of our dimensions, as these
    // locations will translate to invalid times
    boolean invalidLocation =
            logicalX < 0 || logicalY < 0 ||
                    logicalX >= logicalDim.width || logicalY >= logicalDim.height;

    if (invalidLocation) {
      throw new IllegalArgumentException("Given coordinates must be within the panel.");
    }

    // Translate coordinates to a time

    // Horizontal location translates to day.
    // Example calculation: logical x is 650, which maps to sunday. This is sixth in the
    // WeekDay enumeration. We can do floor (650/100), which is logicalX / (logical_width/7)
    WeekDay day = WeekDay.values()[logicalX / (LOGICAL_WIDTH / 7)];

    // Vertical position translates to an hour and minute. yPix = 60 * hr + minute.
    // divide by 60 because to isolate the hour because we use integer division and minute will
    // get cancelled out. Take mod of 60 to isolate the minute.
    int hour = logicalY / 60;
    int minute = logicalY % 60;

    return new LocalWeekTime(day, hour, minute);
  }

  /**
   * Gets a corresponding Y pixel position in logical coordinates from a given hour and minute.
   *
   * @param hour   The hour in military time from 0 to 23
   * @param minute The minute from 0 to 59
   * @return the corresponding pixel coordinate
   */
  private int getYFromTime(int hour, int minute) {
    // Our logicalY height is 24*60. This gives a ratio of 1 pixel : 1 minute.
    // Thus, The y value of a given time will be 60*hour + minute.
    return hour * LOGICAL_HEIGHT / 24 + minute * LOGICAL_HEIGHT / 24 / 60;
  }

  /**
   * A private class that defines what happens when a user clicks on the panel. When the mouse
   * is released, the pixel coordinate should be translated into the corresponding WeekTime.
   * That WeekTime can then be used to create a new Frame to display the details of the event
   * at that time, if one exists. In the future, a similar implementation will delegate to the
   * features class.
   */
  private class MouseEventsListener extends MouseInputAdapter {
    @Override
    public void mouseReleased(MouseEvent e) {
      // Ignore request if no schedule is stored
      if (currentSchedule == null) {
        return;
      }

      // This point is measured in actual physical pixels
      Point physicalP = e.getPoint();
      // Transform the point to logical pixels
      Point2D logicalP = transformPhysicalToLogical().transform(physicalP, null);
      // Using wrapper class so the doubles returned by the affine transformation can be converted
      // to int without casting
      Double logicalX = logicalP.getX();
      Double logicalY = logicalP.getY();

      // Make sure P is in logical bounds, and then get the requested time. Making sure P
      // is within bounds prevents errors from WeekTime construction, as well as making sure
      // we can use the double to int conversion without losing information by converting from
      // double to int.
      Dimension logicalDim = getPreferredLogicalSize();
      if (logicalX < 0 || logicalY < 0 ||
              logicalX >= logicalDim.width || logicalY >= logicalDim.height) {
        return;
      }

      // Convert pixel to WeekTime
      WeekTime requestedTime = getTimeFromLoc(logicalX.intValue(), logicalY.intValue());
      features.displayEventAt(requestedTime);
    }
  }


}
