package model;

import provider.model.EventTime;
import provider.model.WeekTime;

import java.util.Objects;

/**
 * Represents the times that are found in each event in the schedule planner.
 * Implements our time interface, and uses the provider's EventTime interface
 * and WeekTime enum in order to convert and compare the times.
 */
public final class ITimeAdapter implements ITime {
  private final EventTime eventTime;
  private final WeekTime startTime;
  private final WeekTime endTime;

  /**
   * Represents the times that are found in each event in the schedule planner.
   * Implements our time interface, and uses the provider's EventTime interface
   * and WeekTime enum in order to convert and compare the times.
   *
   * @param eventTime Sets the start and end times of the event.
   */
  public ITimeAdapter(EventTime eventTime) {
    this.eventTime = Objects.requireNonNull(eventTime);
    this.startTime = eventTime.getStartTime();
    this.endTime = eventTime.getEndTime();
  }

  @Override
  public DaysOfTheWeek startDay() {
    return DaysOfTheWeek.valueOf(startTime.getWeekDay().toString());
  }

  @Override
  public DaysOfTheWeek endDay() {
    return DaysOfTheWeek.valueOf(endTime.getWeekDay().toString());
  }

  @Override
  public String startTime() {
    return startTime.getTime();
  }

  @Override
  public String endTime() {
    return endTime.getTime();
  }

  @Override
  public boolean anyOverlap(ITime t) {
    return eventTime.overlapsWith(new TimeAdapter(t));
  }

  @Override
  public boolean hasOverlapContainedWeek(ITime time) {
    return eventTime.contains(new WeekTimeAdapter(time.startDay(), time.startTime()))
      || eventTime.contains(new WeekTimeAdapter(time.endDay(), time.endTime()));
  }

  @Override
  public int getHours(String s) {
    return (Integer.parseInt(s) - (Integer.parseInt(s) % 60)) / 60;
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof ITime) {
      return this.equals(other);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.startDay(), this.startTime(), this.endDay(), this.endTime());
  }
}
