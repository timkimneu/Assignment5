package view;

import controller.ScheduleSystem;
import model.EventImpl;
import model.ISchedule;
import model.SchedulePlanner;

/**
 * Creates an interface to view the schedule in a formatted manner, with
 * each event indented under their corresponding day of the week. This
 * helps the user keep track of what events have been scheduled throughout
 * the week.
 */
public interface ScheduleSystemView<T> {

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
   * Adds controller to view to communicate view with model.
   *
   * @param listener Controller to allow listening on view.
   */
  void addListener(ScheduleSystem<T> listener);

  /**
   * Repaints the view to update changes from the model.
   */
  void refresh();
}
