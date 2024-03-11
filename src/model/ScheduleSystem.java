package model;

import java.util.List;

public interface ScheduleSystem {
  /**
   * Adds the given event to the current schedule of events.
   *
   * @param e Event to be added.
   */
  void addEvent(Event e);

  /**
   *
   *
   * @param oldEvent
   * @param newEvent
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
   * @param filePath
   */
  void readXML(String filePath);

  /**
   *
   * @param filePath
   */
  void writeXML(String filePath);
}
