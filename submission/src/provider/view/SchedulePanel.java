package provider.view;

import provider.controller.Features;
import provider.model.ReadOnlySchedule;

/**
 * A panel for displaying a schedule, which has the exclusive responsibility of drawing the
 * schedule. This event also takes in a set of features to enable interactivity with the schedule,
 * such as viewing any of the displayed events.
 */
public interface SchedulePanel {
  /**
   * Displays the given schedule.
   * @param schedule A schedule to display.
   * @throws IllegalArgumentException if the schedule is null
   */
  void displaySchedule(ReadOnlySchedule schedule);

  /**
   * Gives a set of features to the schedule panel. This enables interaction with the panel,
   * such as selecting an event on the schedule to view.
   *
   * @param features features that a controller should be able to execute.
   * @throws IllegalArgumentException if the features are null
   */
  void addFeatures(Features features);
}
