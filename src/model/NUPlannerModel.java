package model;

import java.util.List;

/**
 *
 */
public class NUPlannerModel implements PlannerModel {
  private final List<SchedulePlanner> schedules;

  public NUPlannerModel(List<SchedulePlanner> schedules) {
    this.schedules = schedules;
  }

  @Override
  public List<SchedulePlanner> schedules() {
    return this.schedules;
  }

  @Override
  public void addEvent(Event event) {
    for (User u : event.users()) {
      for (SchedulePlanner sch : this.schedules) {
        String scheduleID = sch.scheduleID();
        if (u.name().equals(scheduleID)) {
          sch.addEvent(event);
        }
      }
    }
  }

  @Override
  public void modifyEvent(Event event, Event newEvent) {
    if (event.equals(newEvent)) {
      throw new IllegalArgumentException("Cannot replace old event with same event!");
    }
    this.removeEvent(event);
    this.addEvent(newEvent);
  }

  @Override
  public void removeEvent(Event event) {
    for (User u : event.users()) {
      for (SchedulePlanner sch : this.schedules) {
        String scheduleID = sch.scheduleID();
        if (u.name().equals(scheduleID)) {
          sch.removeEvent(event);
        }
      }
    }
  }

}
