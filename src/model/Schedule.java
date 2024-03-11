package model;

import java.util.List;

/**
 *
 */
public class Schedule implements ScheduleSystem{
  final private List<Event> events;

  public Schedule(List<Event> events) {
    this.events = events;
  }

  // add event
  /**
   * Adds the given event to the current schedule of events.
   *
   * @param e Event to be added.
   */
  @Override
  public void addEvent(Event e) {
    this.events.add(e);
  }

  // modify event
  /**
   * ???
   *
   * @param e Event to be modified.
   */
  @Override
  public void modifyEvent(Event e) {

  }

  // remove event
  /**
   * Removes the given event from the current schedule of events if it exists.
   * If given event is not in schedule throw IllegalArgumentException.
   *
   * @param e Event to be removed.
   */
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
