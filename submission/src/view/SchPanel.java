package view;

import controller.ScheduleSystem;

/**
 * Creates an interface in order to visualize the users'
 * schedules. Includes a method that takes in the user
 * that is chosen, and will draw red blocks according
 * to the events in their schedules.
 */
public interface SchPanel {
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
  void addListener(ScheduleSystem controller);
}
