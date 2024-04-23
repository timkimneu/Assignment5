package strategy;

import java.util.List;

import model.LocationImpl;
import model.UserImpl;

/**
 * Makes an event and schedules the event according to the rules of an implemented strategy.
 * Adds the given event to all users in the planner system provided by the list of users
 * pertaining to the given event. In other words, adds this event to a user's schedule if they
 * are in the list of attendees in a new event. Throws an error if there exists any overlap
 * with the new event with any existing event of any attendee. Throws an error if the given
 * event is already scheduled in any attendee's schedule.
 *
 * @param <T> generic type resembling the calendar type (i.e. which day the calendar starts on).
 */
public interface SchedulingStrategy<T> {
  /**
   * Makes an event with the provided parameters provided, and schedules the event.
   * Adds the given event to all users in the planner system provided by the list of users
   * pertaining to the given event. In other words, adds this event to a user's schedule if they
   * are in the list of attendees in a new event. Throws an error if there exists any overlap
   * with the new event with any existing event of any attendee. Throws an error if the given
   * event is already scheduled in any attendee's schedule.
   *
   * @param name     String that represents the event name.
   * @param location Location class that represents the place and whether event is online.
   * @param duration How long the event is in minutes.
   * @param users    List of users that are attending the event.
   */
  void scheduleEvent(String name, LocationImpl location, int duration, List<UserImpl> users);
}
