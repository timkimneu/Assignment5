package model;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;

/**
 *
 */
public class Schedule {
  final private List<Event> events;
  final private String id;

  public Schedule(List<Event> events, String id) {
    this.events = events;
    this.id = id;
    this.checkAnyOverlap();
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
   * Replaces the old event with the new event by removing the old event and adding the new event
   * to the event list.
   *
   * @param oldEvent event to be removed
   * @param newEvent event to be added in place of the old event
   */
  public void modifyEvent(Event oldEvent, Event newEvent) {
    if (oldEvent.equals(newEvent)) {
      throw new IllegalArgumentException("Cannot replace old event with same event!");
    }
    this.removeEvent(oldEvent);
    this.addEvent(newEvent);
  }

  // remove event
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
