package model;

import java.util.List;

/**
 * Represents a list of events for a single user with an identification number.
 */
public class SchedulePlanner implements ISchedule {
  final private List<EventImpl> events;
  final private String id;

  /**
   * Instantiates the schedule, which includes a list of events, as well
   * as a unique id identifier.
   *
   * @param events List of events that will form a schedule
   * @param id     String to show the unique id of the event
   */
  public SchedulePlanner(List<EventImpl> events, String id) {
    this.events = events;
    this.id = id;
    this.checkAnyOverlap(this.events());
  }

  @Override
  public String scheduleID() {
    return this.id;
  }

  @Override
  public List<EventImpl> events() {
    return this.events;
  }

  @Override
  public void addEvent(EventImpl e) {
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
  public void removeEvent(EventImpl e) {
    if (!this.events().contains(e)) {
      throw new IllegalArgumentException("Event to be removed not found!");
    } else {
      for (EventImpl event : this.events()) {
        if (e.equals(event)) {
          this.events().remove(e);
          break;
        }
      }
    }
  }

  // method to check if the events have any overlap
  private void checkAnyOverlap(List<EventImpl> events) throws IllegalStateException {
    for (EventImpl e1 : events) {
      for (EventImpl e2 : events) {
        if (!e1.equals(e2)) {
          checkForOverlap(e1, e2);
        }
      }
    }
  }

  // checks to see if two given events overlap each other
  private void checkForOverlap(EventImpl e1, EventImpl e2) throws IllegalStateException {
    TimeImpl e1Time = e1.time();
    TimeImpl e2Time = e2.time();
    if (e1Time.anyOverlap(e2Time)) {
      throw new IllegalStateException("Schedule contains overlapping events!" + e1.time().toString()
              + e2.time().toString());
    }
  }
}
