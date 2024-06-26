package model;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a list of events for a single user with an identification number.
 */
public class SatSchedulePlanner implements ISchedule<SatDOTW> {
  final private List<IEvent<SatDOTW>> events;
  final private String id;
  private final List<String> daysOfTheWeek = Arrays.asList("Saturday", "Sunday", "Monday",
          "Tuesday", "Wednesday", "Thursday", "Friday");

  /**
   * Instantiates the schedule, which includes a list of events, as well
   * as a unique id identifier.
   *
   * @param events List of events that will form a schedule
   * @param id     String to show the unique id of the event
   */
  public SatSchedulePlanner(List<IEvent<SatDOTW>> events, String id) {
    this.events = events;
    this.id = id;
    this.checkAnyOverlap(this.events());
  }

  @Override
  public int getFirstDay() {
    return daysOfTheWeek.indexOf("Monday");
  }

  @Override
  public String scheduleID() {
    return this.id;
  }

  @Override
  public List<IEvent<SatDOTW>> events() {
    return this.events;
  }

  @Override
  public void addEvent(int startDay, String startTime, int endDay, String endTime,
                       LocationImpl loc, List<UserImpl> users, String eventName, UserImpl user) {
    SatTimeImpl newTime = new SatTimeImpl(SatDOTW.valueOf(daysOfTheWeek.get(startDay)
            .toUpperCase()), startTime, SatDOTW.valueOf(daysOfTheWeek.get(endDay)
            .toUpperCase()), endTime);
    SatEventImpl newEvent = new SatEventImpl(eventName, newTime, loc, users);
    if (this.events().contains(newEvent)) {
      throw new IllegalArgumentException("Schedule already contains given event!");
    } else {
      this.events().add(newEvent);
      try {
        this.checkAnyOverlap(this.events());
      } catch (IllegalStateException ex) {
        this.removeEvent(startDay, startTime, endDay, endTime, loc, users, eventName);
        throw new IllegalArgumentException("Added event overlaps with an existing event!" +
            " Removing added event.");
      }
    }
  }

  @Override
  public void removeEvent(int startDay, String startTime, int endDay, String endTime,
                          LocationImpl loc, List<UserImpl> users, String eventName) {
    SatTimeImpl newTime = new SatTimeImpl(SatDOTW.valueOf(daysOfTheWeek.get(startDay)
            .toUpperCase()), startTime, SatDOTW.valueOf(daysOfTheWeek.get(endDay)
            .toUpperCase()), endTime);
    SatEventImpl newEvent = new SatEventImpl(eventName, newTime, loc, users);
    if (!this.events().contains(newEvent)) {
      throw new IllegalArgumentException("Event to be removed not found!");
    } else {
      for (IEvent<SatDOTW> event : this.events()) {
        if (newEvent.equals(event)) {
          this.events().remove(newEvent);
          break;
        }
      }
    }
  }

  // method to check if the events have any overlap
  private void checkAnyOverlap(List<IEvent<SatDOTW>> events) throws IllegalStateException {
    for (IEvent<SatDOTW> e1 : events) {
      for (IEvent<SatDOTW> e2 : events) {
        if (!e1.equals(e2)) {
          checkForOverlap(e1, e2);
        }
      }
    }
  }

  // checks to see if two given events overlap each other
  private void checkForOverlap(IEvent<SatDOTW> e1, IEvent<SatDOTW> e2)
          throws IllegalStateException {
    ITime<SatDOTW> e1Time = e1.time();
    ITime<SatDOTW> e2Time = e2.time();
    if (e1Time.anyOverlap(e2Time)) {
      throw new IllegalStateException("Schedule contains overlapping events!"
              + e1.time().toString() + e2.time().toString());
    }
  }
}
