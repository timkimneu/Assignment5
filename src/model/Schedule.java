package model;

import java.util.List;

/**
 * Represents a list of events for a single user with an identification number.
 */
public class Schedule {
  final private List<Event> events;
  final private String id;

  /**
   * Instantiates the schedule, which includes a list of events, as well
   * as a unique id identifier.
   *
   * @param events List of events that will form a schedule
   * @param id String to show the unique id of the event
   */
  public Schedule(List<Event> events, String id) {
    this.events = events;
    this.id = id;
    this.checkAnyOverlap();
  }

  /**
   * Observes the state of the id.
   *
   * @return a String that represents the id of the event.
   */
  public String scheduleID() {
    return this.id;
  }

  /**
   * Adds the given event to the current schedule of events. Checks if the new added event
   * overlaps with any existing event in the current list of events.
   *
   * @param e Event to be added.
   */
  public void addEvent(Event e) {
    if (this.events().contains(e)) {
      throw new IllegalArgumentException("Schedule already contains given event!");
    } else {
      this.events().add(e);
      try {
        this.checkAnyOverlap();
      } catch (IllegalStateException ex) {
        this.removeEvent(e);
        throw new IllegalArgumentException("Added event overlaps with an existing event!" +
            " Removing added event.");
      }
    }
  }

  /**
   * Removes the given event from the current schedule of events if it exists.
   * If given event is not in schedule throw IllegalArgumentException.
   *
   * @param e Event to be removed.
   */
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

  /**
   * Observer that lists all the events in this schedule in no particular order. Currently used
   * mostly for testing purposes.
   *
   * @return the list of events currently contained in this schedule.
   */
  public List<Event> events() {
    return this.events;
  }

  private void checkAnyOverlap() throws IllegalStateException {
    for (Event e1 : this.events()) {
      for (Event e2 : this.events()) {
        if (!e1.equals(e2)) {
          checkForOverlap(e1, e2);
        }
      }
    }
  }

  private void checkForOverlap(Event e1, Event e2) throws IllegalStateException {
    Time e1Time = e1.time();
    Time e2Time = e2.time();
    if (e1Time.anyOverlap(e2Time)) {
      throw new IllegalStateException("Schedule contains overlapping events!");
    }
  }
}
