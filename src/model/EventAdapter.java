package model;

import provider.model.Event;
import provider.model.User;
import provider.model.EventTime;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter implements IEvent {
  private final Event event;

  public EventAdapter(Event event) {
    this.event = event;
  }

  @Override
  public String name() {
    return event.getName();
  }

  @Override
  public TimeImpl time() {
    EventTime eventTime = event.getEventTime();
    TimeAdapter timeAdapter = new TimeAdapter(eventTime);
    DaysOfTheWeek startDay = timeAdapter.startDay();
    DaysOfTheWeek endDay = timeAdapter.endDay();
    String startTime = timeAdapter.startTime();
    String endTime = timeAdapter.endTime();
    return new TimeImpl(startDay, startTime, endDay, endTime);
  }

  @Override
  public LocationImpl location() {
    return new LocationImpl(event.isOnline(), event.getLocation());
  }

  @Override
  public List<UserImpl> users() {
    List<UserImpl> adaptedUsers = new ArrayList<>();
    for (User u : event.getAllAttendees()) {
      UserImpl userImpl = new UserImpl(u.getUsername());
      adaptedUsers.add(userImpl);
    }
    return adaptedUsers;
  }

  @Override
  public UserImpl host() {
    return new UserImpl(event.getHost().toString());
  }

  @Override
  public boolean isHost(UserImpl user) {
    return host().equals(user);
  }
}
