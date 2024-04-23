package model;

import java.util.ArrayList;
import java.util.List;

import strategy.AnytimeStrategy;

/**
 * Represents a part of the full planner or schedule system that allows for interaction between
 * different individual's schedules and the adding, modifying, and removing events from schedules
 * within the system. Adding, modifying, and removing events should add, modify, or remove events
 * according to the list of users of the specified event and according to the existing users in the
 * planner system. Throws errors if there are time conflicts between events for any attendee of the
 * modified event.
 */
public class SatPlannerModel implements IPlannerModel<SatDOTW> {
  private final List<ISchedule<SatDOTW>> schedules;

  /**
   * Planner model that is initialized without a given list of schedules.
   */
  public SatPlannerModel() {
    this.schedules = new ArrayList<>();
  }


  /**
   * Planner model that takes in an initial list of schedules and creates
   * a new planner with this list of schedules.
   *
   * @param schedules a list of schedules to be initialized with a new planner model.
   */
  public SatPlannerModel(List<ISchedule<SatDOTW>> schedules) {
    this.schedules = schedules;
  }

  @Override
  public int getFirstDay() {
    return this.schedules.get(0).getFirstDay();
  }

  @Override
  public void addSchedule(List<String> startDay, List<String> endDay, List<String> startTime,
                          List<String> endTime, List<LocationImpl> loc, List<List<UserImpl>> users,
                          List<String> eventName, String id) {
    List<IEvent<SatDOTW>> events = new ArrayList<>();
    for (int event = 0; event < eventName.size(); event++) {
      SatTimeImpl timeImpl = new SatTimeImpl(SatDOTW.valueOf(startDay.get(event).toUpperCase()),
          startTime.get(event), SatDOTW.valueOf(endDay.get(event)),
              endTime.get(event).toUpperCase());
      IEvent<SatDOTW> eventImpl = new SatEventImpl(eventName.get(event), timeImpl, loc.get(event),
              users.get(event));
      events.add(eventImpl);
    }
    ISchedule<SatDOTW> schPlan = new SatSchedulePlanner(events, id);
    if (!this.schedules().contains(schPlan)) {
      this.schedules.add(schPlan);
    }
  }

  @Override
  public List<ISchedule<SatDOTW>> schedules() {
    return this.schedules;
  }

  @Override
  public List<IEvent<SatDOTW>> events(String id) {
    for (ISchedule<SatDOTW> sch : this.schedules()) {
      if (sch.scheduleID().equals(id)) {
        return sch.events();
      }
    }
    throw new IllegalArgumentException("Could not find events for given schedule id!");
  }

  @Override
  public List<String> users() {
    List<String> allUsers = new ArrayList<>();
    for (ISchedule<SatDOTW> sch : this.schedules()) {
      allUsers.add(sch.scheduleID());
    }
    return allUsers;
  }

  @Override
  public void addEvent(IEvent<SatDOTW> event) {
    for (UserImpl u : event.users()) {
      for (ISchedule<SatDOTW> sch : this.schedules()) {
        String scheduleID = sch.scheduleID();
        if (u.name().equals(scheduleID)) {
          sch.addEvent(event.time().startDay().getDayOrder(), event.time().startTime(),
                  event.time().endDay().getDayOrder(), event.time().endTime(), event.location(),
                  event.users(), event.name());
        }
      }
    }
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration,
                            List<UserImpl> users) {
    AnytimeStrategy<SatDOTW> anytimeStrategy = new AnytimeStrategy<>(this);
    anytimeStrategy.scheduleEvent(name, location, duration, users);
  }

  @Override
  public void modifyEvent(IEvent<SatDOTW> event, IEvent<SatDOTW> newEvent, UserImpl user) {
    if (event.equals(newEvent)) {
      throw new IllegalArgumentException("Cannot replace old event with same event!");
    }
    for (UserImpl u : event.users()) {
      for (ISchedule<SatDOTW> sch : this.schedules) {
        String schID = sch.scheduleID();
        if (u.name().equals(schID)) {
          sch.removeEvent(event.time().startDay().getDayOrder(), event.time().startTime(),
                  event.time().endDay().getDayOrder(), event.time().endTime(), event.location(),
                  event.users(), event.name());
          sch.addEvent(newEvent.time().startDay().getDayOrder(), newEvent.time().startTime(),
                  newEvent.time().endDay().getDayOrder(), newEvent.time().endTime(),
                  newEvent.location(), newEvent.users(), newEvent.name());
        }
      }
    }
  }

  @Override
  public void removeEvent(IEvent<SatDOTW> event, UserImpl user) {
    if (event.isHost(user.toString())) {
      for (UserImpl u : event.users()) {
        for (ISchedule<SatDOTW> sch : this.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.removeEvent(event.time().startDay().getDayOrder(), event.time().startTime(),
                    event.time().endDay().getDayOrder(), event.time().endTime(), event.location(),
                    event.users(), event.name());
          }
        }
      }
    } else {
      for (ISchedule<SatDOTW> sch : this.schedules()) {
        if (user.name().equals(sch.scheduleID())) {
          sch.removeEvent(event.time().startDay().getDayOrder(), event.time().startTime(),
                  event.time().endDay().getDayOrder(), event.time().endTime(), event.location(),
                  event.users(), event.name());
        }
      }
    }
  }
}
