package model;

import java.util.List;

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
