package model;

import java.util.List;

/**
 * Represents a list of events for a single user with an identification number.
 */
public interface Schedule {
  /**
   * Observes the state of the id.
   *
   * @return a String that represents the id of the event.
   */
  String scheduleID();

  /**
   * Observer that lists all the events in this schedule in no particular order. Currently used
   * mostly for testing purposes.
   *
   * @return the list of events currently contained in this schedule.
   */
  List<Event> events();

  /**
   * Adds the given event to the current schedule of events. Checks if the new added event
   * overlaps with any existing event in the current list of events.
   *
   * @param e Event to be added.
   */
  void addEvent(Event e);

  /**
   * Removes the given event from the current schedule of events if it exists.
   * If given event is not in schedule throw IllegalArgumentException.
   *
   * @param e Event to be removed.
   */
  void removeEvent(Event e);
}
