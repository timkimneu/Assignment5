package model;

import java.util.List;

/**
 * Represents a list of events for a single user with an identification number.
 */
public interface ISchedule<T> {

  /**
   * Returns the index of Monday to allow for proper alignment in the WorkTime strategy
   * between schedules starting on different days (e.g. Sunday vs Saturday).
   *
   * @return Returns the index of Monday for this calendar type.
   */
  int getFirstDay();

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
  List<IEvent<T>> events();

  /**
   * Adds the given event to the current schedule of events. Checks if the new added event
   * overlaps with any existing event in the current list of events.
   *
   * @param startDay Index of the day the event begins on.
   * @param startTime String time resembling the starting time of the event.
   * @param endDay Index of the day the event will end on.
   * @param endTime String time resembling the ending time of the event.
   * @param loc Location of the event.
   * @param users List of attendees of the event.
   * @param eventName Name of the event.
   */
  void addEvent(int startDay, String startTime, int endDay, String endTime, LocationImpl loc,
                List<UserImpl> users, String eventName, UserImpl host);

  /**
   * Removes the given event from the current schedule of events if it exists.
   * If given event is not in schedule throw IllegalArgumentException.
   *
   * @param startDay Index of the day the event begins on.
   * @param startTime String time resembling the starting time of the event.
   * @param endDay Index of the day the event will end on.
   * @param endTime String time resembling the ending time of the event.
   * @param loc Location of the event.
   * @param users List of attendees of the event.
   * @param eventName Name of the event.
   */
  void removeEvent(int startDay, String startTime, int endDay, String endTime,
                   LocationImpl loc, List<UserImpl> users, String eventName);
}
