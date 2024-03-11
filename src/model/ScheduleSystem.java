package model;

public interface ScheduleSystem {
  /**
   *
   */
  void addEvent(Event e);

  /**
   *
   */
  void modifyEvent(Event e);

  /**
   *
   */
  void removeEvent(Event e);
}
