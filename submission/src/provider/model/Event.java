package provider.model;

import java.util.List;

/**
 * An interface defining the necessary observations for an event.
 *
 * <p>Design Decisions: An implementation that only implements this interface should be immutable.
 * This prevents any issues that might arise from mutation, and makes reading in events from XML
 * easier in the CentralSystem at the cost of making event modifications slightly more involved.
 *
 * <p>This class clearly belongs in the model, as it is the base data unit that the rest of the
 * model acts upon.
 */
public interface Event {

  /**
   * Returns the name of the event a string.
   *
   * <p>This is a necessary observation of an event.
   *
   * @return a string that represents the name of the event
   */
  String getName();

  /**
   * Returns a list of unique users representing all attendees of the event with the host listed
   * first.
   *
   * <p>Seeing attendees is a necessary observation of an event.
   * Having two methods for getting attendees, one that gets everyone including the host, and one
   * where the host is not included is a quality of life feature.
   *
   * @return a list of users representing all attendees of the event.
   */
  List<User> getAllAttendees();

  /**
   * Returns a list of unique users representing all attendees of the event without the host.
   *
   * @return a list of users representing all attendees of the event. without the host
   */
  List<User> getAttendeesNoHost();

  /**
   * Returns a string representing the location of the event.
   *
   * @return a string representing the location of the event.
   */
  String getLocation();

  /**
   * True if the event is online, else false.
   *
   * @return true if the event is online, else false
   */
  boolean isOnline();

  /**
   * Returns a user that is the host of this event.
   *
   * @return a user that is the host of this event
   */
  User getHost();

  /**
   * Returns an event time representing that duration of time that this event spans. The EventTime
   * contains the start time (containing the day, hour and minute), and the end date (containing
   * the day hour and minute).
   *
   * <p>Having a custom interface represent the duration of an event seemed like a prudent idea
   * in case specifications relating to time changed (such as if timezones or  dates needed to be
   * implemented).
   *
   * @return the time of the event
   */
  EventTime getEventTime();

  /**
   * Returns true if the given event is the same as the current event. Two events are the same if
   * their names, hosts, attendees, locations, online status, start times, and end times
   * are all the same.
   *
   * <p>This method is important as it forces any implementation to consider event equality. This is
   * necessary for identifying events for removal or modification, as well as preventing issues
   * reading in XML data.
   *
   * @param other an event that should be compared to this event for equality
   * @return true if the events are the same, as defined above, else false.
   * @throws IllegalArgumentException if the other event is null
   */
  boolean sameEvent(Event other);
}
