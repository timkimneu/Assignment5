package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NUPlannerModel implements PlannerModel {
  private final List<SchedulePlanner> schedules;

  /**
   *
   */
  public NUPlannerModel() {
    this.schedules = new ArrayList<>();
  }

  /**
   *
   * @param schedules
   */
  public NUPlannerModel(List<SchedulePlanner> schedules) {
    this.schedules = schedules;
  }

  @Override
  public List<SchedulePlanner> schedules() {
    return this.schedules;
  }

  @Override
  public List<Event> events(String id) {
    for (Schedule sch : this.schedules()) {
      if (sch.scheduleID().equals(id)) {
        return sch.events();
      }
    }
  }

  @Override
  public void addEvent(Event event) {
    for (User u : event.users()) {
      for (SchedulePlanner sch : this.schedules()) {
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
    for (User u : event.users()) {
      for (SchedulePlanner sch : this.schedules) {
        String schID = sch.scheduleID();
        if (u.name().equals(schID)) {
          sch.removeEvent(event);
          sch.addEvent(newEvent);
        }
      }
    }
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
