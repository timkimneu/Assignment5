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
    if (this.events.contains(e)) {
      throw new IllegalArgumentException("Schedule already contains given event!");
    } else {
      this.events.add(e);
      try {
        this.checkAnyOverlap();
      } catch (IllegalStateException ex) {
        this.removeEvent(e);
        throw new IllegalArgumentException("Added event overlaps with an existing event!" +
            " Removing new event.");
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
    if (!this.events.contains(e)) {
      throw new IllegalArgumentException("Event not found!");
    } else {
      for (Event event : this.events) {
        if (e.equals(event)) {
          this.events.remove(e);
        }
      }
    }
  }

  private void checkAnyOverlap() throws IllegalStateException {
    for (Event e1 : this.events) {
      for (Event e2 : this.events) {
        checkForOverlap(e1, e2);
      }
    }
  }

  private void checkForOverlap(Event e1, Event e2) throws IllegalStateException {

  }
}
