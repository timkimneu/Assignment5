package model;

import java.util.List;

/**
 *
 */
public class Schedule implements ScheduleSystem {
  final private List<Event> events;

  public Schedule(List<Event> events) {
    this.events = events;
  }

  // add event
  @Override
  public void addEvent(Event e) {
    this.events.add(e);
  }

  // modify event
  @Override
  public void modifyEvent(Event e) {

  }

  // remove event
  @Override
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
}
