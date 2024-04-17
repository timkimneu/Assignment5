package model;

import provider.model.WeekDay;
import provider.model.WeekTime;

public class WeekTimeAdapter implements WeekTime {
  private final DaysOfTheWeek dotw;
  private final String time;

  public WeekTimeAdapter(DaysOfTheWeek dotw, String time) {
    this.dotw = dotw;
    this.time = time;
  }

  @Override
  public WeekDay getWeekDay() {
    return WeekDay.valueOf(dotw.toString());
  }

  @Override
  public String getTime() {
    return time;
  }

  @Override
  public int getHour() {
    return ((Integer.parseInt(time) - getMinute()) / 60) % 24;
  }

  @Override
  public int getMinute() {
    return Integer.parseInt(time) % 60;
  }

  @Override
  public boolean isBefore(WeekTime other) {
    if (this.getWeekDay().getDayOrder() < other.getWeekDay().getDayOrder()) {
      return true;
    }
    else if (this.getWeekDay().getDayOrder() == other.getWeekDay().getDayOrder() && this.getHour() < other.getHour()) {
      return true;
    }
    else if (this.getHour() == (other.getHour()) && this.getMinute() < other.getMinute()) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isAfter(WeekTime other) {
    if (this.getWeekDay().getDayOrder() > other.getWeekDay().getDayOrder()) {
      return true;
    }
    else if (this.getWeekDay().getDayOrder() == other.getWeekDay().getDayOrder() && this.getHour() > other.getHour()) {
      return true;
    }
    else if (this.getHour() == (other.getHour()) && this.getMinute() > other.getMinute()) {
      return true;
    }
    return false;
  }

  @Override
  public boolean isSame(WeekTime other) {
    return this.equals(other);
  }

  @Override
  public int compareTo(WeekTime o) {
    if (isAfter(o)) {
      return 1;
    }
    else if (isBefore(o)) {
      return -1;
    }
    else if (isSame(o)) {
      return 0;
    }
    throw new IllegalStateException("Incompatible times");
  }
}
