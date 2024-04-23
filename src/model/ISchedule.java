package model;

import java.util.List;

/**
 * Represents a list of events for a single user with an identification number.
 */
public interface ISchedule<T> {

  /**
   *
   * @return
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
   * @param startDay
   * @param startTime
   * @param endDay
   * @param endTime
   * @param loc
   * @param users
   * @param eventName
   */
  void addEvent(int startDay, String startTime, int endDay, String endTime, LocationImpl loc,
                List<UserImpl> users, String eventName);

  /**
   * Removes the given event from the current schedule of events if it exists.
   * If given event is not in schedule throw IllegalArgumentException.
   *
   * @param startDay
   * @param startTime
   * @param endDay
   * @param endTime
   * @param loc
   * @param users
   * @param eventName
   */
  void removeEvent(int startDay, String startTime, int endDay, String endTime,
                   LocationImpl loc, List<UserImpl> users, String eventName);
}
