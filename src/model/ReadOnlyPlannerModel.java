package model;

import java.util.List;

public interface ReadOnlyPlannerModel {

  /**
   *
   * @return
   */
  List<SchedulePlanner> schedules();

  /**
   *
   *
   * @param name
   * @return
   */
  List<Event> events(String name);
}
