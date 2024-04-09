package model;

import java.util.ArrayList;
import java.util.List;

public class WorkTimePlannerModel extends NUPlannerModel {
  private final List<SchedulePlanner> schedules;
  WorkTimePlannerModel() {
    super();
    this.schedules = new ArrayList<>();
  }

  WorkTimePlannerModel(List<SchedulePlanner> schedules) {
    super();
    this.schedules = schedules;
  }

  @Override
  public void scheduleEvent(String name, Location location, int duration, List<User> users) {
    int mins = duration % 60;
    int hours = ((duration - mins) / 60) % 24;

    String endTime = getEndingTime("0859", hours, mins);
    Time potentialTime = new Time(DaysOfTheWeek.MONDAY, "0859", DaysOfTheWeek.MONDAY, endTime);
    Event potentialEvent = new Event(name, potentialTime, location, users);

    if (!attemptEvent(potentialEvent)) {
      for (Schedule sch : this.schedules()) {
        for (Event e : sch.events()) {
          String eventEndTime = e.time().endTime();
          String eventEndTimePlusOne = String.valueOf(Integer.parseInt(eventEndTime) + 1);
          DaysOfTheWeek eventEndDay = e.time().endDay();
          Time potTime = new Time(eventEndDay, eventEndTimePlusOne, eventEndDay,
              getEndingTime(eventEndTimePlusOne, hours, mins));
          Event potEvent = new Event(name, potTime, location, users);
          attemptEvent(potEvent);
        }
      }
    }
  }

  private boolean attemptEvent(Event event) {
    try {
      for (Schedule sch : this.schedules()) {
        sch.events().add(event);
      }
      return true;
    } catch (IllegalArgumentException e) {
      for (Schedule sch : this.schedules()) {
        try {
          sch.removeEvent(event);
        } catch (IllegalArgumentException ex) {
          // ignore
        }
      }
    }
    return false;
  }

  private String getEndingTime(String start, int hours, int mins) {
    int startTimeInt = Integer.parseInt(start);
    int startMin = startTimeInt % 60;
    int startHr = (startTimeInt - startMin) / 60;

    int endMin = mins + startMin;
    int endHr = hours + startHr;
    if (endMin > 59) {
      endMin -= 60;
      endHr += 1;
    }
    if (endHr > 17) {
      throw new IllegalArgumentException("Cannot fit event!");
    }
    return getTimeString(endHr, endMin);
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
}
