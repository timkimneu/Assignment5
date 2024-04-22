package model;

import org.hamcrest.core.Is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class NUPlannerModel implements IPlannerModel<DaysOfTheWeek> {
  private final List<ISchedule<DaysOfTheWeek>> schedules;

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
  public NUPlannerModel(List<ISchedule<DaysOfTheWeek>> schedules) {
    this.schedules = schedules;
  }

  @Override
  public void addSchedule(List<String> startDay, List<String> endDay, List<String> startTime,
                          List<String> endTime, List<LocationImpl> loc, List<List<UserImpl>> users,
                          List<String> eventName, String id) {
    List<IEvent<DaysOfTheWeek>> events = new ArrayList<>();
    for (int event = 0; event < eventName.size(); event++) {
      TimeImpl timeImpl = new TimeImpl(DaysOfTheWeek.valueOf(startDay.get(event).toUpperCase()),
          startTime.get(event), DaysOfTheWeek.valueOf(endDay.get(event)), endTime.get(event).toUpperCase());
      IEvent<DaysOfTheWeek> eventImpl = new EventImpl(eventName.get(event), timeImpl,
          loc.get(event), users.get(event));
      events.add(eventImpl);
    }
    ISchedule<DaysOfTheWeek> schPlan = new SchedulePlanner(events, id);
    if (!this.schedules().contains(schPlan)) {
      this.schedules.add(schPlan);
    }
  }

  @Override
  public List<ISchedule<DaysOfTheWeek>> schedules() {
    return this.schedules;
  }

  @Override
  public List<IEvent<DaysOfTheWeek>> events(String id) {
    for (ISchedule<DaysOfTheWeek> sch : this.schedules()) {
      if (sch.scheduleID().equals(id)) {
        return sch.events();
      }
    }
    throw new IllegalArgumentException("Could not find events for given schedule id!");
  }

  @Override
  public List<String> users() {
    List<String> allUsers = new ArrayList<>();
    for (ISchedule<DaysOfTheWeek> sch : this.schedules()) {
      allUsers.add(sch.scheduleID());
    }
    return allUsers;
  }

  @Override
  public void addEvent(IEvent<DaysOfTheWeek> event) {
    for (UserImpl u : event.users()) {
      for (ISchedule<DaysOfTheWeek> sch : this.schedules()) {
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
    List<String> daysOfTheWeek = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday");
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
        DaysOfTheWeek startingDay = DaysOfTheWeek.valueOf(daysOfTheWeek.get(day).toUpperCase());
        TimeImpl potentialTime = new TimeImpl(startingDay, startTime,
                getNextDOTW(startingDay, days + value), endTime);
        EventImpl potentialEvent = new EventImpl(name, potentialTime, location, users);
        if (attemptEvent(potentialEvent, users)) {
          return;
        }
      }
    }
    throw new IllegalArgumentException("Cannot schedule");
  }

  // helper method to attempt to add an event and check for exceptions
  private boolean attemptEvent(IEvent<DaysOfTheWeek> event, List<UserImpl> users) {
    for (UserImpl u : users) {
      try {
        for (ISchedule<DaysOfTheWeek> sch : this.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.addEvent(event);
          }
        }
      } catch (IllegalArgumentException e) {
        for (ISchedule<DaysOfTheWeek> sch : this.schedules()) {
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
  private DaysOfTheWeek getNextDOTW(DaysOfTheWeek start, int days) {
    List<String> daysOfTheWeek = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday");
    String startStrDOTW = start.observeDay();
    int startIndexDOTW = daysOfTheWeek.indexOf(startStrDOTW);
    int endIndexDOTW = startIndexDOTW + days;
    if (endIndexDOTW > 6) {
      endIndexDOTW -= 7;
    }
    String endStrDOTW = daysOfTheWeek.get(endIndexDOTW);
    return DaysOfTheWeek.valueOf(endStrDOTW.toUpperCase());
  }

  // retrieves the time string in hours and minutes
  private String getTimeString(int hours, int mins) {
    String hrsString = String.format("%02d", hours);
    String minsString = String.format("%02d", mins);
    return hrsString + minsString;
  }

  @Override
  public void modifyEvent(IEvent<DaysOfTheWeek> event, IEvent<DaysOfTheWeek> newEvent, UserImpl user) {
    if (event.equals(newEvent)) {
      throw new IllegalArgumentException("Cannot replace old event with same event!");
    }
    for (UserImpl u : event.users()) {
      for (ISchedule<DaysOfTheWeek> sch : this.schedules) {
        String schID = sch.scheduleID();
        if (u.name().equals(schID)) {
          sch.removeEvent(event);
          sch.addEvent(newEvent);
        }
      }
    }
  }

  @Override
  public void removeEvent(IEvent<DaysOfTheWeek> event, UserImpl user) {
    if (event.isHost(user.toString())) {
      for (UserImpl u : event.users()) {
        for (ISchedule<DaysOfTheWeek> sch : this.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.removeEvent(event);
          }
        }
      }
    } else {
      for (ISchedule<DaysOfTheWeek> sch : this.schedules()) {
        if (user.name().equals(sch.scheduleID())) {
          sch.removeEvent(event);
        }
      }
    }
  }

}

