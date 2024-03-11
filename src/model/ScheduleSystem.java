package model;

/**
 * Represents the model of the Schedule system or collection of events over a calendar. Individual
 * events can be added, removed, or "modified".
 */
public interface ScheduleSystem {
  /**
   * Adds the given event to the current schedule of events.
   *
   * @param e Event to be added.
   */
  void addEvent(Event e);

  /**
   * Replaces the old event with the new event by removing the old event and adding the new event
   * to the event list.
   *
   * @param oldEvent event to be removed
   * @param newEvent event to be added in place of the old event
   */
  void modifyEvent(Event oldEvent, Event newEvent);

  /**
   * Removes the given event from the current schedule of events if it exists.
   * If given event is not in schedule throw IllegalArgumentException.
   *
   * @param e Event to be removed.
   */
  void removeEvent(Event e);

  /**
   *
   *
   * @param filePath
   */
  void readXML(String filePath);

  /**
   *
   * @param filePath
   */
  void writeXML(String filePath);
}
