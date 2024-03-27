package model;

import java.util.List;

/**
 * Represents a part of the full planner or schedule system that allows for interaction between
 * different individual's schedules and the adding, modifying, and removing events from schedules
 * within the system. Also retrieves the full list of schedules currently contained within the
 * planner system and can also observe the list of events for a specific user if the user exists,
 * otherwise throws an error.
 */
public interface ReadOnlyPlannerModel {

  /**
   * Observes all the schedules contained in the planner.
   *
   * @return a list of all the schedules in the planner.
   */
  List<SchedulePlanner> schedules();

  /**
   * Observes all the events for a provided user's id. Throws an IllegalArgumentException if
   * there is no such schedule with the given id.
   *
   * @param name Name of
   * @return
   */
  List<Event> events(String name);

  /**
   *
   * @return
   */
  List<String> users();
}
