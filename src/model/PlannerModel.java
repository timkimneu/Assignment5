package model;

import java.util.List;

public interface PlannerModel extends ReadOnlyPlannerModel {
  /**
   *
   * @return
   */
  public List<Schedule> schedules();

  /**
   *
   */
  public void addEvent(Event event);

  /**
   *
   */
  public void modifyEvent(Event event, Event newEvent);

  /**
   *
   */
  public void removeEvent(Event event);
}
