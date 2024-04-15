package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the work time planner or schedule system that allows for interaction between different
 * individual's schedules and the adding, modifying, and removing events from schedules within the
 * system. Adding, modifying, and removing events should add, modify, or remove events according
 * to the list of users of the specified event and according to the existing users in the planner
 * system. Throws errors if there are time conflicts between events for any attendee of the
 * modified event. Also retrieves the full list of schedules currently contained within the
 * planner system and can also observe the list of events for a specific user if the user exists,
 * otherwise throws an error.
 */
public class WorkTimePlannerModel extends NUPlannerModel {
  private final List<SchedulePlanner> schedules;

  /**
   * Planner model that is initialized without a given list of schedules.
   */
  public WorkTimePlannerModel() {
    super();
    this.schedules = new ArrayList<>();
  }

  /**
   * Planner model that is initialized with a given list of schedules.
   */
  public WorkTimePlannerModel(List<SchedulePlanner> schedules) {
    super(schedules);
    this.schedules = schedules;
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration, List<UserImpl> users) {
    List<String> daysOfTheWeek = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday",
            "Friday");
    int mins = duration % 60;
    int hours = ((duration - mins) / 60) % 24;
    String endTime = null;
    for (int day = 0; day < 5; day++) {
      for (int min = 540; min < 1020; min++) {
        if (duration + min > 1020) {
          break;
        }
        int startMins = min % 60;
        int startHours = (min - startMins) / 60;
        String startTime = this.getTimeString(startHours, startMins);
        Map<String, Integer> map = getEndingTime(startTime, hours, mins);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
          endTime = entry.getKey();
        }
        DaysOfTheWeek startingDay = DaysOfTheWeek.valueOf(daysOfTheWeek.get(day).toUpperCase());
        TimeImpl potentialTime = new TimeImpl(startingDay, startTime, startingDay, endTime);
        EventImpl potentialEvent = new EventImpl(name, potentialTime, location, users);
        if (attemptEvent(potentialEvent, users)) {
          return;
        }
      }
    }
    throw new IllegalArgumentException("Cannot schedule");
  }

  // converting the time string
  private String getTimeString(int hours, int mins) {
    String hrsString = String.format("%02d", hours);
    String minsString = String.format("%02d", mins);
    return hrsString + minsString;
  }

  // determines if event is valid
  private boolean attemptEvent(EventImpl event, List<UserImpl> users) {
    for (UserImpl u : users) {
      try {
        for (ISchedule sch : this.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.addEvent(event);
          }
        }
      } catch (IllegalArgumentException e) {
        for (ISchedule sch : this.schedules()) {
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
}
