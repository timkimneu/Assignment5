package view;

import controller.ScheduleSystem;
import controller.ScheduleSystemController;

/**
 * Creates an interface to view the schedule in a formatted manner, with
 * each event indented under their corresponding day of the week. This
 * helps the user keep track of what events have been scheduled throughout
 * the week.
 */
public interface ScheduleSystemView {

  /**
   * Creates a Viewer to view the schedule in a formatted manner, with
   * each event indented under their corresponding day of the week. Displays
   * the schedule as a string textual view.
   *
   * @return String that represents the schedule format
   */
  String schedulesToString();

  /**
   * Sets the visibility of the component to true. Makes the component visible.
   */
  void makeVisible();

  /**
   * Hides the panel. Sets the visibility of the component to false.
   */
  void hidePanel();

  /**
   *
   * @param listener
   */
  void addListener(ScheduleSystem listener);

  void refresh();

  void action(ScheduleSystem system);
}
