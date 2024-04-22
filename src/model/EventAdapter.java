package model;

import provider.model.Event;
import provider.model.User;
import provider.model.EventTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the events that are represented by colored boxes on the view. Implements
 * methods that observe information about an event such as name, location, etc. Implements
 * the provider's Event interface, and composes of our EventImpl implementation.
 */
public class EventAdapter implements Event {
  private final IEvent<DaysOfTheWeek> event;

  /**
   * Represents the events that are represented by colored boxes on the view. Implements
   * methods that observe information about an event such as name, location, etc.
   *
   * @param event Sets the event to be our event implementation.
   */
  public EventAdapter(IEvent<DaysOfTheWeek> event) {
    this.event = event;
  }

  @Override
  public String getName() {
    return event.name();
  }

  @Override
  public List<User> getAllAttendees() {
    List<User> allUsers = new ArrayList<>();
    for (UserImpl ui : event.users()) {
      User user = new UserAdapter(ui);
      allUsers.add(user);
    }
    return allUsers;
  }

  @Override
  public List<User> getAttendeesNoHost() {
    List<User> noHost = getAllAttendees();
    noHost.remove(getHost());
    return noHost;
  }

  @Override
  public String getLocation() {
    return event.location().place();
  }

  @Override
  public boolean isOnline() {
    return event.location().online();
  }

  @Override
  public User getHost() {
    return new UserAdapter(event.host());
  }

  @Override
  public EventTime getEventTime() {
    return new TimeAdapter(event.time());
  }

  @Override
  public boolean sameEvent(Event other) {
    return this.equals(other);
  }
}
