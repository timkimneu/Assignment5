package view;

import model.Schedule;

import java.util.List;

/**
 *
 */
public class ScheduleSystemTextView implements ScheduleSystemView {
  private final List<Schedule> schedules;

  public ScheduleSystemTextView(List<Schedule> schedules) {
    this.schedules = schedules;
  }

  @Override
  public String schedulesToString() {
    return "";
  }
}
