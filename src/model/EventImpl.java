package model;

import java.util.List;
import java.util.Objects;

/**
 * Represents a social gathering that has a name, a specified starting and ending time,
 * a location, and a list of attendees along with the host of the event.
 */
public class EventImpl implements IEvent {
  private final String name;
  private final TimeImpl time;
  private final LocationImpl location;
  private final List<UserImpl> users;
  private UserImpl host;

  /**
   * Represents a social gathering that has a name, a specified starting and ending time,
   * a location, and a list of attendees along with the host of the event. Parameters
   * include the name of the event, and references to the time, location, and user classes
   * in order to store their corresponding values.
   *
   * @param name  String that represents the name of the event
   * @param time  Includes the start time and end time of an event
   * @param loc   Has information of whether the place and whether it is online or not
   * @param users Includes a list of users that are a part of the event
   */
  public EventImpl(String name, TimeImpl time, LocationImpl loc, List<UserImpl> users) {
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
   * Observer in order to observe the name field.
   *
   * @return a String that has the name of the event
   */
  @Override
  public String name() {
    return this.name;
  }

  /**
   * Observes the Time of the event, which includes the starting and ending day and the starting
   * and ending time of the event.
   *
   * @return Time object that represents beginning and ending time of an event.
   */
  @Override
  public TimeImpl time() {
    return this.time;
  }

  /**
   * Observer in order to observe the name location. Includes information
   * of the place and whether the event is online or not.
   *
   * @return instance of Location field
   */
  @Override
  public LocationImpl location() {
    return this.location;
  }

  /**
   * Observer in order to observe the list of users. Includes information
   * of the place and whether the event is online or not.
   *
   * @return instance of list of users field
   */
  @Override
  public List<UserImpl> users() {
    return this.users;
  }

  /**
   * Observer in order to observe the host user.
   *
   * @return User that represents the host
   */
  @Override
  public UserImpl host() {
    return this.host;
  }

  /**
   * Method that returns a boolean to determine if user is the host.
   *
   * @param user User that may represent the host
   * @return
   */
  @Override
  public boolean isHost(UserImpl user) {
    return this.host.equals(user);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof EventImpl)) {
      return false;
    }
    EventImpl event = (EventImpl) other;
    return this.name.equals(event.name) && this.time.equals(event.time) &&
            this.location.equals(event.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.time, this.location);
  }
}
