package model;

import java.util.List;

/**
 *
 */
public class NUPlannerModel implements PlannerModel {
  private final List<Schedule> schedules;

  public NUPlannerModel(List<Schedule> schedules) {
    this.schedules = schedules;
  }

  @Override
  public List<Schedule> schedules() {
    return this.schedules;
  }

  @Override
  public void addEvent(Event event) {
    for (User u : event.users()) {
      for (Schedule sch : this.schedules) {
        String scheduleID = sch.scheduleID();
        if (u.name().equals(scheduleID)) {
          try {
            sch.addEvent(event);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
          }
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
      for (Schedule sch : this.schedules) {
        String scheduleID = sch.scheduleID();
        if (u.name().equals(scheduleID)) {
          try {
            sch.removeEvent(event);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
          }
        }
      }
    }
  }
}
