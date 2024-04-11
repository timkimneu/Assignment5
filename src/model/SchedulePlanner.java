package model;

import java.util.List;

/**
 * Represents a list of events for a single user with an identification number.
 */
public class SchedulePlanner implements Schedule {
  final private List<Event> events;
  final private String id;

  /**
   * Instantiates the schedule, which includes a list of events, as well
   * as a unique id identifier.
   *
   * @param events List of events that will form a schedule
   * @param id String to show the unique id of the event
   */
  public SchedulePlanner(List<Event> events, String id) {
    this.events = events;
    this.id = id;
    this.checkAnyOverlap(this.events());
  }

  @Override
  public String scheduleID() {
    return this.id;
  }

  @Override
  public List<Event> events() {
    return this.events;
  }

  @Override
  public void addEvent(Event e) {
    if (this.events().contains(e)) {
      throw new IllegalArgumentException("Schedule already contains given event!");
    } else {
      this.events().add(e);
      try {
        this.checkAnyOverlap(this.events());
      } catch (IllegalStateException ex) {
        this.removeEvent(e);
        throw new IllegalArgumentException("Added event overlaps with an existing event!" +
            " Removing added event.");
      }
    }
  }

  @Override
  public void removeEvent(Event e) {
    if (!this.events().contains(e)) {
      throw new IllegalArgumentException("Event to be removed not found!");
    } else {
      for (Event event : this.events()) {
        if (e.equals(event)) {
          this.events().remove(e);
          break;
        }
      }
    }
  }

  // method to check if the events have any overlap
  private void checkAnyOverlap(List<Event> events) throws IllegalStateException {
    for (Event e1 : events) {
      for (Event e2 : events) {
        if (!e1.equals(e2)) {
          checkForOverlap(e1, e2);
        }
      }
    }
  }

  // checks to see if two given events overlap each other
  private void checkForOverlap(Event e1, Event e2) throws IllegalStateException {
    Time e1Time = e1.time();
    Time e2Time = e2.time();
    if (e1Time.anyOverlap(e2Time)) {
      throw new IllegalStateException("Schedule contains overlapping events!" + e1.time().toString() + e2.time().toString());
    }
  }
}
