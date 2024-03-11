package model;

import java.util.List;

/**
 *
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
}
