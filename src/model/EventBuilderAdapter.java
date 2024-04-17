package model;

import provider.model.Event;
import provider.model.EventBuilder;
import provider.model.User;
import provider.model.EventTime;
import provider.model.WeekTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the builder method from the provider code that will build an event, with
 * all the given information. Represents the events that are represented by colored boxes
 * on the view. Implements methods that observe information about an event such as name,
 * location, etc. Implements the provider's Event interface, and composes of our EventImpl
 * implementation.
 */
public class EventBuilderAdapter implements EventBuilder {
  private String name;
  private boolean online;
  private String place;
  private EventTime eventTime;
  private WeekTime startTime;
  private WeekTime endTime;
  private User host;
  private List<User> attendees = new ArrayList<>();

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
    return this.host;
  }

  @Override
  public EventBuilder setHost(User host) {
    this.host = host;
    return this;
  }

  @Override
  public EventBuilder setHost(String host) {
    this.host = new UserAdapter(new UserImpl(host));
    return this;
  }

  @Override
  public EventBuilder addAttendees(List<User> attendees) {
    this.attendees.addAll(attendees);
    return this;
  }

  @Override
  public List<User> getAttendees() {
    return attendees;
  }

  @Override
  public EventBuilder setAttendees(List<User> attendees) {
    this.attendees = attendees;
    return this;
  }

  @Override
  public EventBuilder setEventTime(EventTime time) {
    this.eventTime = time;
    return this;
  }

  @Override
  public EventBuilder setStartTime(WeekTime time) {
    this.startTime = time;
    return this;
  }

  @Override
  public EventBuilder setEndTime(WeekTime time) {
    this.endTime = time;
    return this;
  }

  @Override
  public EventBuilder reset() {
    return new EventBuilderAdapter();
  }

  @Override
  public Event buildEvent() {
    List<UserImpl> allUsers = new ArrayList<>();
    for (User u : attendees) {
      UserImpl newUser = new UserImpl(u.toString());
      allUsers.add(newUser);
    }
    try {
      return new EventAdapter(new EventImpl(name, new ITimeAdapter(eventTime),
          new LocationImpl(online, place), allUsers));
    } catch (NullPointerException e) {
      // ignore
    }
    System.out.println("THISSS" + this.online);
    DaysOfTheWeek startDay = DaysOfTheWeek.valueOf(startTime.getWeekDay().toString().toUpperCase());
    DaysOfTheWeek endDay = DaysOfTheWeek.valueOf(endTime.getWeekDay().toString().toUpperCase());
    String startingTime = startTime.getTime();
    String endingTime = endTime.getTime();
    TimeImpl timeImpl = new TimeImpl(startDay, startingTime, endDay, endingTime);
    return new EventAdapter(new EventImpl(name, timeImpl,
        new LocationImpl(online, place), allUsers));
  }
}
