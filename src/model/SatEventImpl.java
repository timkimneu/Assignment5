package model;

import java.util.List;
import java.util.Objects;

/**
 * Represents a social gathering that has a name, a specified starting and ending time,
 * a location, and a list of attendees along with the host of the event.
 */
public class SatEventImpl implements IEvent<SatDOTW> {
  private final String name;
  private final ITime<SatDOTW> time;
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
  public SatEventImpl(String name, ITime<SatDOTW> time, LocationImpl loc, List<UserImpl> users) {
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

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public ITime<SatDOTW> time() {
    return this.time;
  }

  @Override
  public LocationImpl location() {
    return this.location;
  }

  @Override
  public List<UserImpl> users() {
    return this.users;
  }

  @Override
  public UserImpl host() {
    return this.host;
  }

  @Override
  public boolean isHost(String user) {
    return this.host.toString().equals(user);
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof SatEventImpl)) {
      return false;
    }
    SatEventImpl event = (SatEventImpl) other;
    return this.name.equals(event.name) && this.time.equals(event.time) &&
        this.location.equals(event.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.time, this.location);
  }
}
