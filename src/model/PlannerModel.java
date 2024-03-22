package model;

public interface PlannerModel extends ReadOnlyPlannerModel {
  /**
   *
   */
  void addEvent(Event event);

  /**
   *
   */
  void modifyEvent(Event event, Event newEvent);

  /**
   *
   */
  void removeEvent(Event event);
}
