package model;

import provider.model.EventTime;
import provider.model.WeekTime;

/**
 * Represents the times that are found in each event in the schedule planner.
 * Implements the provider's EventTime interface, and composes of our ITime interface
 * in order to return the start and end time methods.
 */
public final class TimeAdapter implements EventTime {
  private final ITime timeImpl;

  /**
   * Represents the times that are found in each event in the schedule planner.
   * Implements the provider's EventTime interface, and composes of our ITime interface
   * in order to return the start and end time methods.
   *
   * @param time Sets the time of the event in the schedule.
   */
  public TimeAdapter(ITime time) {
    this.timeImpl = time;
  }

  @Override
  public WeekTime getStartTime() {
    return new WeekTimeAdapter(timeImpl.startDay(), timeImpl.startTime());
  }

  @Override
  public WeekTime getEndTime() {
    return new WeekTimeAdapter(timeImpl.endDay(), timeImpl.endTime());
  }

  @Override
  public boolean overlapsWith(EventTime other) {
    return timeImpl.anyOverlap(new ITimeAdapter(other));
  }

  @Override
  public boolean contains(WeekTime time) {
    return false;
  }
}
