package strategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.IPlannerModel;
import model.ISchedule;
import model.LocationImpl;
import model.UserImpl;

public class AnytimeStrategy<T> implements SchedulingStrategy<T> {
  private final IPlannerModel<T> model;

  public AnytimeStrategy(IPlannerModel<T> model) {
    this.model = model;
  }
  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration,
                            List<UserImpl> users) {
    int mins = duration % 60;
    int hours = ((duration - mins) / 60) % 24;
    int days = (((duration - mins) / 60) - hours) / 24;
    String endTime = null;

    for (int day = 0; day < 7; day++) {
      for (int min = 0; min < 1440; min++) {
        int startMins = min % 60;
        int startHours = (min - startMins) / 60;
        String startTime = this.getTimeString(startHours, startMins);
        Map<String, Integer> map = getEndingTime(startTime, hours, mins);
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
          endTime = entry.getKey();
        }
        if (attemptEvent(day, startTime, getNextDOTW(day, days), endTime, location, users, name)) {
          return;
        }
      }
    }
    throw new IllegalArgumentException("Cannot schedule");
  }

  // helper method to attempt to add an event and check for exceptions
  private boolean attemptEvent(int startDay, String startTime, int endDay, String endTime,
                               LocationImpl loc, List<UserImpl> users, String eventName) {
    for (UserImpl u : users) {
      try {
        for (ISchedule<T> sch : this.model.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.addEvent(startDay, startTime, endDay, endTime, loc, users, eventName);
          }
        }
      } catch (IllegalArgumentException e) {
        for (ISchedule<T> sch : this.model.schedules()) {
          try {
            sch.removeEvent(startDay, startTime, endDay, endTime, loc, users, eventName);
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

  // retrieves the next day of the week if an increment is necessary
  private int getNextDOTW(int currentDays, int days) {
    int endIndexDOTW = currentDays + days;
    if (endIndexDOTW > 6) {
      endIndexDOTW -= 7;
    }
    return endIndexDOTW;
  }

  // retrieves the time string in hours and minutes
  private String getTimeString(int hours, int mins) {
    String hrsString = String.format("%02d", hours);
    String minsString = String.format("%02d", mins);
    return hrsString + minsString;
  }
}
