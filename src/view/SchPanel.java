package view;

import controller.ScheduleSystem;

import java.awt.Graphics2D;

/**
 * Creates an interface in order to visualize the users'
 * schedules. Includes a method that takes in the user
 * that is chosen, and will draw red blocks according
 * to the events in their schedules.
 */
public interface SchPanel<T> {
  /**
   * Draws the current schedule state by filling each rectangle with a color to represent the
   * presence of an event.
   *
   * @param g2d Graphics object to enable drawing.
   */
  void drawScheduleState(Graphics2D g2d);

  /**
   * Draws the dates provided by the frame onto the panel.
   *
   * @param user Schedule designated by the user to draw the schedule of.
   */
  void drawDates(String user);

  /**
   * Adds controller to listen to the view to then communicate changes to model.
   *
   * @param controller Listener to allow for the handling of events and changes to model.
   */
  void addListener(ScheduleSystem<T> controller);
}
