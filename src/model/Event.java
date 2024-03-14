package model;

import java.util.List;

/**
 * Represents a social gathering that has a name, a specified starting and ending time,
 * a location, and a list of attendees along with the host of the event.
 */
public class Event {
  private final String name;
  private final Time time;
  private final Location location;
  private final List<User> users;
  private User host;

  /**
   *
   * @param name
   * @param time
   * @param loc
   * @param users
   */
  public Event(String name, Time time, Location loc, List<User> users) {
    this.name = name;
    this.time = time;
    this.location = loc;
    this.users = users;
    if (this.users.isEmpty()) {
      throw new IllegalArgumentException("Must have 1 or more people attending event!");
    } else {
      this.host = this.users.get(0);
    }
  }

  /**
   * Observes the Time of the event, which includes the starting and ending day and the starting
   * and ending time of the event.
   *
   * @return Time object that represents beginning and ending time of an event.
   */
  public Time time() {
    return this.time;
  }
}
