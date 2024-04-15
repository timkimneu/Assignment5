package provider.model;

import java.util.List;

/**
 * A ReadOnly version of the schedule interface that only defines observations.
 * Prevents unintended mutation.
 */
public interface ReadOnlySchedule {
  /**
   * Returns the user who owns the schedule of interest.
   * @return the user who owns this schedule.
   */
  User getUser();

  /**
   * Returns a list containing all events within the schedule sorted in chronological order.
   * Changing the returned list of events should not change the schedule's internal representation
   * in any way.
   *
   * @return a list containing all events within the schedule.
   */
  List<Event> getEvents();

  /**
   * Returns true if the schedule has an event that overlaps with the current event. An event
   * overlaps with another as is defined by the EventTime class.
   * This is a useful method for determining whether an event can be created at a given time.
   *
   * @param time a range of time at which the schedule should assess whether it has an event.
   * @return true if the schedule has an event that overlaps with the given time.
   */
  boolean hasEventAtTime(EventTime time);

  /**
   * Gets the events that overlap with the range of time defined by the given EventTime. This
   * observation is useful when having an event at a specific time that overlaps is not important,
   * such as when we are replacing an old event with a new event.
   *
   * @param time an EventTime with a start and end time.
   * @return a list containing all events whose end time or start time is contained within
   *         the range of time defined by the given EventTime.
   */
  List<Event> getEventsAtTime(EventTime time);

  /**
   * Returns the chronologically first event that contains the given WeekTime.
   * This enables a view to easily convert a click on the view to the time, and then retrieve the
   * corresponding event from a schedule.
   *
   * @param time the time to check for an event at
   * @return An event at the given time
   * @throws IllegalArgumentException if there is no Event at the given time
   */
  Event getEventAtTime(WeekTime time);
}
