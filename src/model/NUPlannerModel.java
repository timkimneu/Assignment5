package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the full planner or schedule system that allows for interaction between different
 * individual's schedules and the adding, modifying, and removing events from schedules within the
 * system. Adding, modifying, and removing events should add, modify, or remove events according
 * to the list of users of the specified event and according to the existing users in the planner
 * system. Throws errors if there are time conflicts between events for any attendee of the
 * modified event. Also retrieves the full list of schedules currently contained within the
 * planner system and can also observe the list of events for a specific user if the user exists,
 * otherwise throws an error.
 */
public class NUPlannerModel implements PlannerModel {
  private final List<SchedulePlanner> schedules;

  /**
   * Planner model that is initialized without a given list of schedules.
   */
  public NUPlannerModel() {
    this.schedules = new ArrayList<>();
  }

  /**
   * Planner model that takes in an initial list of schedules and creates
   * a new planner with this list of schedules.
   * 
   * @param schedules a list of schedules to be initialized with a new planner model.
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
    throw new IllegalArgumentException("Could not find events for given schedule id!");
  }

  @Override
  public List<String> users() {
    List<String> allUsers = new ArrayList<>();
    for (Schedule sch : this.schedules()) {
      allUsers.add(sch.scheduleID());
    }
    return allUsers;
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
