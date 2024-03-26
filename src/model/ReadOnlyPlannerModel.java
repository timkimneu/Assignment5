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
   *
   *
   * @return
   */
  List<SchedulePlanner> schedules();

  /**
   *
   *
   * @param name
   * @return
   */
  List<Event> events(String name);

  /**
   *
   * @return
   */
  List<String> users();
}
