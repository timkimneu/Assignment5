package model;

import java.util.List;

/**
 * Represents a part of the full planner or schedule system that allows for interaction between
 * different individual's schedules and the adding, modifying, and removing events from schedules
 * within the system. Also retrieves the full list of schedules currently contained within the
 * planner system and can also observe the list of events for a specific user if the user exists,
 * otherwise throws an error.
 */
public interface IReadOnlyPlannerModel<T> {

  /**
   * Observes all the schedules contained in the planner.
   *
   * @return a list of all the schedules in the planner.
   */
  List<ISchedule<T>> schedules();

  /**
   * Observes all the events for a provided user's id. Throws an IllegalArgumentException if
   * there is no such schedule with the given id.
   *
   * @param name Name or id of the owner of a schedule in the planner system.
   * @return a list of Event objects that belong to the given user's schedule.
   */
  List<IEvent<T>> events(String name);

  /**
   * Returns the names of all the users who have a schedule in the planner system.
   *
   * @return a list of String objects representing all of the users in the planner system.
   */
  List<String> users();
}
