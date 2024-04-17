package model;

import provider.model.EventTime;
import provider.model.WeekTime;

public final class TimeAdapter implements EventTime {
  private final ITime timeImpl;

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
