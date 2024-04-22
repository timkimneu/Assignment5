package provider.model;

/**
 * An interface that defines the behavior for an event that spans a single week. The start and
 * end times should not be the same.
 *
 * <p>Design Decisions: This class was designed in order to represent the start and end time of an
 * Event. The useful feature of this class is that it defines behavior for determining whether
 * an EventTime overlaps with another EventTime, a crucial behavior for a schedule system. By using
 * WeekTimes, an EventTime automatically has the property that no event can last longer than 6 days
 * 23 hours, and 59 minutes if the start and end dates are not equal.
 */
public interface EventTime {

  /**
   * Gets the start time of an event as a WeekTime.
   *
   * @return the start time of an event with day, minute, and hour granularity.
   */
  WeekTime getStartTime();

  /**
   * Gets the end time of an event as a WeekTime.
   *
   * @return the end time of an event with day, minute, and hour granularity.
   */
  WeekTime getEndTime();

  /**
   * Determines if the other event overlaps with this event.
   *
   * @return true if the events overlap, else false.
   */
  boolean overlapsWith(EventTime other);

  /**
   * Determines whether the given time is contained within the range of this event time.
   * This means the start is before or equal and the ending is after or equal to the WeekTime.
   * This method allows a user clicking on a view to translate a time to an event.
   *
   * @param time A given WeekTIme
   * @return true if the EventTime contains the weekTime
   */
  boolean contains(WeekTime time);
}
