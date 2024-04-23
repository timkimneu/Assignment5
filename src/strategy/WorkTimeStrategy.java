package strategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.IPlannerModel;
import model.ISchedule;
import model.LocationImpl;
import model.UserImpl;

/**
 *
 * @param <T>
 */
public class WorkTimeStrategy<T> implements SchedulingStrategy<T> {
  private final IPlannerModel<T> model;

  public WorkTimeStrategy(IPlannerModel<T> model) {
    this.model = model;
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration,
                            List<UserImpl> users) {
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
        if (attemptEvent(day + model.getFirstDay(), startTime, day + model.getFirstDay(), endTime,
                location, users, name)) {
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
  private boolean attemptEvent(int startDay, String startTime, int endDay, String endTime,
                               LocationImpl loc, List<UserImpl> users, String eventName) {
    for (UserImpl u : users) {
      try {
        for (ISchedule<T> sch : model.schedules()) {
          if (u.name().equals(sch.scheduleID())) {
            sch.addEvent(startDay, startTime, endDay, endTime, loc, users, eventName);
          }
        }
      } catch (IllegalArgumentException e) {
        for (ISchedule<T> sch : model.schedules()) {
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
}
