package model;

import org.testng.internal.collections.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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
  public void scheduleEvent(String name, Location location, int duration, List<User> users) {
    int mins = duration % 60;
    int hours = ((duration - mins) / 60) % 24;
    int days = (((duration - mins) / 60) - hours) / 24;
    String endTime = null;
    int value = 0;

    Map<String, Integer> map = getEndingTime("0000", hours, mins);
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      endTime = entry.getKey();
      value = entry.getValue();
    }

    System.out.println("ENDDDD: " + endTime);
    System.out.println("VALUEEE: " + value);
    Time potentialTime = new Time(DaysOfTheWeek.SUNDAY, "0000",
        getNextDOTW(DaysOfTheWeek.SUNDAY, days + value), endTime);
    Event potentialEvent = new Event(name, potentialTime, location, users);

    if (!attemptEvent(potentialEvent, users)) {
      for (Schedule sch : this.schedules()) {
        for (int i = 0 ; i < sch.events().size(); i++) {
          String eventEndTime = sch.events().get(i).time().endTime();
          String eventEndTimePlusOne = String.valueOf(Integer.parseInt(eventEndTime) + 1);
          DaysOfTheWeek eventEndDay = sch.events().get(i).time().endDay();
          Map<String, Integer> map1 = getEndingTime(eventEndTimePlusOne, hours, mins);
          for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            endTime = entry.getKey();
            value = entry.getValue();
          }
          Time potTime = new Time(eventEndDay, eventEndTimePlusOne, getNextDOTW(eventEndDay, days + value), endTime);
          Event potEvent = new Event(name, potTime, location, users);
          if (attemptEvent(potEvent, users)) {
            System.out.println("!!PLp");
            return;
          }
        }
      }
    }
  }

  private boolean attemptEvent(Event event, List<User> users) {
    for (User u : users) {
      try {
        for (Schedule sch : this.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.addEvent(event);
          }
        }
      } catch (IllegalArgumentException e) {
        for (Schedule sch : this.schedules()) {
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
      days = 1;
    }
    Map<String, Integer> result = new HashMap<>();
    result.put(getTimeString(endHr, endMin), days);
    return result;
  }

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
    DaysOfTheWeek day = DaysOfTheWeek.valueOf(endStrDOTW.toUpperCase());
    return day;
  }

  private String getTimeString(int hours, int mins) {
    String hrsString;
    String minsString;
    if (hours < 10) {
      hrsString = "0" + hours;
    } else {
      hrsString = String.valueOf(hours);
    }

    if (mins < 10) {
      minsString = "0" + mins;
    } else {
      minsString = String.valueOf(mins);
    }
    return hrsString + minsString;
  }

  @Override
  public void modifyEvent(Event event, Event newEvent, User user) {
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
  public void removeEvent(Event event, User user) {
    if (event.isHost(user)) {
      for (User u : event.users()) {
        for (Schedule sch : this.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.removeEvent(event);
          }
        }
      }
    } else {
      for (Schedule sch : this.schedules()) {
        if (user.name().equals(sch.scheduleID())) {
          sch.removeEvent(event);
        }
      }
    }
  }

}
