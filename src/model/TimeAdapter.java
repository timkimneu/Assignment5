package model;

import provider.model.EventTime;
import provider.model.WeekTime;

public final class TimeAdapter implements ITime {
  private final WeekTime startWkTime;
  private final WeekTime endWkTime;

  public TimeAdapter(EventTime eventTime) {
    this.startWkTime = eventTime.getStartTime();
    this.endWkTime = eventTime.getEndTime();
  }

  @Override
  public DaysOfTheWeek startDay() {
    String startDayStr = startWkTime.getWeekDay().toString();
    return DaysOfTheWeek.valueOf(startDayStr);
  }

  @Override
  public DaysOfTheWeek endDay() {
    String endDayStr = endWkTime.getWeekDay().toString();
    return DaysOfTheWeek.valueOf(endDayStr);
  }

  @Override
  public String startTime() {
    return startWkTime.getTime();
  }

  @Override
  public String endTime() {
    return endWkTime.getTime();
  }

  @Override
  public boolean anyOverlap(TimeImpl t) {
    TimeImpl thisTime = new TimeImpl(startDay(), startTime(), endDay(), endTime());
    return thisTime.anyOverlap(t);
  }
}
