package model;

import provider.model.WeekDay;
import provider.model.WeekTime;

/**
 * Represents the week times that are found in each event in the schedule planner.
 * Implements the provider's WeekTime interface, and composes of our DayOfTheWeek
 * enum in order to return the start and end time methods.
 */
public class WeekTimeAdapter implements WeekTime {
  private final DaysOfTheWeek dotw;
  private final String time;

  /**
   * Represents the week times that are found in each event in the schedule planner.
   * Implements the provider's WeekTime interface, and composes of our DayOfTheWeek
   * enum in order to return the start and end time methods.
   *
   * @param dotw Sets enum that will be used for the start and end days.
   * @param time Sets the four letter string that will be used for the times.
   */
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
    return ((Integer.parseInt(time) - getMinute()) / 100);
  }

  @Override
  public int getMinute() {
    return Integer.parseInt(time) % 100;
  }

  @Override
  public boolean isBefore(WeekTime other) {
    if (this.getWeekDay().getDayOrder() < other.getWeekDay().getDayOrder()) {
      return true;
    }
    else if (this.getWeekDay().getDayOrder() == other.getWeekDay().getDayOrder()
            && this.getHour() < other.getHour()) {
      return true;
    }
    else return this.getHour() == (other.getHour()) && this.getMinute() < other.getMinute();
  }

  @Override
  public boolean isAfter(WeekTime other) {
    if (this.getWeekDay().getDayOrder() > other.getWeekDay().getDayOrder()) {
      return true;
    }
    else if (this.getWeekDay().getDayOrder() == other.getWeekDay().getDayOrder()
            && this.getHour() > other.getHour()) {
      return true;
    }
    else return this.getHour() == (other.getHour()) && this.getMinute() > other.getMinute();
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
