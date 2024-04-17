package model;

import provider.model.Event;
import provider.model.EventBuilder;
import provider.model.User;
import provider.model.EventTime;
import provider.model.WeekTime;

import java.util.ArrayList;
import java.util.List;

public class EventBuilderAdapter implements EventBuilder {
  private String name;
  private boolean online;
  private String place;
  private EventTime eventTime;
  private WeekTime startTime;
  private WeekTime endTime;
  private UserImpl host;
  private List<UserImpl> attendees;

  @Override
  public EventBuilder copyEvent(Event event) {
    // ignore, isn't called
    return this;
  }

  @Override
  public EventBuilder setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public EventBuilder setLocation(String location) {
    this.place = location;
    return this;
  }

  @Override
  public EventBuilder setOnline(boolean isOnline) {
    this.online = isOnline;
    return this;
  }

  @Override
  public User getHost() {
    // ignore isn't used
    return null;
  }

  @Override
  public EventBuilder setHost(User host) {
    this.host = new UserImpl(host.toString());
    return this;
  }

  @Override
  public EventBuilder setHost(String host) {
    this.host = new UserImpl(host);
    return this;
  }

  @Override
  public EventBuilder addAttendees(List<User> attendees) {
    List<UserImpl> tbaUsers = new ArrayList<>();
    for (User u : attendees) {
      UserImpl newUser = new UserImpl(u.toString());
    }
    return this;
  }

  @Override
  public List<User> getAttendees() {
    return null;
  }

  @Override
  public EventBuilder setAttendees(List<User> attendees) {
    return this;
  }

  @Override
  public EventBuilder setEventTime(EventTime time) {
    return this;
  }

  @Override
  public EventBuilder setStartTime(WeekTime time) {
    return this;
  }

  @Override
  public EventBuilder setEndTime(WeekTime time) {
    return this;
  }

  @Override
  public EventBuilder reset() {
    return new EventBuilderAdapter();
  }

  @Override
  public Event buildEvent() {
    return new EventAdapter(new EventImpl(name, new ITimeAdapter(eventTime),
        new LocationImpl(online, place), attendees));
  }
}
