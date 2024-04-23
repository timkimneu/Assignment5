package model;

import java.util.ArrayList;
import java.util.List;

import strategy.WorkTimeStrategy;

public class SatWorktimeModel extends SatPlannerModel {
  private final List<ISchedule<SatDOTW>> schedules;

  /**
   * Planner model that is initialized without a given list of schedules.
   */
  public SatWorktimeModel() {
    super();
    this.schedules = new ArrayList<>();
  }

  /**
   * Planner model that is initialized with a given list of schedules.
   */
  public SatWorktimeModel(List<ISchedule<SatDOTW>> schedules) {
    super(schedules);
    this.schedules = schedules;
  }

  @Override
  public void scheduleEvent(String name, LocationImpl location, int duration,
                            List<UserImpl> users) {
    WorkTimeStrategy<SatDOTW> workTimeStrategy = new WorkTimeStrategy<>(this);
    workTimeStrategy.scheduleEvent(name, location, duration, users);
  }
}
