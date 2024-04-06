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
   *
   * @param user
   */
  void drawDates(String user);

  void addListener(ScheduleSystem controller);
}
