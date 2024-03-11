package model;

public interface ScheduleSystem {
  /**
   * Adds the given event to the current schedule of events.
   *
   * @param e Event to be added.
   */
  void addEvent(Event e);

  /**
   * ???
   *
   * @param e Event to be modified.
   */
  void modifyEvent(Event e);

  /**
   * Removes the given event from the current schedule of events if it exists.
   * If given event is not in schedule throw IllegalArgumentException.
   *
   * @param e Event to be removed.
   */
  void removeEvent(Event e);
}
