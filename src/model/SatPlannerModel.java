package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  public void addSchedule(List<String> startDay, List<String> endDay, List<String> startTime,
                          List<String> endTime, List<LocationImpl> loc, List<List<UserImpl>> users,
                          List<String> eventName, String id) {
    List<IEvent<SatDOTW>> events = new ArrayList<>();
    for (int event = 0; event < eventName.size(); event++) {
      SatTimeImpl timeImpl = new SatTimeImpl(SatDOTW.valueOf(startDay.get(event).toUpperCase()),
          startTime.get(event), SatDOTW.valueOf(endDay.get(event)), endTime.get(event).toUpperCase());
      IEvent<SatDOTW> eventImpl = new SatEventImpl(eventName.get(event), timeImpl, loc.get(event), users.get(event));
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
          sch.addEvent(event);
        }
      }
    }
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration,
                            List<UserImpl> users) {
    List<String> daysOfTheWeek = Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday");
    int mins = duration % 60;
    int hours = ((duration - mins) / 60) % 24;
    int days = (((duration - mins) / 60) - hours) / 24;
    String endTime = null;
    int value = 0;

    for (int day = 0; day < 7; day++) {
      for (int min = 0; min < 1440; min++) {
        int startMins = min % 60;
        int startHours = (min - startMins) / 60;
        String startTime = this.getTimeString(startHours, startMins);
        Map<String, Integer> map = getEndingTime(startTime, hours, mins);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
          endTime = entry.getKey();
          value = entry.getValue();
        }
        SatDOTW startingDay = SatDOTW.valueOf(daysOfTheWeek.get(day).toUpperCase());
        SatTimeImpl potentialTime = new SatTimeImpl(startingDay, startTime,
            getNextDOTW(startingDay, days + value), endTime);
        SatEventImpl potentialEvent = new SatEventImpl(name, potentialTime, location, users);
        if (attemptEvent(potentialEvent, users)) {
          return;
        }
      }
    }
    throw new IllegalArgumentException("Cannot schedule");
  }

  // helper method to attempt to add an event and check for exceptions
  private boolean attemptEvent(IEvent<SatDOTW> event, List<UserImpl> users) {
    for (UserImpl u : users) {
      try {
        for (ISchedule<SatDOTW> sch : this.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.addEvent(event);
          }
        }
      } catch (IllegalArgumentException e) {
        for (ISchedule<SatDOTW> sch : this.schedules()) {
          try {
            sch.removeEvent(event);
          } catch (IllegalArgumentException ex) {
            // ignore
          }
        }
        return false;
      }
    }
    return true;
  }

  // returns a map of the string days and integer of the day index of the list
  private Map<String, Integer> getEndingTime(String start, int hours, int mins) {
    int startTimeInt = Integer.parseInt(start);
    int startMin = startTimeInt % 100;
    int startHr = (startTimeInt - startMin) / 100;
    int days = 0;

    int endMin = mins + startMin;
    int endHr = hours + startHr;
    while (endMin > 59) {
      endMin -= 60;
      endHr += 1;
    }
    while (endHr > 23) {
      endHr -= 24;
      days += 1;
    }
    Map<String, Integer> result = new HashMap<>();
    result.put(getTimeString(endHr, endMin), days);
    return result;
  }

  // retrieves the next day of the week if an incrememt is necessary
  private SatDOTW getNextDOTW(SatDOTW start, int days) {
    List<String> daysOfTheWeek = Arrays.asList("Saturday", "Sunday", "Monday", "Tuesday",
        "Wednesday", "Thursday", "Friday");
    String startStrDOTW = start.observeDay();
    int startIndexDOTW = daysOfTheWeek.indexOf(startStrDOTW);
    int endIndexDOTW = startIndexDOTW + days;
    if (endIndexDOTW > 6) {
      endIndexDOTW -= 7;
    }
    String endStrDOTW = daysOfTheWeek.get(endIndexDOTW);
    return SatDOTW.valueOf(endStrDOTW.toUpperCase());
  }

  // retrieves the time string in hours and minutes
  private String getTimeString(int hours, int mins) {
    String hrsString = String.format("%02d", hours);
    String minsString = String.format("%02d", mins);
    return hrsString + minsString;
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
          sch.removeEvent(event);
          sch.addEvent(newEvent);
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
            sch.removeEvent(event);
          }
        }
      }
    } else {
      for (ISchedule<SatDOTW> sch : this.schedules()) {
        if (user.name().equals(sch.scheduleID())) {
          sch.removeEvent(event);
        }
      }
    }
  }
}
